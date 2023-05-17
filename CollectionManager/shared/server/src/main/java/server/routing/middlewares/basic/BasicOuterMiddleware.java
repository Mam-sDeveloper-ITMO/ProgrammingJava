package server.routing.middlewares.basic;

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
    public Response handle(Response response) {
        return response;
    }
}
