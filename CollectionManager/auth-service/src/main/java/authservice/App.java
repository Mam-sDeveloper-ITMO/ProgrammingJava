package authservice;

import java.sql.Connection;

import authservice.dbmodels.AccountModel;
import authservice.routers.AuthRouter;
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
     * Environment variables:
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

    /**
     * Setup database connection and create tables.
     *
     * Environment variables:
     * - COLLECTIONS_DB_CONFIG: The path to the database configuration file.
     *
     * @throws Exception
     *
     * @see fqme.connection.ConnectionManager
     */
    private static void setupDatabase() throws Exception {
        // register models
        Model.register(AccountModel.class);

        // configure database config
        DBConfig dbConfig = DBConfig.fromConfigFile(dotenv.get("COLLECTIONS_DB_CONFIG"));
        ConnectionManager.bind(AccountModel.class, dbConfig);

        // create tables
        Connection connection = ConnectionManager.getConnection(AccountModel.class);
        View.of(AccountModel.class, connection);
    }

    /**
     * Application entry point.
     *
     * Configure logger, database and start the server.
     *
     * Environment variables:
     * - PORT: The port number of the server.
     */
    public static void main(String[] args) {
        Logger logger = setupLogger();

        try {
            setupDatabase();
        } catch (Exception e) {
            logger.log("Database error", e.getMessage(), Level.CRITICAL);
            return;
        }

        Router authRouter = new AuthRouter();
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.includeRouter(authRouter);

        try {
            Integer port = Integer.parseInt(dotenv.get("AUTH_SERVICE_PORT"));
            Server server = new Server("127.0.0.1", port, dispatcher);
            server.setThreadPoolSize(1);
            logger.log("Server up", "Server is running on port " + port, Level.INFO);
            server.run();
        } catch (Exception e) {
            logger.log("Server down", e.getMessage(), Level.CRITICAL);
        }
    }
}
