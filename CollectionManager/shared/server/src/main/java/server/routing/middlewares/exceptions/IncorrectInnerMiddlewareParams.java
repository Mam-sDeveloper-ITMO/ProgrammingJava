package server.routing.middlewares.exceptions;

/**
 * Exception thrown when method annotated with {@code server.routing.middlewares.InnerMiddleware}
 * has params that does not match {@code HandlerFunction, Request}
 *
 * @see server.routing.handlers.Handler
 * @see server.routing.handlers.HandlerFunction
 * @see server.requests.Request
 */
public class IncorrectInnerMiddlewareParams extends RuntimeException {
}
