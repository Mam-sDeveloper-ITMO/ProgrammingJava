package server.responses;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.NonNull;
import server.utils.StatusCodes;

/**
 * Response is a class that represents a response from the server to the client.
 */
@Data
public class Response implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ok is a boolean that represents whether the request was successful.
     */
    @NonNull
    private final Boolean ok;

    /**
     * message is a string that represents the message of the response.
     */
    @NonNull
    private final String message;

    /**
     * data is a map that represents the data of the response.
     */
    @NonNull
    private final Map<String, ?> data;

    /**
     * status code is same to HTTP status code concept.
     */
    @NonNull
    private final Integer code;

    /**
     * Return a new Response for successful requests.
     */
    public static Response success(String message, Map<String, ?> data, Integer code) {
        return new Response(true, message, data, code);
    }

    /**
     * Return a new Response for successful requests with default 200 status code.
     */
    public static Response success(String message, Map<String, ?> data) {
        return new Response(true, message, data, StatusCodes.OK);
    }

    /**
     * Return a new Response for successful requests
     * with empty data and specified status code.
     */
    public static Response success(String message, Integer code) {
        return new Response(true, message, new HashMap<>(), code);
    }

    /**
     * Return a new Response for successful requests
     * with empty data and empty message and specified status code.
     */
    public static Response success(Map<String, ?> data, Integer code) {
        return new Response(true, "", data, code);
    }

    /**
     * Return a new Response for successful requests
     * with empty data and empty message and default 200 status code.
     */
    public static Response success(Map<String, ?> data) {
        return new Response(true, "", data, StatusCodes.OK);
    }

    /**
     * Return a new Response for successful requests with default 200 status code
     * and empty data.
     */
    public static Response success(String message) {
        return new Response(true, message, new HashMap<>(), StatusCodes.OK);
    }

    /**
     * Return a new Response for successful requests with empty data and message.
     */
    public static Response success(Integer code) {
        return new Response(true, "", new HashMap<>(), code);
    }

    /**
     * Return a new Response for successful requests with default 200 status code
     * and empty data and message.
     */
    public static Response success() {
        return new Response(true, "", new HashMap<>(), StatusCodes.OK);
    }

    /**
     * Return a new Response for failed requests with specified data.
     */
    public static Response failure(String message, Map<String, ?> data, Integer code) {
        return new Response(false, message, data, code);
    }

    /**
     * Return a new Response for failed requests with empty data.
     */
    public static Response failure(String message, Integer code) {
        return new Response(false, message, new HashMap<>(), code);
    }
}
