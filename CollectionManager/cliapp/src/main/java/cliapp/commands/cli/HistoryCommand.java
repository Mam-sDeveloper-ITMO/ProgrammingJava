package cliapp.commands.cli;

import java.util.List;

import cliapp.TextResources.Commands.Cli.HistoryCommandResources;
import cliapp.cliclient.CLIClient;
import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.RequirementsPipeline;

/**
 * Show list of all registered commands with description and static requirements
 */
public class HistoryCommand extends CLICommand {
    public HistoryCommand(CLIClient client) {
        super(HistoryCommandResources.NAME, HistoryCommandResources.DESCRIPTION, client);
    }

    @Override
    public void execute(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
        List<String> history = client.getHistory();

        if (history.size() == 0) {
            output.putString(HistoryCommandResources.HISTORY_EMPTY);
        } else {
            output.putString(HistoryCommandResources.HISTORY_TITLE);
            for (String trigger : history) {
                output.putString("- " + trigger);
            }
        }
    }
}
