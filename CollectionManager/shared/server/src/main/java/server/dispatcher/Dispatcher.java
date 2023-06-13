package server.dispatcher;

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
import server.utils.StatusCodes;

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
                response = Response.failure("Incorrect request data", StatusCodes.INCORRECT_REQUEST_DATA);
            }

            // Get router outer middleware for the trigger or set default
            Optional<OuterMiddlewareFunction> resolvedOuterMiddleware = router.resolveOuterMiddleware(trigger);
            OuterMiddlewareFunction outerMiddleware = resolvedOuterMiddleware.orElse(basicOuterMiddleware);

            // process response with outer middleware
            try {
                return outerMiddleware.handle(request, response);
            } catch (IncorrectRequestData e) {
                return Response.failure("Incorrect request data", StatusCodes.INCORRECT_REQUEST_DATA);
            }
        }
        return Response.failure("Not handlers for such trigger", StatusCodes.UNHANDLED);
    }
}
