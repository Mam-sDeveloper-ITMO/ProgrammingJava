package server.routers;

import java.io.Serializable;
import java.util.Map;

import server.responses.Response;
import server.routing.Router;
import server.routing.handlers.Handler;
import server.routing.handlers.exceptions.IncorrectHandlerParams;
import server.routing.handlers.exceptions.IncorrectHandlerReturns;

public class BasicRouter extends Router {
    public BasicRouter() throws IncorrectHandlerParams, IncorrectHandlerReturns {
        super("testPrefix");
        System.out.println();
    }

    public BasicRouter(String prefix) throws IncorrectHandlerParams, IncorrectHandlerReturns {
        super(prefix);
    }

    @Handler("foo")
    Response foo(Map<String, Serializable> data) {
        return new Response(true, "Hello from foo", data, 200);
    }

    @Handler("bar")
    Response bar(Map<String, Serializable> data) {
        return new Response(true, "Hello from bar", data, 200);
    }

    @Handler("bar.sub")
    Response barSub(Map<String, Serializable> data) {
        return new Response(true, "Hello from bar.sub", data, 200);
    }
}
