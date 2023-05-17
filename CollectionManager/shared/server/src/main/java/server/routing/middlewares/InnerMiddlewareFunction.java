package server.routing.middlewares;

import server.requests.Request;
import server.responses.Response;
import server.routing.exceptions.IncorrectRequestData;
import server.routing.handlers.HandlerFunction;

/**
 * Interface for inner middleware functions
 *
 * This interface is used to define handler methods
 * annotated with @InnerMiddleware annotation.
 *
 * @see server.routing.handlers.Handler
 */
@FunctionalInterface
public interface InnerMiddlewareFunction {
    /**
     * Handle request with given handler and request data
     *
     * @param handler handler function
     * @param request request object
     * @return processed request data
     * @throws IncorrectRequestData if request data is incorrect
     */
    Response handle(HandlerFunction handler, Request request) throws IncorrectRequestData;
}
