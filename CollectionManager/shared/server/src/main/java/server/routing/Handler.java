package server.routing;

import java.util.Map;

import server.Response;
import server.routing.exceptions.IncorrectRequestData;

/**
 * Handler interface
 *
 * This interface is used to define handler methods
 * annotated with @Handler (@see Handler) annotation.
 */
public interface Handler {
    /**
     * Handle and process the request
     *
     * @param requestData data of the request
     * @return response object
     * @throws IncorrectRequestData if request data is incorrect
     */
    Response handle(Map<String, ?> requestData) throws IncorrectRequestData;
}
