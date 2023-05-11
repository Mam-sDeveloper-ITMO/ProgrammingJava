package server.responses;

/**
 * Failure is a class that represents a failed response from the server to the
 */
public class Failure extends Response {
    public Failure(String message) {
        super(false, message, null);
    }
}
