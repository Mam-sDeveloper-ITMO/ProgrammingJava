package cliapp.commands.cli;

import cliapp.cliclient.CLIClient;
import commands.Command;

/**
 * Basic class of all commands, that use CLIClient as receiver
 */
public abstract class CLICommand extends Command {
    protected CLIClient client;

    public CLICommand(String name, String description, CLIClient client) {
        super(name, description);
        this.client = client;
    }
}
