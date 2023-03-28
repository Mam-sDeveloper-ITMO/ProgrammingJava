package cliapp.commands.cli;

import cliapp.TextResources.Commands.Cli.ExitCommandResources;
import cliapp.TextResources.CatsResources;
import cliapp.cliclient.CLIClient;
import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.RequirementsPipeline;

/**
 * Exit from program
 */
public class ExitCommand extends CLICommand {
    public ExitCommand(CLIClient client) {
        super(ExitCommandResources.NAME, ExitCommandResources.DESCRIPTION, client);
    }

    @Override
    public void execute(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
        output
                .putString(ExitCommandResources.GOODBYE + System.lineSeparator() + CatsResources.CAT1);
        System.exit(0);
    }
}
