package server.routing.middlewares;

import server.responses.Response;

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
    Response handle(Response response);
}
