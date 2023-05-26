package cliapp.commands.cli;

import static textlocale.TextLocale._;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cliapp.cliclient.CLIClient;
import cliapp.cliclient.UserInputPipeline;
import cliapp.cliclient.exceptions.CommandNotFoundError;
import cliapp.cliclient.exceptions.InlineParamsError;
import commands.Command;
import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.Requirement;
import commands.requirements.RequirementsPipeline;
import commands.requirements.exceptions.RequirementAskError;
import commands.requirements.exceptions.ValidationError;
import commands.requirements.validators.Validator;

/**
 * A command that executes specified script with commands.
 *
 * Script file must have .neko extension.
 *
 * Direct dynamic params load:
 * add {TestName 0 0 true false 10.4 KW "" SADNESS KiaRio}
 *
 * Ask dynamic params from user:
 * update 0 {}
 *
 * Example script:
 * add {TestName 0 0 true false 10.4 KW "" SADNESS KiaRio}
 * print_sorted des
 * show
 */
public class ExecuteCommand extends CLICommand {
    /**
     * Maximum count of recursive call for one script file
     */
    private Integer maxRecursionDepth = 5;

    /**
     * Map with count of call for each script file. Used to prevent recursion
     */
    private Map<Path, Integer> callCounter = new HashMap<>();

    private static final Requirement<String, Path> scriptFileRequirement = new Requirement<>(
            _("commands.cli.commands.ExecuteCommand.ScriptFileRequirement.Name"),
            _("commands.cli.commands.ExecuteCommand.ScriptFileRequirement.Description"),
            new ScriptPathValidator());

    /**
     * Validates string as file path and returns absolute path
     */
    private static class ScriptPathValidator implements Validator<String, Path> {

        /**
         * Validates a string as a file path with .neko extension and returns its
         * absolute path.
         *
         * @param value the string to be validated as a file path
         * @return the absolute path of the validated file path
         * @throws ValidationError if the value is not a valid file path or does not
         *                         have a .neko extension
         */
        @Override
        public Path validate(String value) throws ValidationError {
            if (!value.endsWith(".neko")) {
                throw new ValidationError(value,
                        _("commands.cli.commands.ExecuteCommand.ScriptFileRequirement.IncorrectExtension"));
            }
            try {
                Path path = Paths.get(value);
                return path.toAbsolutePath();
            } catch (InvalidPathException e) {
                throw new ValidationError(value, e.getMessage());
            }
        }
    }

    /**
     * Creates a new ExecuteCommand object.
     *
     * @param client a {@link cliapp.cliclient.CLIClient} instance.
     */
    public ExecuteCommand(CLIClient client) {
        super(_("commands.cli.commands.ExecuteCommand.Name"), _("commands.cli.commands.ExecuteCommand.Description"),
                client);
    }

    /**
     * Returns the static requirements of this command.
     *
     * @return a list of {@link commands.requirements.Requirement}.
     */
    @Override
    public List<Requirement<?, ?>> getStaticRequirements() {
        return List.of(scriptFileRequirement);
    }

    /**
     * A pipeline that stores dynamic parameters and pops one of them when asked for
     * next parameter.
     */
    private static class DirectLoadPipeline implements RequirementsPipeline {
        private final Map<String, String> staticRequirementsMap;

        private final LinkedList<String> dynamicRequirements;

        /**
         * Creates a new DirectLoadPipeline object.
         *
         * @param staticRequirementsMap a map containing the static requirements.
         * @param dynamicRequirements   a list of dynamic requirements.
         */
        public DirectLoadPipeline(Map<String, String> staticRequirementsMap, List<String> dynamicRequirements) {
            this.staticRequirementsMap = staticRequirementsMap;
            this.dynamicRequirements = new LinkedList<>(dynamicRequirements);
        }

        /**
         * Asks for the requirement and returns the value of it.
         *
         * @param requirement a {@link commands.requirements.Requirement} instance.
         *
         * @throws RequirementAskError if there's an error while asking the requirement.
         *
         * @return the value of the requirement.
         */
        @Override
        public <I, O> O askRequirement(Requirement<I, O> requirement) throws RequirementAskError {
            try {
                if (staticRequirementsMap.containsKey(requirement.getName())) {
                    // static requirements
                    return requirement.getValue((I) staticRequirementsMap.get(requirement.getName()));
                } else {
                    // dynamic requirements
                    if (dynamicRequirements.isEmpty()) {
                        throw new RequirementAskError(requirement.getName(),
                                _("commands.cli.commands.ExecuteCommand.NotEnoughDynamicParams"));
                    }
                    return requirement.getValue((I) dynamicRequirements.poll());
                }
            } catch (ValidationError e) {
                throw new RequirementAskError(requirement.getName(), e);
            }
        }
    }

