package cliapp.commands.cli;

import java.util.List;

import cliapp.TextResources.Commands.Cli.HistoryCommandResources;
import cliapp.cliclient.CLIClient;
import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.RequirementsPipeline;

/**
 * Show list of all previously executed commands
 */
public class HistoryCommand extends CLICommand {
    /**
     * Constructor for HistoryCommand
     * 
     * @param client the instance of {@link CLIClient} class
     */
    public HistoryCommand(CLIClient client) {
        super(HistoryCommandResources.NAME, HistoryCommandResources.DESCRIPTION, client);
    }

    /**
     * Show the list of all previously executed commands
     * 
     * @param pipeline an instance of {@link RequirementsPipeline} class
     * @param output   an instance of {@link OutputChannel} class
     * @throws ExecutionError if there is an error executing the command
     */
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