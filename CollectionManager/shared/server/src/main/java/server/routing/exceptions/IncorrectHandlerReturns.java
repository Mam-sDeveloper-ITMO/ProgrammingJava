package server.routing.exceptions;

/**
 * Exception thrown when method annotated with {@code routing.Trigger}
 * has signature that does not match single {@code Response} parameter.
 */
public class IncorrectHandlerReturns extends RuntimeException {
}
