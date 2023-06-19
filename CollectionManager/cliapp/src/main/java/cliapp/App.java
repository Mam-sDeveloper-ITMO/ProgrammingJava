package cliapp;

import java.util.List;
import java.util.Optional;

import adapter.Adapter;
import adapter.common.AuthAdapter;
import auth.AuthToken;
import cliapp.cliclient.CLIClient;
import cliapp.collection.RemoteManager;
import cliapp.commands.cli.ExecuteCommand;
import cliapp.commands.cli.ExitCommand;
import cliapp.commands.cli.HelpCommand;
import cliapp.commands.cli.HistoryCommand;
import cliapp.commands.cli.LoginCommand;
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
import commands.Command;
import humandeque.manager.CollectionManager;
import textlocale.text.TextSupplier;

/**
 * The main application class for running the space collection manager.
 */
public class App {
    private static final TextSupplier ts = TextsManager.getTexts()::getText;

    private static Adapter initAuthAdapter() {
        try {
            return new Adapter("127.0.0.1", 8003);
        } catch (Exception e) {
            System.out.println(ts.t("app.ConnectLater"));
            return null;
        }
    }

    private static void registerCliCommands(CLIClient client) {
        client.registerCommand("help", new HelpCommand(client));
        client.registerCommand("exit", new ExitCommand(client));
        client.registerCommand("history", new HistoryCommand(client));
        client.registerCommand("fuzzy", new SetFuzzyCommand(client));
        client.registerCommand("execute", new ExecuteCommand(client));
    }

    private static void registerCollectionCommands(CLIClient client, CollectionManager manager) {
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
    }

    private static AuthToken login(Adapter authAdapter, CLIClient client) {
        AuthToken[] tokenContainer = new AuthToken[1];
        Command loginCommand = new LoginCommand(client, authAdapter, tokenContainer);
        client.executeCommand(loginCommand, List.of());
        return tokenContainer[0];
    }

    private static AuthAdapter initCollectionsAdapter(AuthToken token) {
        try {
            AuthAdapter collectionsAdapter = new AuthAdapter("127.0.0.1", 8000);
            collectionsAdapter.setToken(Optional.of(token));
            return collectionsAdapter;
        } catch (Exception e) {
            System.out.println(ts.t("app.ConnectLater"));
            return null;
        }
    }

    private static CollectionManager initCollectionManager(AuthAdapter collectionsAdapter) {
        try {
            return new RemoteManager(collectionsAdapter);
        } catch (Exception e) {
            System.out.println(ts.t("app.ConnectLater"));
            return null;
        }
    }

    /**
     * The entry point for the application. Initializes the collection manager and
     * the CLI client, and registers all commands.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        CLIClient client = new CLIClient();
        registerCliCommands(client);

        Adapter authAdapter = initAuthAdapter();
        if (authAdapter == null) {
            System.exit(1);
        }

        AuthToken token = login(authAdapter, client);
        if (token == null) {
            System.exit(1);
        }

        AuthAdapter collectionsAdapter = initCollectionsAdapter(token);
        if (collectionsAdapter == null) {
            System.exit(1);
        }

        CollectionManager manager = initCollectionManager(collectionsAdapter);
        if (manager == null) {
            System.exit(1);
        }

        registerCollectionCommands(client, manager);

        // Let's go!
        client.runClient();
    }
}
