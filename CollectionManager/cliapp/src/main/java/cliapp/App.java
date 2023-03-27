package cliapp;

import java.io.IOException;

import cliapp.cliclient.CLIClient;
import cliapp.commands.cli.ExitCommand;
import cliapp.commands.cli.HelpCommand;
import cliapp.commands.cli.HistoryCommand;
import cliapp.commands.cli.SetFuzzyCommand;
import cliapp.commands.collection.AddElementCommand;
import cliapp.commands.collection.AverageOfImpactSpeedCommand;
import cliapp.commands.collection.ClearCommand;
import cliapp.commands.collection.FilterByImpactSpeed;
import cliapp.commands.collection.HeadCommand;
import cliapp.commands.collection.InfoCommand;
import cliapp.commands.collection.PrintSortedCommand;
import cliapp.commands.collection.RandomCommand;
import cliapp.commands.collection.RemoveByIdCommand;
import cliapp.commands.collection.RemoveFirstCommand;
import cliapp.commands.collection.RemoveLastCommand;
import cliapp.commands.collection.ShowCommand;
import cliapp.commands.collection.TailCommand;
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
            System.out
                    .println("Incorrect command line arguments. Usage: java -jar cliapp.jar [file]");
            return;
        }

        // create client and register commands
        CLIClient client = new CLIClient();
        // Collection commands
        client.registerCommand("info", new InfoCommand(manager));
        client.registerCommand("show", new ShowCommand(manager));
        client.registerCommand("add", new AddElementCommand(manager));
        client.registerCommand("update", new UpdateElementCommand(manager));
        client.registerCommand("remove_first", new RemoveFirstCommand(manager));
        client.registerCommand("remove_last", new RemoveLastCommand(manager));
        client.registerCommand("remove_by_id", new RemoveByIdCommand(manager));
        client.registerCommand("print_sorted", new PrintSortedCommand(manager));
        client.registerCommand("random", new RandomCommand(manager));
        client.registerCommand("clear", new ClearCommand(manager));
        client.registerCommand("average_speed", new AverageOfImpactSpeedCommand(manager));
        client.registerCommand("head", new HeadCommand(manager));
        client.registerCommand("tail", new TailCommand(manager));
        client.registerCommand("filter", new FilterByImpactSpeed(manager));

        // CLI commands
        client.registerCommand("help", new HelpCommand(client));
        client.registerCommand("exit", new ExitCommand(client));
        client.registerCommand("history", new HistoryCommand(client));
        client.registerCommand("fuzzy", new SetFuzzyCommand(client));

        // let's go!
        client.runClient();
    }
}
