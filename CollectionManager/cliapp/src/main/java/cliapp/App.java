package cliapp;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

import adapter.Adapter;
import cliapp.cliclient.CLIClient;
import cliapp.collection.RemoteManager;
import cliapp.commands.cli.ExecuteCommand;
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
import cliapp.commands.collection.SaveCommand;
import cliapp.commands.collection.ShowCommand;
import cliapp.commands.collection.TailCommand;
import cliapp.commands.collection.UpdateElementCommand;
import humandeque.manager.CollectionManager;
import humandeque.manager.local.LocalManager;

/**
 * The main application class for running the space collection manager.
 */
public class App {

    /**
     * Initializes the collection manager.
     *
     * @param args The command line arguments, which must include the path to the
     *             collection file.
     *
     * @return The newly-initialized collection manager.
     *
     * @throws InvalidPathException If the specified file path is invalid.
     */
    private static CollectionManager initManager(String[] args) throws InvalidPathException {
        try {
            String filePath = args[args.length - 1];
            Paths.get(filePath);
        } catch (IndexOutOfBoundsException | InvalidPathException e) {
            System.out.println(TextResources.App.INCORRECT_ARGS);
            System.exit(1);
        }

        try {
            return new LocalManager(args[args.length - 1]);
        } catch (Exception e) {
            System.out.println(TextResources.App.CANNOT_CREATE_MANAGER.formatted(e.getMessage()));
            System.exit(1);
        }
        System.exit(1);
        return null;
    }

    /**
     * The entry point for the application. Initializes the collection manager and
     * the CLI client, and registers all commands.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        // CollectionManager manager = initManager(args);
        Adapter serviceAdapter = null;
        try {
            serviceAdapter = new Adapter("127.0.0.1", 8000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        CollectionManager manager = null;
        try {
            manager = new RemoteManager(serviceAdapter, 1);
        } catch (Exception e) {
            e.printStackTrace();
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
        client.registerCommand("save", new SaveCommand(manager));

        // CLI commands
        client.registerCommand("help", new HelpCommand(client));
        client.registerCommand("exit", new ExitCommand(client));
        client.registerCommand("history", new HistoryCommand(client));
        client.registerCommand("fuzzy", new SetFuzzyCommand(client));
        client.registerCommand("execute", new ExecuteCommand(client));

        // let's go!
        client.runClient();
    }
}
