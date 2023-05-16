package server.responses;

import java.io.Serializable;
import java.util.Map;

import lombok.Data;

/**
 * Response is a class that represents a response from the server to the client.
 */
@Data
public class Response implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ok is a boolean that represents whether the request was successful.
     */
    public final Boolean ok;

    /**
     * message is a string that represents the message of the response.
     */
    public final String message;

    /**
     * data is a map that represents the data of the response.
     */
    public final Map<String, ?> data;

    /**
     * Return a new Response for successful requests.
     */
    public static Response success(String message, Map<String, ?> data) {
        return new Response(true, message, data);
    }

    /**
     * Return a new Response for successful requests.
     */
    public static Response success(Map<String, ?> data) {
        return new Response(true, null, data);
    }

    /**
     * Return a new Response for failed requests.
     */
    public static Response failure(String message) {
        return new Response(false, message, null);
    }
}
