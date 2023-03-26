package cliapp.cliclient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import cliapp.Messages;
import cliapp.cliclient.exceptions.CommandNotFoundError;
import cliapp.cliclient.exceptions.InlineParamsCountError;
import cliapp.utils.TextColor;
import commands.Command;
import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.Requirement;
import commands.requirements.RequirementsPipeline;
import lombok.Cleanup;
import lombok.Getter;
import lombok.Setter;

/**
 * CLIClient implement command line interaction with user.
 * 
 * It contains Command objects and methods for them execution. It provides input and output,
 * commands resolving and execution, exceptions processing and other shell stuff
 */
public class CLIClient {
    /**
     * Map with triggers associated with commands. Trigger is name of command for invoke from
     */
    @Getter private HashMap<String, Command> commands = new HashMap<String, Command>();

    /**
     * List of user input triggers
     */
    @Getter private List<String> history = new ArrayList<String>();

    /**
     * If fuzzy matching enabled, then cli will try to find command by prefix of trigger
     */
    @Setter @Getter private boolean fuzzyMatching = false;

    /**
     * Specify count of attempts for ask requirements from user After this count of attempts, if
     * requirement not satisfied, then command will not be executed
     */
    @Setter @Getter private int askRequirementAttempts = 3;

    /**
     * Add new command to cli. Trigger will be used as name of command for invoke from command line
     */
    public void registerCommand(String trigger, Command command) {
        commands.put(trigger, command);
    }

    /**
     * Remove command associated with specified trigger from cli
     */
    public void removeCommand(String trigger) {
        commands.remove(trigger);
    }

    /**
     * Try to find command by trigger or prefix (if fuzzy matching enabled). If command not found,
     * then throw CommandNotFound exception
     */
    public Command resolveCommand(String trigger) throws CommandNotFoundError {
        if (commands.containsKey(trigger)) {
            return commands.get(trigger);
        } else if (fuzzyMatching) {
            for (String key : commands.keySet()) {
                if (key.startsWith(trigger)) {
                    return commands.get(key);
                }
            }
        }
        throw new CommandNotFoundError(trigger);
    }

    /**
     * Split user input line to separated non-empty words
     * 
     * @param line - user input line
     * @return List of separated words
     */
    public List<String> parseInlineParams(String line) {
        List<String> params = new ArrayList<String>();
        String[] split = line.split(" ");
        for (String param : split) {
            if (param.length() > 0) {
                params.add(param);
            }
        }
        return params;
    }

    /**
     * Map static requirements names to specified inline params
     * 
     * @return Map where key is requirement name and value is inline param
     */
    public Map<String, String> mapStaticRequirements(List<Requirement<?, ?>> staticRequirements,
        List<String> inlineParams)
        throws InlineParamsCountError {

        if (staticRequirements.size() != inlineParams.size()) {
            throw new InlineParamsCountError(staticRequirements.size(), inlineParams.size());
        }
        Map<String, String> map = new HashMap<String, String>();
        for (int i = 0; i < staticRequirements.size(); i++) {
            map.put(staticRequirements.get(i).getName(), inlineParams.get(i));
        }
        return map;
    }

    /**
     * Execute one command with specified inline params.
     * 
     * Handle exceptions and print error messages
     */
    public void executeCommand(Command command, List<String> inlineParams) {
        // map static requirements to inline params
        Map<String, String> staticRequirementsMap;
        try {
            staticRequirementsMap =
                mapStaticRequirements(command.getStaticRequirements(), inlineParams);
        } catch (InlineParamsCountError e) {
            System.out.println(TextColor.getColoredString(e.getMessage(), TextColor.RED));
            return;
        }
        // init output channel and requirements pipeline
        OutputChannel output = System.out::println;
        RequirementsPipeline pipeline = new UserInputPipeline(
            staticRequirementsMap,
            new Scanner(System.in),
            askRequirementAttempts);
        // try execute command
        try {
            command.execute(pipeline, output);
        } catch (ExecutionError e) {
            System.out.println(TextColor.getColoredString(e.getMessage(), TextColor.RED));
        }
    }

    /**
     * Run infinite loop for command line interaction
     * 
     * Wait user input and try resolve and execute command
     */
    public void runClient() {
        System.out.println(Messages.Cats.CAT3);
        System.out.println();
        // create user input inputScanner
        @Cleanup
        Scanner inputScanner = new Scanner(System.in);
        while (true) {
            // cute arrows
            System.out.print(">> ");
            // get user input and parse on separated words
            // if line is empty, then skip it
            List<String> inlineParams = parseInlineParams(inputScanner.nextLine());
            if (inlineParams.size() == 0) {
                continue;
            }
            // try to resolve command by trigger
            Command command;
            String trigger = inlineParams.get(0);
            inlineParams.remove(0);
            try {
                command = resolveCommand(trigger);
            } catch (CommandNotFoundError e) {
                System.out.println(TextColor.getColoredString(e.getMessage(), TextColor.RED));
                continue;
            }
            // execute command
            executeCommand(command, inlineParams);
            // save trigger to history
            history.add(trigger);
        }
    }
}
