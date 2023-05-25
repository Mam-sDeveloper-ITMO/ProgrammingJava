package cliapp.commands.cli;

import static textlocale.TextLocale._;

import cliapp.cliclient.CLIClient;
import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.RequirementsPipeline;

/**
 * ExitCommand is a CLICommand that exits the program
 */
public class ExitCommand extends CLICommand {

    /**
     * Constructor for ExitCommand class
     *
     * @param client the CLIClient instance
     */
    public ExitCommand(CLIClient client) {
        super(_("commands.cli.commands.ExitCommand.Name"), _("commands.cli.commands.ExitCommand.Description"), client);
    }

    /**
     * Executes the command by outputting a goodbye message and exiting the program
     *
     * @param pipeline the RequirementsPipeline instance
     * @param output   the OutputChannel instance
     * @throws ExecutionError if an error occurs during execution
     */
    @Override
    public void execute(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
        output.putString(_("commands.cli.commands.ExitCommand.Goodbye") + System.lineSeparator() + _("cats.Cat1"));
        System.exit(0);
    }
}