package server.dispatcher;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import server.requests.Request;
import server.responses.Response;
import server.routing.Router;
import server.routing.exceptions.IncorrectRequestData;
import server.routing.handlers.HandlerFunction;
import server.routing.middlewares.InnerMiddlewareFunction;
import server.routing.middlewares.OuterMiddlewareFunction;
import server.routing.middlewares.basic.BasicInnerMiddleware;
import server.routing.middlewares.basic.BasicOuterMiddleware;
import server.utils.Serializer;
import server.utils.exceptions.BadRequestStream;

/**
 * Dispatcher is a class that handles requests from clients
 * and dispatches them to the appropriate handlers.
 *
 * Used by {@link server.Server}
 *
 * It contains a set of routers, which are used to resolve
 * handlers for requests.
 */
public class Dispatcher {
    /**
     * Set of routers, which are used to resolve handlers for requests.
     */
    @Getter
    @Setter
    private Set<Router> routers = new HashSet<>();

    /**
     * Inner middleware for basic requests handling
     * if there is no middlewares in router.
     */
    private final InnerMiddlewareFunction basicInnerMiddleware = new BasicInnerMiddleware();

    /**
     * Outer middleware for basic responses handling
     * if there is no middlewares in router.
     */
    private final OuterMiddlewareFunction basicOuterMiddleware = new BasicOuterMiddleware();

    /**
     * Include router to the set of routers.
     *
     * @param router
     */
    public void includeRouter(Router router) {
        this.routers.add(router);
    }

    /**
     * Remove router from the set of routers.
     *
     * @param router
     */
    public void removeRouter(Router router) {
        this.routers.remove(router);
    }

    /**
     * Dispatch request from given stream data and return response as stream.
     * Used by {@code Server}
     *
     * @see Dispatcher.dispatch
     *
     * @param requestStream input stream with Request object
     * @return output stream with Response object
     */
    public ByteArrayOutputStream dispatchStreams(ByteArrayInputStream requestStream) {
        Request request;
        try {
            request = Serializer.deserializeRequest(requestStream);
            Response response = dispatch(request);
            return Serializer.serializeResponse(response);
        } catch (BadRequestStream e) {
            Response response = new Response(false, "Bad request stream", null);
            return Serializer.serializeResponse(response);
        }
    }

    /**
     * Dispatch request to the appropriate router.
     * If there is no appropriate router, return response with
     * error message.
     *
     * @param request processed request
     * @return response object
     */
    public Response dispatch(Request request) {
        for (Router router : this.routers) {
            // Check if router is corresponding to the trigger
            Optional<String> resolvedTrigger = router.resolveTrigger(request.getTrigger());
            if (!resolvedTrigger.isPresent()) {
                continue;
            }
            // trigger without router prefix
            String trigger = resolvedTrigger.get();

            // Check if router has handler for the trigger
            Optional<HandlerFunction> resolvedHandler = router.resolveHandler(trigger);
            if (!resolvedHandler.isPresent()) {
                continue;
            }
            HandlerFunction handler = resolvedHandler.get();

            // Get router inner middleware for the trigger or set default
            Optional<InnerMiddlewareFunction> resolvedInnerMiddleware = router.resolveInnerMiddleware(trigger);
            InnerMiddlewareFunction innerMiddleware = resolvedInnerMiddleware.orElse(basicInnerMiddleware);

            // Handle request with inner middleware
            Response response;
            try {
                response = innerMiddleware.handle(handler, request);
            } catch (IncorrectRequestData e) {
                response = Response.failure("Bad request data");
            }

            // Get router outer middleware for the trigger or set default
            Optional<OuterMiddlewareFunction> resolvedOuterMiddleware = router.resolveOuterMiddleware(trigger);
            OuterMiddlewareFunction outerMiddleware = resolvedOuterMiddleware.orElse(basicOuterMiddleware);

            // process response with outer middleware
            return outerMiddleware.handle(response);
        }
        return new Response(false, "Not handlers for such trigger", null);
    }
}
