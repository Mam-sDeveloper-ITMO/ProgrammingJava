package server.routing.middlewares.exceptions;

/**
 * Exception thrown when method annotated with {@code server.routing.middlewares.InnerMiddleware}
 * has returns that does not match {@code Response}
 */
public class IncorrectInnerMiddlewareReturns extends RuntimeException {
}
