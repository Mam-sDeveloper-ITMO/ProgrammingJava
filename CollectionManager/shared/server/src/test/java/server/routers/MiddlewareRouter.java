package server.routers;

import java.util.Map;

import server.requests.Request;
import server.responses.Response;
import server.routing.Router;
import server.routing.exceptions.IncorrectHandlerParams;
import server.routing.exceptions.IncorrectHandlerReturns;
import server.routing.handlers.Handler;
import server.routing.handlers.HandlerFunction;
import server.routing.middlewares.InnerMiddleware;

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
}
