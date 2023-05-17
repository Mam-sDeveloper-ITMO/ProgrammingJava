package server.routers;

import server.responses.Response;
import server.routing.Router;
import server.routing.exceptions.IncorrectHandlerParams;
import server.routing.exceptions.IncorrectHandlerReturns;
import server.routing.handlers.Handler;

import java.util.Map;

public class BasicRouter extends Router {
    public BasicRouter() throws IncorrectHandlerParams, IncorrectHandlerReturns {
        super("testPrefix");
        System.out.println();
    }

    public BasicRouter(String prefix) throws IncorrectHandlerParams, IncorrectHandlerReturns {
        super(prefix);
    }

    @Handler("foo")
    Response foo(Map<String, ?> data) {
        return new Response(true, "Hello from foo", data);
    }

    @Handler("bar")
    Response bar(Map<String, ?> data) {
        return new Response(true, "Hello from bar", data);
    }
}
