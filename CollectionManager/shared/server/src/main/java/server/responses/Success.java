package server.responses;

import java.util.Map;

/**
 * Success is a class that represents a successful response from the server to
 * the client.
 */
public class Success extends Response {
    public Success(Map<String, ?> data) {
        super(true, "", data);
    }
}
