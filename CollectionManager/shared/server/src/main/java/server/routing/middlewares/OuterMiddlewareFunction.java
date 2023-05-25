package server.routing.middlewares;

import server.requests.Request;
import server.responses.Response;
import server.routing.exceptions.IncorrectRequestData;

/**
 * Interface for outer middleware functions
 *
 * This interface is used to define handler methods
 * annotated with @OuterMiddleware annotation.
 *
 * @see server.routing.handlers.Router
 * @see server.dispatcher.Dispatcher
 */
@FunctionalInterface
public interface OuterMiddlewareFunction {
    /**
     * Handle request and response
     *
     * @param request  Request object
     * @param response Response object
     * @return Response object
     * @throws IncorrectRequestData Incorrect request data
     */
    Response handle(Request request, Response response) throws IncorrectRequestData;
}
