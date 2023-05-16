package server.routing;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import server.responses.Response;
import server.routing.exceptions.IncorrectHandlerParams;
import server.routing.exceptions.IncorrectHandlerReturns;
import server.routing.exceptions.UnhandledRequest;
import server.routing.handlers.Handler;
import server.routing.handlers.HandlerFunction;

/**
 * Router class
 *
 * This class is responsible for routing requests to handlers
 *
 * Handlers are methods of the class that are annotated with @Handler
 * annotation (@see Handler).
 * The annotation contains a trigger string that is used to
 * determine which handler should be called.
 *
 * Methods annotated with @Handler must have the following signature:
 * - return type: Response
 * - single parameter: Request
 *
 * The class is initialized with a prefix string that is used to determine
 * whether the request should be handled by this router or not.
 * Prefix is part of the trigger string before the first dot.
 */
public class Router {
    /**
     * Prefix of handlers triggers attached to this router.
     * Default value is empty string.
     * Prefix is part of the trigger string before the first dot.
     */
    @Getter
    private final String triggersPrefix;

    /**
     * Map of handlers triggers and handlers
     */
    private final HashMap<String, HandlerFunction> handlers;

    /**
     * Router constructor with default prefix
     *
     * @param eventPrefix prefix string that is used to determine whether the
     * @throws IncorrectHandlerParams  if handler method has incorrect signature
     * @throws IncorrectHandlerReturns if handler method has incorrect signature
     */
    public Router() {
        this.triggersPrefix = "";
        this.handlers = defineHandlers();
    }

    /**
     * Router constructor with specified prefix
     *
     * @param eventPrefix prefix string that is used to determine whether the
     * @throws IncorrectHandlerParams  if handler method has incorrect signature
     * @throws IncorrectHandlerReturns if handler method has incorrect signature
     */
    public Router(String eventPrefix) {
        this.triggersPrefix = eventPrefix;
        this.handlers = defineHandlers();
    }

    /**
     * Resolves handler for the given trigger
     * If the trigger does not start with the prefix, throws UnhandledRequest
     *
     * @param trigger string that is used to determine which handler should be
     *                called
     * @return handler function
     * @throws UnhandledRequest if the trigger does not start with the prefix
     */
    public HandlerFunction resolveHandler(String trigger) throws UnhandledRequest {
        if (!this.triggersPrefix.isEmpty()) {
            if (!trigger.startsWith(this.triggersPrefix + ".")) {
                throw new UnhandledRequest();
            }
            trigger = trigger.substring(this.triggersPrefix.length() + 1);
        }
        if (!this.handlers.containsKey(trigger)) {
            throw new UnhandledRequest();
        }
        return this.handlers.get(trigger);
    }

    /**
     * Builds handler function for the given method
     *
     * @param method method that is annotated with @Handler
     * @return handler function
     * @throws IncorrectHandlerParams  if handler method has incorrect signature
     * @throws IncorrectHandlerReturns if handler method has incorrect signature
     */
    private HandlerFunction buildHandler(Method method) {
        // Java Google Style must die
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length != 1 || !Map.class.isAssignableFrom(parameterTypes[0])) {
            throw new IncorrectHandlerParams();
        }

        Class<?> returnType = method.getReturnType();
        if (returnType != Response.class) {
            throw new IncorrectHandlerReturns();
        }

        HandlerFunction handler = (request) -> {
            try {
                method.setAccessible(true);
                return (Response) method.invoke(this, request);
            } catch (Exception e) {
                // never happens (I hope)
                return null;
            }
        };
        return handler;
    }

    /**
     * Parse all methods of the class and build handlers for the methods
     * annotated with @Handler
     *
     * @return map of handlers with trigger strings as keys
     * @throws IncorrectHandlerParams  if handler method has incorrect signature
     * @throws IncorrectHandlerReturns if handler method has incorrect signature
     */
    private HashMap<String, HandlerFunction> defineHandlers() {
        // Java Google Style must die
        HashMap<String, HandlerFunction> handlers = new HashMap<String, HandlerFunction>();

        Method[] methods = this.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (!method.isAnnotationPresent(Handler.class)) {
                continue;
            }
            HandlerFunction handler = buildHandler(method);

            Handler annotation = method.getAnnotation(Handler.class);
            String trigger = annotation.value();

            handlers.put(trigger, handler);
        }
        return handlers;
    }
}
