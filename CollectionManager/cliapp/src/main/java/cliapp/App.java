package cliapp;

import java.io.IOException;

import cliapp.cliclient.CLIClient;
import cliapp.commands.cli.ExitCommand;
import cliapp.commands.cli.HelpCommand;
import cliapp.commands.collection.AddElementCommand;
import cliapp.commands.collection.ClearCommand;
import cliapp.commands.collection.InfoCommand;
import cliapp.commands.collection.RemoveByIdCommand;
import cliapp.commands.collection.ShowCommand;
import cliapp.commands.collection.UpdateElementCommand;
import humandeque.manager.CollectionManager;
import humandeque.manager.local.LocalManager;

public class App {
    public static void main(String[] args) {
        // depends on args count we create new collection or load from file
        CollectionManager manager;
        if (args.length == 0) {
            manager = new LocalManager();
        } else if (args.length == 1) {
            try {
                manager = new LocalManager(args[0]);
            } catch (IOException e) {
                System.out.println("Cannot load collection from file");
                return;
            }
        } else {
            System.out.println("Incorrect command line arguments. Usage: java -jar cliapp.jar [file]");
            return;
        }

        // create client and register commands
        CLIClient client = new CLIClient();
        // Collection commands
        client.registerCommand("info", new InfoCommand(manager));
        client.registerCommand("show", new ShowCommand(manager));
        client.registerCommand("add", new AddElementCommand(manager));
        client.registerCommand("update", new UpdateElementCommand(manager));
        client.registerCommand("remove_by_id", new RemoveByIdCommand(manager));
        client.registerCommand("clear", new ClearCommand(manager));
        // CLI commands
        client.registerCommand("help", new HelpCommand(client));
        client.registerCommand("exit", new ExitCommand(client));

        // let's go!
        client.runClient();
    }
}
