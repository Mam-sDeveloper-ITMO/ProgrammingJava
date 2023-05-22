package server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Test;

import server.dispatcher.Dispatcher;
import server.requests.Request;
import server.responses.Response;
import server.routers.BasicRouter;
import server.routing.Router;

public class DispatcherTest {
    @Test
    public void testRouters() {
        Dispatcher dispatcher = new Dispatcher();
        assertEquals(0, dispatcher.getRouters().size());

        try {
            dispatcher.includeRouter(new BasicRouter());
        } catch (Exception e) {
            assert false;
        }
        assert dispatcher.getRouters().size() == 1;

        try {
            Router router = new BasicRouter("testPrefix2");
            dispatcher.includeRouter(router);
            assertEquals(2, dispatcher.getRouters().size());
            dispatcher.removeRouter(router);
            assertEquals(1, dispatcher.getRouters().size());
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    public void testDispatch() {
        Router router = new BasicRouter("prefix");
        Router router2 = new BasicRouter("prefix2");

        Dispatcher dispatcher = new Dispatcher();
        dispatcher.includeRouter(router);
        dispatcher.includeRouter(router2);

        Response response = dispatcher.dispatch(new Request("prefix.foo", new HashMap<>()));
        assertEquals("Hello from foo", response.getMessage());
        assertTrue(response.getOk());

        response = dispatcher.dispatch(new Request("prefix2.bar", new HashMap<>()));
        assertEquals("Hello from bar", response.getMessage());
        assertTrue(response.getOk());

        response = dispatcher.dispatch(new Request("prefix2.bar.sub", new HashMap<>()));
        assertEquals("Hello from bar.sub", response.getMessage());
        assertTrue(response.getOk());

        response = dispatcher.dispatch(new Request("000", new HashMap<>()));
        assertEquals("Not handlers for such trigger", response.getMessage());
        assertFalse(response.getOk());
        try {
        } catch (Exception e) {
            assert false;
        }
    }
}
