package server.routing.handlers;

import java.util.Map;

import server.responses.Response;
import server.routing.exceptions.IncorrectRequestData;

/**
 * Interface for handler functions
 *
 * This interface is used to define handler methods
 * annotated with @Handler annotation.
 *
 * @see server.routing.handlers.Handler
 */
@FunctionalInterface
public interface HandlerFunction {
    /**
     * Handle and process the request
     *
     * @param requestData data of the request
     * @return response object
     * @throws IncorrectRequestData if request data is incorrect
     */
    Response handle(Map<String, ?> requestData) throws IncorrectRequestData;
}
