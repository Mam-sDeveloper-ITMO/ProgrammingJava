package server;

import java.io.Serializable;
import java.util.Map;

import lombok.Data;

/**
 * Request is a class that represents a request from the client to the server.
 */
@Data
public class Request implements Serializable {
    /**
     * trigger define what the client wants to do.
     */
    public final String trigger;

    /**
     * data is a map that represents the data of the request.
     */
    public final Map<String, ?> data;
}
