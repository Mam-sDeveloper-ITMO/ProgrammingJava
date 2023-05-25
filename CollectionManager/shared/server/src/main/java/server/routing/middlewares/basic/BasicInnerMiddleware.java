package server.routing.middlewares.basic;

import java.util.Map;

import server.requests.Request;
import server.responses.Response;
import server.routing.exceptions.IncorrectRequestData;
import server.routing.handlers.HandlerFunction;
import server.routing.middlewares.InnerMiddlewareFunction;
import server.utils.DataConverter;

/**
 * Basic inner middleware function.
 *
 * Just calls handler function with request data.
 *
 * @see InnerMiddlewareFunction
 */
public class BasicInnerMiddleware implements InnerMiddlewareFunction {
    /**
     * Calls handler function with request data.
     *
     * @param handler Handler function to call.
     * @param request Request to handle.
     * @return Response from handler function.
     */
    @Override
    public Response handle(HandlerFunction handler, Request request) throws IncorrectRequestData {
        Map<String, Object> requestData = DataConverter.serializableToObjects(request.getData());
        return handler.handle(requestData);
    }
}
