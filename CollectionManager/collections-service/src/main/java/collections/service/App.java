package collections.service;

import collections.service.collections.storage.CollectionsStorage;
import collections.service.routers.CollectionsRouter;
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
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
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
