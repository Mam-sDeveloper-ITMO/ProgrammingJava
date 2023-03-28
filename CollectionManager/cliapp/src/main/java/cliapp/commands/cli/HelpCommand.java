package cliapp.commands.cli;

import java.util.List;
import java.util.Map;

import cliapp.TextResources.Commands.Cli.HelpCommandResources;
import cliapp.cliclient.CLIClient;
import cliapp.utils.TextColor;
import commands.Command;
import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.Requirement;
import commands.requirements.RequirementsPipeline;

/**
 * HelpCommand is a CLICommand that shows a list of all registered commands with
 * their descriptions and static requirements
 */
public class HelpCommand extends CLICommand {

    /**
     * Constructor for HelpCommand class
     *
     * @param client the CLIClient instance
     */
    public HelpCommand(CLIClient client) {
        super(HelpCommandResources.NAME, HelpCommandResources.DESCRIPTION, client);
    }

    /**
     * Returns a string representation of a command line that includes its trigger,
     * description, and static requirements
     *
     * @param trigger the trigger string of the command
     * @param command the Command instance
     * @return a string representation of the command line
     */
    private String getCommandLine(String trigger, Command command) {
        StringBuilder builder = new StringBuilder();
        // trigger and description
        builder.append("\t")
                .append(TextColor.getColoredString(trigger, TextColor.CYAN))
                .append("\t")
                .append(TextColor.getColoredString(command.getDescription(), TextColor.YELLOW));

        List<Requirement<?, ?>> staticRequirements = command.getStaticRequirements();
        if (!staticRequirements.isEmpty()) {
            builder.append(System.lineSeparator())
                    .append("\t")
                    .append(HelpCommandResources.COMMANDS_INLINE_PARAMS + System.lineSeparator());
            for (Requirement<?, ?> requirement : staticRequirements) {
                builder.append("\t\t")
                        .append(TextColor.getColoredString(requirement.getName(), TextColor.BLUE))
                        .append(" - ")
                        .append(
                                TextColor.getColoredString(requirement.getDescription(), TextColor.BLUE))
                        .append(System.lineSeparator());
            }
        }
        return builder.toString();
    }

    /**
     * Executes the command by outputting a list of all registered commands with
     * their descriptions and static requirements
     *
     * @param pipeline the RequirementsPipeline instance
     * @param output   the OutputChannel instance
     * @throws ExecutionError if an error occurs during execution
     */
    @Override
    public void execute(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
        Map<String, Command> commands = client.getCommands();

        StringBuilder builder = new StringBuilder();
        builder.append(HelpCommandResources.COMMANDS_TITLE).append(System.lineSeparator());
        for (String trigger : commands.keySet()) {
            Command command = commands.get(trigger);
            builder.append(getCommandLine(trigger, command)).append(System.lineSeparator());
        }
        output.putString(builder.toString());
    }
}