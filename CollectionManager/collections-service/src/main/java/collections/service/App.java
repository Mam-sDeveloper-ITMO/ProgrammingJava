package collections.service;

import collections.service.routers.CollectionsRouter;
import io.github.cdimascio.dotenv.Dotenv;
import pingback.Level;
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
        Logger logger = new Logger(
                dotenv.get("PINGBACK_APP_URL"),
                dotenv.get("PINGBACK_API_KEY"),
                dotenv.get("PINGBACK_PROJECT"),
                "mainLogger");
        System.out.println(dotenv.get("PINGBACK_API_KEY"));
        System.out.println(logger.log("Startup", "Starting up the collection service.", Level.DEBUG));

        Router collectionsRouter = new CollectionsRouter();

        Dispatcher dispatcher = new Dispatcher();
        dispatcher.includeRouter(collectionsRouter);

        try {
            Server server = new Server("localhost", 5000, dispatcher);
            server.run();
        } catch (Exception e) {
            logger.log("Server down", e.getMessage(), Level.CRITICAL);
        }
    }
}
