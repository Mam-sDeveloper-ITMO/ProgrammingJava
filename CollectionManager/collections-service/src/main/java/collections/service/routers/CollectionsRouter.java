package collections.service.routers;

import java.util.Map;

import server.Response;
import server.routing.Router;
import server.routing.Trigger;

public class CollectionsRouter extends Router {
    public CollectionsRouter() {
        super("collections");
    }

    @Trigger(trigger = "test")
    Response test(Map<String, ?> data) {
        return new Response(true, "test", null);
    }
}