    /**
     * Extracts the list of static parameters from a given script line.
     *
     * @param line the script line to extract static parameters from
     * @return the list of static parameters extracted from the given line
     */
    private List<String> extractStaticParams(String line) {
        Pattern pattern = Pattern.compile("^[^{\\n]+");
        Matcher matcher = pattern.matcher(line);
        String match = matcher.find() ? matcher.group() : "";
        return client.parseInlineParams(match);
    }

    /**
     * Extracts the list of dynamic parameters from a given script line.
     *
     * @param line the script line to extract dynamic parameters from
     * @return the list of dynamic parameters extracted from the given line
     */
    private List<String> extractDynamicParams(String line) {
        Pattern pattern = Pattern.compile("(?<=\\{).+(?=\\})");
        Matcher matcher = pattern.matcher(line);
        String match = matcher.find() ? matcher.group() : "";
        return client.parseInlineParams(match);
    }

    /**
     * Executes a script line with specified dynamic arguments.
     *
     * @param line the script line to execute with dynamic arguments
     * @throws ExecutionError if an error occurs while executing the command
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
        output.putString("Execute: " + command.getName() + " ...");
        command.execute(pipeline, output);
    }

    /**
     * Executes a script line that requires input from the user.
     *
     * @param line the script line to execute with user input
     * @throws ExecutionError if an error occurs while executing the command
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
                client.getAskRequirementAttempts(),
                client.getUserInputSupplier());
        // execute command
        output.putString("Execute: " + command.getName() + " ...");
        command.execute(pipeline, output);
    }

    /**
     * Parse one script line, resolve params type and execute command
     */
    private void executeScriptLine(String line) throws ExecutionError {
        if (line.startsWith("#")) {
            // comment
            return;
        } else if (line.matches("^ *$")) {
            // empty line
            return;
        } else if (line.matches("^.+\\{.+\\}$")) {
            // with direct loaded params
            executeWithDynamicParams(line);
        } else if (line.matches("^.+(\\{\\})?$")) {
            // with params from user
            executeWithUserParams(line);
        } else {
            // without dynamic params
            throw new ExecutionError(_("commands.cli.commands.ExecuteCommand.IncorrectLineFormat").formatted(line));
        }
    }

    /**
     * Executes a script by iterating over its lines and calling
     * {@link #executeScriptLine(String)} on each line.
     *
     * @param scriptPath Path of script file.
     * @param script     The script content.
     * @throws ExecutionError If an error occurs while executing any of the script
     *                        lines.
     */
    private void executeScript(Path scriptPath, String script) throws ExecutionError {
        // update call counter
        Integer callCount = callCounter.getOrDefault(scriptPath, 0);
        callCounter.put(scriptPath, callCount + 1);
        // check max recursion depth exceeding
        if (callCount + 1 > maxRecursionDepth) {
            // reset counter and throw error
            callCounter.remove(scriptPath);
            throw new ExecutionError(
                    _("commands.cli.commands.ExecuteCommand.MaxCallCountExceed").formatted(maxRecursionDepth));
        }

        // execute script
        for (String line : script.split(System.lineSeparator())) {
            try {
                executeScriptLine(line);
            } catch (Exception e) {
                throw new ExecutionError(
                        _("commands.cli.commands.ExecuteCommand.LineError").formatted(scriptPath, e.getMessage()));
            }
        }

        // if whole script was executed decrease counter cause it is not recursive
        callCount = callCounter.get(scriptPath);
        if (callCount != null) {
            callCounter.put(scriptPath, callCount - 1);
        }
    }

    /**
     * Executes the command by reading the script file from a
     * {@link ScriptFileRequirement} provided by the given
     * {@link RequirementsPipeline}, parsing the script, and executing each line.
     *
     * @param pipeline The pipeline from which to obtain the
     *                 {@link ScriptFileRequirement}.
     * @param output   Unused in this implementation.
     * @throws ExecutionError If the script file cannot be read or if an error
     *                        occurs while executing any of the script
     *                        lines.
     */
    @Override
    public void execute(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
        Path scriptPath;
        try {
            scriptPath = pipeline.askRequirement(scriptFileRequirement);
        } catch (RequirementAskError e) {
            throw new ExecutionError(e.getMessage());
        }
        // read script file
        String scriptContent;
        try {
            scriptContent = Files.readString(scriptPath);
        } catch (IOException e) {
            throw new ExecutionError(e.getMessage(), e);
        }
        executeScript(scriptPath, scriptContent);
    }
}
