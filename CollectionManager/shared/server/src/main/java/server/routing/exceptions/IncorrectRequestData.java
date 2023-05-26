package server.routing.exceptions;

/**
 * Exception thrown when the {@code Request} data is not valid
 * for handling.
 *
 * @see server.routing.Router
 * @see server.routing.handlers.HandlerFunction
 * @see server.routing.middlewares.InnerMiddlewareFunction
 */
public class IncorrectRequestData extends Exception {
}
