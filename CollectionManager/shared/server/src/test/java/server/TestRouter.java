package server;

import server.responses.Response;
import server.routing.Router;
import server.routing.Trigger;
import server.routing.exceptions.IncorrectHandlerParams;
import server.routing.exceptions.IncorrectHandlerReturns;

import java.util.HashMap;
import java.util.Map;

public class TestRouter extends Router {
    public TestRouter() throws IncorrectHandlerParams, IncorrectHandlerReturns {
        super("testPrefix");
    }

    public TestRouter(String prefix) throws IncorrectHandlerParams, IncorrectHandlerReturns {
        super(prefix);
    }

    @Trigger(trigger = "foo")
    Response foo(Map<String, ?> data) {
        return new Response(true, "Hello from foo", data);
    }

    @Trigger(trigger = "bar")
    Response bar(Map<String, ?> data) {
        return new Response(true, "Hello from bar", data);
    }
}
