package cliapp.commands.cli;

import java.util.HashMap;
import java.util.List;

import cliapp.TextResources.Commands.Cli.HelpCommandResources;
import cliapp.cliclient.CLIClient;
import cliapp.utils.TextColor;
import commands.Command;
import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.Requirement;
import commands.requirements.RequirementsPipeline;

/**
 * Show list of all registered commands with description and static requirements
 */
public class HelpCommand extends CLICommand {
    public HelpCommand(CLIClient client) {
        super(HelpCommandResources.NAME, HelpCommandResources.DESCRIPTION, client);
    }

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

    @Override
    public void execute(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
        HashMap<String, Command> commands = client.getCommands();

        StringBuilder builder = new StringBuilder();
        builder.append(HelpCommandResources.COMMANDS_TITLE).append(System.lineSeparator());
        for (String trigger : commands.keySet()) {
            Command command = commands.get(trigger);
            builder.append(getCommandLine(trigger, command)).append(System.lineSeparator());
        }
        output.putString(builder.toString());
    }
}
