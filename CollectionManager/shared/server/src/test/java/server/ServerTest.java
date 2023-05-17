package server;

import org.junit.Test;

import server.dispatcher.Dispatcher;
import server.exceptions.ChannelInitFailed;
import server.exceptions.ServerRunningFailed;
import server.routers.BasicRouter;

public class ServerTest {
    // @Test
    public void testServer() {
        Dispatcher dispatcher = new Dispatcher();
        try {
            dispatcher.includeRouter(new BasicRouter());
        } catch (Exception e) {
            assert false;
        }
        try {
            Server server = new Server("127.0.0.1", 8000, dispatcher);
            try {
                server.run();
            } catch (ServerRunningFailed e) {
                assert false;
            }
        } catch (ChannelInitFailed e) {
            assert false;
        }
    }
}
