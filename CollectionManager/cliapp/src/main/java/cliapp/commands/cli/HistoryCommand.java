package cliapp.commands.cli;

import static textlocale.TextLocale.t;

import java.util.List;

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
        super(t("commands.cli.commands.HistoryCommand.Name"),
                t("commands.cli.commands.HistoryCommand.Description"),
                client);
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
            output.putString(t("commands.cli.commands.HistoryCommand.Empty"));
        } else {
            output.putString(t("commands.cli.commands.HistoryCommand.Title"));
            for (String trigger : history) {
                output.putString("- " + trigger);
            }
        }
    }
}