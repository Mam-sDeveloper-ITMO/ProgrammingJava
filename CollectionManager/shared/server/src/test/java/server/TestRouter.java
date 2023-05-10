package server;

import server.routing.Router;
import server.routing.Trigger;
import server.routing.exceptions.IncorrectHandlerParams;
import server.routing.exceptions.IncorrectHandlerReturns;

import java.util.HashMap;

public class TestRouter extends Router {
    public TestRouter() throws IncorrectHandlerParams, IncorrectHandlerReturns {
        super("testPrefix");
    }

    public TestRouter(String prefix) throws IncorrectHandlerParams, IncorrectHandlerReturns {
        super(prefix);
    }

    @Trigger(trigger = "foo")
    Response foo(Request request) {
        HashMap<String, String> data = new HashMap<>();
        data.put("handler", "foo");
        return new Response(true, "Hello from foo", data);
    }

    @Trigger(trigger = "bar")
    Response bar(Request request) {
        HashMap<String, String> data = new HashMap<>();
        data.put("handler", "bar");
        return new Response(true, "Hello from bar", data);
    }
}
