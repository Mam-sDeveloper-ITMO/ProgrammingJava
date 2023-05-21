package server.requests;

import java.io.Serializable;
import java.util.Map;

import lombok.Data;

/**
 * Request is a class that represents a request from the client to the server.
 */
@Data
public class Request implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * trigger define what the client wants to do.
     */
    private final String trigger;

    /**
     * data is a map that represents the data of the request.
     */
    private final Map<String, ?> data;
}
