package cliapp.commands.cli;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cliapp.Messages;
import cliapp.cliclient.CLIClient;
import cliapp.cliclient.UserInputPipeline;
import cliapp.cliclient.exceptions.CommandNotFoundError;
import cliapp.cliclient.exceptions.InlineParamsError;
import cliapp.commands.cli.requirements.ScriptFileRequirement;
import commands.Command;
import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.Requirement;
import commands.requirements.RequirementsPipeline;
import commands.requirements.exceptions.RequirementAskError;
import commands.requirements.exceptions.ValidationError;;

/**
 * Execute specified script with commands.
 * 
 * Script format:
 * Script file must have .neko extension.
 * 
 * Direct dynamic params load:
 * update 0 {Alex 25 1.75 1 true false SADNESS KIA}
 * 
 * Ask dynamic params from user:
 * update 0 {}
 * 
 * Example script:
 * add {Alex 25 1.75 1 true false SADNESS KIA}
 * print_sorted des
 * show
 */
public class ExecuteCommand extends CLICommand {
    public ExecuteCommand(CLIClient client) {
        super(Messages.ExecuteCommand.NAME, Messages.ExecuteCommand.DESCRIPTION, client);
    }

    @Override
    public List<Requirement<?, ?>> getStaticRequirements() {
        return List.of(new ScriptFileRequirement());
    }

    /**
     * Pipeline that store dynamic params and pop one of them when asked for next
     * param
     * 
     */
    private static class DirectLoadPipeline implements RequirementsPipeline {
        private final Map<String, String> staticRequirementsMap;

        private final LinkedList<String> dynamicRequirements;

        public DirectLoadPipeline(Map<String, String> staticRequirementsMap, List<String> dynamicRequirements) {
            this.staticRequirementsMap = staticRequirementsMap;
            this.dynamicRequirements = new LinkedList<>(dynamicRequirements);
        }

        @Override
        public <I, O> O askRequirement(Requirement<I, O> requirement) throws RequirementAskError {
            try {
                if (staticRequirementsMap.containsKey(requirement.getName())) {
                    // static requirements
                    return requirement.getValue((I) staticRequirementsMap.get(requirement.getName()));
                } else {
                    // dynamic requirements
                    if (dynamicRequirements.isEmpty()) {
                        throw new RequirementAskError(Messages.ExecuteCommand.NOT_ENOUGH_DYNAMIC_PARAMS);
                    }
                    return requirement.getValue((I) dynamicRequirements.poll());
                }
            } catch (ValidationError e) {
                throw new RequirementAskError(requirement.getName(), e);
            }
        }
    }

    /**
     * Extract list of static params from line.
     * 
     * Static params must be specified before "{}" construction
     */
    private List<String> extractStaticParams(String line) {
        Pattern pattern = Pattern.compile("^(\\w+ ?)+(?=\\{|$)");
        Matcher matcher = pattern.matcher(line);
        String match = matcher.find() ? matcher.group() : "";
        return client.parseInlineParams(match);
    }

    /**
     * Extract list of dynamic params from line
     * 
     * Dynamic params must be specified in "{}" construction
     */
    private List<String> extractDynamicParams(String line) {
        Pattern pattern = Pattern.compile("(?<=\\{).+(?=\\})");
        Matcher matcher = pattern.matcher(line);
        String match = matcher.find() ? matcher.group() : "";
        return client.parseInlineParams(match);
    }

    /**
     * Execute script line with specified dynamic arguments
     */
    private void executeWithDynamicParams(String line) throws ExecutionError {
        // parse line for static params
        List<String> params = extractStaticParams(line);
        String trigger = params.get(0);
        params.remove(0);
        // resolve command
        Command command;
        try {
            command = client.resolveCommand(trigger);
        } catch (CommandNotFoundError e) {
            throw new ExecutionError(e.getMessage());
        }
        // map static requirements
        Map<String, String> staticRequirementsMap;
        try {
            staticRequirementsMap = client.mapStaticRequirements(command.getStaticRequirements(), params);
        } catch (InlineParamsError e) {
            throw new ExecutionError(e.getMessage());
        }
        // extract dynamic params
        List<String> dynamicParams = extractDynamicParams(line);
        // init output channel and pipeline
        OutputChannel output = System.out::println;
        RequirementsPipeline pipeline = new DirectLoadPipeline(staticRequirementsMap, dynamicParams);
        // execute command
        command.execute(pipeline, output);
    }

    /**
     * Execute script line that require input from user
     */
    private void executeWithUserParams(String line) throws ExecutionError {
        // parse line for static params
        List<String> params = extractStaticParams(line);
        String trigger = params.get(0);
        params.remove(0);
        // resolve command
        Command command;
        try {
            command = client.resolveCommand(trigger);
        } catch (CommandNotFoundError e) {
            throw new ExecutionError(e.getMessage());
        }
        // map static requirements
        Map<String, String> staticRequirementsMap;
        try {
            staticRequirementsMap = client.mapStaticRequirements(command.getStaticRequirements(), params);
        } catch (InlineParamsError e) {
            throw new ExecutionError(e.getMessage());
        }
        // init output channel and pipeline
        OutputChannel output = System.out::println;
        RequirementsPipeline pipeline = new UserInputPipeline(
                staticRequirementsMap,
                new Scanner(System.in),
                client.getAskRequirementAttempts());
        // execute command
        command.execute(pipeline, output);
    }

    /**
     * Parse one script line, resolve params type and execute command
     */
    private void executeScriptLine(String line) throws ExecutionError {
        if (line.matches("^(\\w+ ?)+\\{.+\\}$")) {
            // with direct loaded params
            executeWithDynamicParams(line);
        } else if (line.matches("^(\\w+ ?)+(\\{})?$")) {
            // with params from user
            executeWithUserParams(line);
        } else {
            // without dynamic params
            throw new ExecutionError(Messages.ExecuteCommand.INCORRECT_LINE_FORMAT.formatted(line));
        }
    }

    /**
     * Execute script line by line
     */
    private void executeScript(String script) throws ExecutionError {
        for (String line : script.split(System.lineSeparator())) {
            try {
                executeScriptLine(line);
            } catch (Exception e) {
                throw new ExecutionError(Messages.ExecuteCommand.LINE_ERROR.formatted(line), e);
            }
        }
    }

    @Override
    public void execute(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
        Path scriptPath;
        try {
            scriptPath = pipeline.askRequirement(new ScriptFileRequirement());
        } catch (RequirementAskError e) {
            throw new ExecutionError(e.getMessage());
        }

        // read script file
        String scriptContent;
        try {
            scriptContent = Files.readString(scriptPath);
        } catch (IOException e) {
            throw new ExecutionError(e.getMessage());
        }
        executeScript(scriptContent);
    }

}
