package server.routing.handlers.exceptions;

/**
 * Exception thrown when method annotated with {@code routing.Trigger}
 * has params that does not match single {@code Map<String, ?>} parameter.
 */
public class IncorrectHandlerParams extends RuntimeException {
}
