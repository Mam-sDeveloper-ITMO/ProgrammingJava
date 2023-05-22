package server.routing.handlers.exceptions;

/**
 * Exception thrown when method annotated with {@code server.routing.handlers.Handler}
 * has params that does not match single {@code Map<String, Object>} parameter.
 */
public class IncorrectHandlerParams extends RuntimeException {
}
