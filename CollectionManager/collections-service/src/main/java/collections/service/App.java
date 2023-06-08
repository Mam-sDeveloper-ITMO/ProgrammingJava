package collections.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;

import collections.service.collections.storage.CollectionsStorage;
import collections.service.models.HumanModel;
import collections.service.routers.CollectionsRouter;
import fqme.connection.ConnectionManager;
import fqme.connection.DBConfig;
import fqme.model.Model;
import fqme.view.View;
import io.github.cdimascio.dotenv.Dotenv;
import pingback.Level;
import pingback.LogMode;
import pingback.Logger;
import server.Server;
import server.dispatcher.Dispatcher;
import server.routing.Router;

/**
 * The main application class for running the collection service.
 */
public class App {
    private static Dotenv dotenv = Dotenv.load();

    /**
     * Create logger instance based on the environment variables.
     *
     * - PINGBACK_APP_URL: The URL of the Pingback application.
     * - PINGBACK_API_KEY: The API key of the Pingback application.
     * - PINGBACK_PROJECT: The project name of the Pingback application.
     * - PINGBACK_LOG_MODE: The log mode of the Pingback application.
     *
     * @return Logger instance
     * @see pingback.Logger
     */
    private static Logger setupLogger() {
        Logger logger = Logger.getLogger(
                dotenv.get("PINGBACK_APP_URL"),
                dotenv.get("PINGBACK_API_KEY"),
                dotenv.get("PINGBACK_PROJECT"),
                "mainLogger");

        if (dotenv.get("PINGBACK_LOG_MODE").equals("BOTH")) {
            logger.setLogMode(LogMode.BOTH);
        } else if (dotenv.get("PINGBACK_LOG_MODE").equals("CONSOLE")) {
            logger.setLogMode(LogMode.CONSOLE);
        }
        return logger;
    }

    private static void setupDatabase() throws Exception {
        // register models
        Model.register(HumanModel.class);

        // configure database config
        DBConfig dbConfig = DBConfig.fromConfigFile(dotenv.get("COLLECTIONS_DB_CONFIG"));
        ConnectionManager.bind(HumanModel.class, dbConfig);

        // create tables
        Connection connection = ConnectionManager.getConnection(HumanModel.class);
        View.of(HumanModel.class, connection);
    }

    /**
     * Application entry point.
     *
     * Configure logger, database and start the server.
     *
     */
    public static void main(String[] args) {
        Logger logger = setupLogger();

        try {
            setupDatabase();
        } catch (Exception e) {
            logger.log("Database error", e.getMessage(), Level.CRITICAL);
            return;
        }

        CollectionsStorage collectionsDispatcher = new CollectionsStorage("./storage");

        Router collectionsRouter = new CollectionsRouter(collectionsDispatcher);

        Dispatcher dispatcher = new Dispatcher();
        dispatcher.includeRouter(collectionsRouter);

        try {
            Integer port = Integer.parseInt(dotenv.get("PORT"));
            Server server = new Server("localhost", port, dispatcher);
            logger.log("Server up", "Server is running on port " + port, Level.INFO);
            server.run();
        } catch (Exception e) {
            logger.log("Server down", e.getMessage(), Level.CRITICAL);
        }
    }
}
