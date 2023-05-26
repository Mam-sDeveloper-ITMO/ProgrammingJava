package server.routers;

import java.io.Serializable;
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
        return Response.success("Hello from foo!", 200);
    }

    @InnerMiddleware("")
    Response all(HandlerFunction handler, Request request) {
        return Response.success("Hello from all!");
    }

    @Handler("foo")
    Response foo(Map<String, Serializable> data) {
        return new Response(true, "Hello from foo", data, 200);
    }

    @Handler("bar")
    Response bar(Map<String, Serializable> data) {
        return new Response(true, "Hello from bar", data, 200);
    }

    @OuterMiddleware("foo")
    Response foo(Request request, Response response) {
        HashMap<String, Serializable> data = new HashMap<>();
        data.put("hint", "foo");
        return Response.success("Hello from foo!", data, 200);
    }

    @OuterMiddleware("")
    Response all(Request request, Response response) {
        HashMap<String, Serializable> data = new HashMap<>();
        data.put("hint", "all");
        return Response.success("Hello from all!", data, 200);
    }
}
