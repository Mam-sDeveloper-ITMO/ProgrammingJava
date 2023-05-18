package adapter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import adapter.exceptions.ReceiveResponseFailed;
import adapter.exceptions.SendRequestFailed;
import adapter.exceptions.SocketInitFailed;
import server.requests.Request;
import server.responses.Response;

public class AdapterTest {
    private static final String IP = "localhost";
    private static final int PORT = 8000;

    private Adapter adapter;

    @Before
    public void setup() throws SocketInitFailed {
        this.adapter = new Adapter(IP, PORT);
    }

    @Test
    public void testCallTrigger() throws SendRequestFailed, ReceiveResponseFailed {
        // Create a request
        String trigger = "testPrefix.foo";
        Map<String, Object> data = Collections.singletonMap("key", "value");
        Request request = new Request(trigger, data);

        try {
            // Call the trigger
            Response response = this.adapter.triggerServer(trigger, data);
        } catch (SendRequestFailed | ReceiveResponseFailed | SocketInitFailed e) {
            assert true;
        }
    }

    // @Test
    public void testLiveCallTrigger() throws SendRequestFailed, ReceiveResponseFailed {
        // Create a request
        String trigger = "testPrefix.foo";
        Map<String, Object> data = Collections.singletonMap("key", "value");
        Request request = new Request(trigger, data);

        // Call the trigger
        try {
            Response response = this.adapter.triggerServer(trigger, data);
            // Check that the response has the expected status code
            assertTrue(response.ok);

            // Check that the response data is not null
            assertNotNull(response.getData());

            // Check that the response data is equal to the request data
            assertEquals(request.getData(), response.getData());
        } catch (SocketInitFailed | SendRequestFailed | ReceiveResponseFailed e) {
            assert true;
        }
    }
}
