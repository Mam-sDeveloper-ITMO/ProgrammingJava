package server.routing.middlewares.exceptions;

/**
 * Exception thrown when method annotated with {@code routing.Trigger}
 * has returns that does not match {@code Response}
 */
public class IncorrectOuterMiddlewareReturns extends RuntimeException {
}
