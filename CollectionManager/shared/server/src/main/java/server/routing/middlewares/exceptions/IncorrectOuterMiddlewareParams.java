package server.routing.middlewares.exceptions;

/**
 * Exception thrown when method annotated with {@code server.routing.middlewares.OuterMiddleware}
 * has params that does not match {@code Request, Response}
 *
 * @see server.requests.Response
 */
public class IncorrectOuterMiddlewareParams extends RuntimeException {
}
