package cliapp.commands.cli;

import java.util.HashMap;
import java.util.List;

import cliapp.Messages;
import cliapp.cliclient.CLIClient;
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
        super(Messages.HelpCommand.NAME, Messages.HelpCommand.DESCRIPTION, client);
    }

    private String getCommandLine(String trigger, Command command) {
        StringBuilder builder = new StringBuilder();
        // trigger and description
        builder.append("\t")
                .append(trigger)
                .append("\t")
                .append(command.getDescription());

        List<Requirement<?, ?>> staticRequirements = command.getStaticRequirements();
        if (!staticRequirements.isEmpty()) {
            builder.append("\n")
                    .append("\t")
                    .append(Messages.HelpCommand.HELP_INLINE_PARAMS + "\n");
            for (Requirement<?, ?> requirement : staticRequirements) {
                builder.append("\t\t")
                        .append(requirement.getName())
                        .append(" - ")
                        .append(requirement.getDescription())
                        .append("\n");
            }
        }
        return builder.toString();
    }

    @Override
    public void execute(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
        HashMap<String, Command> commands = client.getCommands();

        StringBuilder builder = new StringBuilder();
        builder.append(Messages.HelpCommand.TITLE).append("\n");
        for (String trigger : commands.keySet()) {
            Command command = commands.get(trigger);
            builder.append(getCommandLine(trigger, command)).append("\n");
        }
        output.putString(builder.toString());
    }
}
