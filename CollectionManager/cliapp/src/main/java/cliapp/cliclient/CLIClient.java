package cliapp.cliclient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cliapp.TextResources.CatsResources;
import cliapp.cliclient.exceptions.CommandNotFoundError;
import cliapp.cliclient.exceptions.InlineParamsError;
import cliapp.utils.TextColor;
import commands.Command;
import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.Requirement;
import commands.requirements.RequirementsPipeline;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * CLIClient is a class that implements command-line interaction with the user.
 * 
 * It contains Command objects and methods for their execution. It provides
 * input
 * 
 * and output, commands resolving and execution, exception processing, and other
 * shell stuff.
 */
public class CLIClient {
    /**
     * 
     * Map with triggers associated with commands. Trigger is the name of the
     * command for
     * invoking from.
     */
    @Getter
    private final Map<String, Command> commands = new HashMap<>();

    /**
     * 
     * List of user input triggers.
     */
    @Getter
    private final List<String> history = new ArrayList<>();

    /**
     * 
     * If fuzzy matching is enabled, then CLI will try to find the command by the
     * prefix of the trigger.
     */
    @Setter
    @Getter
    private boolean fuzzyMatching = false;

    /**
     * 
     * Specify the number of attempts to ask for requirements from the user.
     * After this number of attempts, if the requirement is not satisfied, the
     * command will not be executed.
     */
    @Setter
    @Getter
    private int askRequirementAttempts = 3;

    /**
     * 
     * A supplier that provides user input from the command line in a safe way.
     */
    @Getter
    private Supplier<String> userInputSupplier = new SafeLineInput();

    /**
     * 
     * An output channel that prints messages to the console using
     * System.out.println().
     */
    @Getter
    private OutputChannel output = System.out::println;

    /**
     * 
     * Add a new command to the CLI. The trigger will be used as the name of the
     * command for invoking
     * from the command line.
     * 
     * @param trigger the trigger for the command
     * @param command the command object
     */
    public void registerCommand(String trigger, Command command) {
        commands.put(trigger, command);
    }

    /**
     * 
     * Remove the command associated with the specified trigger from the CLI.
     * 
     * @param trigger the trigger for the command
     */
    public void removeCommand(String trigger) {
        commands.remove(trigger);
    }

    /**
     * 
     * Find a trigger in the registered commands map by a full word or prefix if
     * fuzzy mode is enabled.
     * 
     * @param trigger the trigger for the command
     * @return the trigger associated with the command
     * @throws CommandNotFoundError if the command is not found
     */
    public String getTrigger(String trigger) throws CommandNotFoundError {
        if (commands.containsKey(trigger)) {
            return trigger;
        }
        if (fuzzyMatching) {
            for (String key : commands.keySet()) {
                if (key.startsWith(trigger)) {
                    return key;
                }
            }
        }
        throw new CommandNotFoundError(trigger);
    }

    /**
     * 
     * Normalize the trigger and return the command associated with it.
     * 
     * @param trigger the trigger to resolve
     * @return the command associated with the trigger
     * @throws CommandNotFoundError if the command is not found
     */
    public Command resolveCommand(String trigger) throws CommandNotFoundError {
        trigger = getTrigger(trigger);
        if (commands.containsKey(trigger)) {
            return commands.get(trigger);
        }
        throw new CommandNotFoundError(trigger);
    }

    /**
     * 
     * Split the user input line into separated, non-empty parameters. Parameters in
     * quotes are
     * matched as one parameter.
     * 
     * @param line the user input line
     * @return a list of separated words
     */
    public List<String> parseInlineParams(String line) {
        Pattern pattern = Pattern.compile("(\"[^\"]+\"|\\S+)");
        Matcher matcher = pattern.matcher(line);
        ArrayList<String> params = new ArrayList<String>();
        while (matcher.find()) {
            String item = matcher.group(1);
            if (item != null) {
                params.add(item);
            } else {
                params.add(matcher.group(2));
            }
        }
        return params;
    }

    /**
     * 
     * Map static requirement names to specified inline parameters.
     * 
     * @param staticRequirements a list of static requirements to map
     * @param inlineParams       a list of inline parameters to map to the
     *                           requirements
     * @return a map where the key is the requirement name and the value is the
     *         inline parameter
     * @throws InlineParamsError if the number of static requirements and inline
     *                           parameters do not match
     */
    public Map<String, String> mapStaticRequirements(List<Requirement<?, ?>> staticRequirements,
            List<String> inlineParams)
            throws InlineParamsError {

        if (staticRequirements.size() != inlineParams.size()) {
            throw new InlineParamsError(staticRequirements);
        }
        Map<String, String> map = new HashMap<String, String>();
        for (int i = 0; i < staticRequirements.size(); i++) {
            map.put(staticRequirements.get(i).getName(), inlineParams.get(i));
        }
        return map;
    }

    /**
     * 
     * Execute a command with specified inline parameters.
     * 
     * @param command      the command to execute
     * @param inlineParams the inline parameters to pass to the command
     */
    public void executeCommand(Command command, List<String> inlineParams) {
        // map static requirements to inline params
        Map<String, String> staticRequirementsMap;
        try {
            staticRequirementsMap = mapStaticRequirements(command.getStaticRequirements(), inlineParams);
        } catch (InlineParamsError e) {
            System.out.println(TextColor.getColoredString(e.getMessage(), TextColor.RED));
            return;
        }
        // init requirements pipeline for specified params
        RequirementsPipeline pipeline = new UserInputPipeline(
                staticRequirementsMap,
                askRequirementAttempts,
                userInputSupplier);
        // try execute command
        try {
            command.execute(pipeline, output);
        } catch (ExecutionError e) {
            System.out.println(TextColor.getColoredString(e.getMessage(), TextColor.RED));
        }
    }

    /**
     * 
     * Run an infinite loop for command line interaction.
     * Waits for user input and tries to resolve and execute a command.
     */
    public void runClient() {
        System.out.println(CatsResources.CAT3);
        System.out.println();
        while (true) {
            // ASAP IMMEDIATELY REFACTOR
            try {
                // cute arrows
                System.out.print(">> ");
                // get user input and parse on separated words
                // if line is empty, then skip it
                List<String> inlineParams = parseInlineParams(userInputSupplier.get());
                if (inlineParams.size() == 0) {
                    continue;
                }
                // try to resolve command by trigger
                Command command;
                String trigger = inlineParams.get(0);
                inlineParams.remove(0);
                try {
                    trigger = getTrigger(trigger);
                    command = resolveCommand(trigger);
                } catch (CommandNotFoundError e) {
                    System.out.println(TextColor.getColoredString(e.getMessage(), TextColor.RED));
                    continue;
                }
                // execute command
                executeCommand(command, inlineParams);
                // save trigger to history
                history.add(trigger);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("sorry :(");
            }
        }
    }
}
