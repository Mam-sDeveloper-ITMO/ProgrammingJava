package server.routing.exceptions;

/**
 * Exception thrown when method annotated with {@code routing.Trigger}
 * has signature that does not match single {@code Map<String, ?>} parameter.
 */
public class IncorrectHandlerParams extends RuntimeException {
}
