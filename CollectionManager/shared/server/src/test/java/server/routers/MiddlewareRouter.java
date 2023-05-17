package server.routers;

import java.util.HashMap;
import java.util.Map;

import server.requests.Request;
import server.responses.Response;
import server.routing.Router;
import server.routing.handlers.Handler;
import server.routing.handlers.HandlerFunction;
import server.routing.handlers.exceptions.IncorrectHandlerParams;
import server.routing.handlers.exceptions.IncorrectHandlerReturns;
import server.routing.middlewares.InnerMiddleware;
import server.routing.middlewares.OuterMiddleware;

public class MiddlewareRouter extends Router {
    public MiddlewareRouter(String prefix) throws IncorrectHandlerParams, IncorrectHandlerReturns {
        super(prefix);
    }

    @InnerMiddleware("foo")
    Response foo(HandlerFunction handler, Request request) {
        return Response.success("Hello from foo!", null);
    }

    @InnerMiddleware("")
    Response all(HandlerFunction handler, Request request) {
        return Response.success("Hello from all!", null);
    }

    @Handler("foo")
    Response foo(Map<String, ?> data) {
        return new Response(true, "Hello from foo", data);
    }

    @Handler("bar")
    Response bar(Map<String, ?> data) {
        return new Response(true, "Hello from bar", data);
    }

    @OuterMiddleware("foo")
    Response foo(Response response) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("hint", "foo");
        return Response.success("Hello from foo!", data);
    }

    @OuterMiddleware("")
    Response all(Response response) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("hint", "all");
        return Response.success("Hello from all!", data);
    }
}
