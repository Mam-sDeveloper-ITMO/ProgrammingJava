package cliapp.commands.cli;

import cliapp.Messages;
import cliapp.cliclient.CLIClient;
import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.RequirementsPipeline;

/**
 * Exit from program
 */
public class ExitCommand extends CLICommand {
    public ExitCommand(CLIClient client) {
        super(Messages.ExitCommand.NAME, Messages.ExitCommand.DESCRIPTION, client);
    }

    @Override
    public void execute(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
        output
            .putString(Messages.ExitCommand.GOODBYE + System.lineSeparator() + Messages.Cats.CAT1);
        System.exit(0);
    }
}
