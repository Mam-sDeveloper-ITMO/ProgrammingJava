package cliapp.commands.cli;

import cliapp.cliclient.CLIClient;
import commands.Command;

/**
 * A basic class for all commands that use a CLIClient as a receiver.
 */
public abstract class CLICommand extends Command {
    protected CLIClient client;

    /**
     * Constructor for CLICommand.
     *
     * @param name the name of the command
     * @param description a brief description of what the command does
     * @param client the CLIClient to be used as a receiver for the command
     */
    public CLICommand(String name, String description, CLIClient client) {
        super(name, description);
        this.client = client;
    }
}
