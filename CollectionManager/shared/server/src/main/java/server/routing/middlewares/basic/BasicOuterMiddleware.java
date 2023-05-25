package server.routing.middlewares.basic;

import server.requests.Request;
import server.responses.Response;
import server.routing.middlewares.OuterMiddlewareFunction;

/**
 * Basic outer middleware function.
 * Just returns response.
 *
 * @see OuterMiddlewareFunction
 */
public class BasicOuterMiddleware implements OuterMiddlewareFunction {
    /**
     * Returns response.
     *
     * @param response Response to return.
     * @return Same response.
     */
    @Override
    public Response handle(Request request, Response response) {
        return response;
    }
}
