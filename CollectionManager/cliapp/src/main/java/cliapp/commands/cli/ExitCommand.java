package cliapp.commands.cli;

import cliapp.cliclient.CLIClient;
import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.RequirementsPipeline;
import textlocale.TextLocale;
import textlocale.TextSupplier;

/**
 * ExitCommand is a CLICommand that exits the program
 */
public class ExitCommand extends CLICommand {
    static TextSupplier ts = TextLocale.getPackage("commands.cli")::getText;

    /**
     * Constructor for ExitCommand class
     *
     * @param client the CLIClient instance
     */
    public ExitCommand(CLIClient client) {
        super(ts.t("ExitCommand.Name"), ts.t("ExitCommand.Description"),
                client);
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
        String cat = TextLocale.getPackage("cats").getText("Cat1");
        output.putString(ts.t("ExitCommand.Goodbye") + System.lineSeparator() + cat);
        System.exit(0);
    }
}