package server.routing.middlewares.basic;

import server.requests.Request;
import server.responses.Response;
import server.routing.exceptions.IncorrectRequestData;
import server.routing.handlers.HandlerFunction;
import server.routing.middlewares.InnerMiddlewareFunction;

/**
 * Basic inner middleware function.
 *
 * Just calls handler function with request data.
 *
 * @see InnerMiddlewareFunction
 */
public class BasicInnerMiddleware implements InnerMiddlewareFunction {
    @Override
    public Response handle(HandlerFunction handler, Request request) throws IncorrectRequestData {
        return handler.handle(request.getData());
    }
}
