package server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

        try {
            Response response = dispatcher.dispatch(new Request("prefix.foo", null));
            assertEquals("Hello from foo", response.message);
            assertTrue(response.ok);

            response = dispatcher.dispatch(new Request("prefix2.bar", null));
            assertEquals("Hello from bar", response.message);
            assertTrue(response.ok);

            response = dispatcher.dispatch(new Request("000", null));
            assertEquals("Not handlers for such trigger", response.message);
            assertFalse(response.ok);
        } catch (Exception e) {
            assert false;
        }
    }
}
