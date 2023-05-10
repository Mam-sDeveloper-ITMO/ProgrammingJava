package server;

import server.dispatcher.Dispatcher;
import server.exceptions.ChannelInitFailed;
import server.exceptions.ServerRunningFailed;

public class ServerTest {
    public void testServer() {
        Dispatcher dispatcher = new Dispatcher();
        try {
            dispatcher.includeRouter(new TestRouter());
        } catch (Exception e) {
            assert false;
        }
        try {
            Server server = new Server("127.0.0.1", 8080, dispatcher);
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
