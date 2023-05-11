package server.routing;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import server.requests.Request;
import server.responses.Response;
import server.routing.exceptions.IncorrectHandlerParams;
import server.routing.exceptions.IncorrectHandlerReturns;
import server.routing.exceptions.UnhandledRequest;

/**
 * Router class
 *
 * This class is responsible for routing requests to handlers
 *
 * Handlers are methods of the class that are annotated with @Trigger
 * annotation (@see Trigger).
 * The annotation contains a trigger string that is used to
 * determine which handler should be called.
 *
 * Methods annotated with @Trigger must have the following signature:
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
    private final HashMap<String, Handler> handlers;

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
    public Handler resolveHandler(String trigger) throws UnhandledRequest {
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
     * @param method method that is annotated with @Trigger
     * @return handler function
.gitignore     * @throws IncorrectHandlerParams  if handler method has incorrect signature
     * @throws IncorrectHandlerReturns if handler method has incorrect signature
     */
    private Handler buildHandler(Method method) {
        // Java Google Style must die
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length != 1 || !Map.class.isAssignableFrom(parameterTypes[0])) {
            throw new IncorrectHandlerParams();
        }

        Class<?> returnType = method.getReturnType();
        if (returnType != Response.class) {
            throw new IncorrectHandlerReturns();
        }

        Handler handler = (request) -> {
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
     * annotated with @Trigger
     *
     * @return map of handlers with trigger strings as keys
     * @throws IncorrectHandlerParams  if handler method has incorrect signature
     * @throws IncorrectHandlerReturns if handler method has incorrect signature
     */
    private HashMap<String, Handler> defineHandlers() {
        // Java Google Style must die
        HashMap<String, Handler> handlers = new HashMap<String, Handler>();

        Method[] methods = this.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (!method.isAnnotationPresent(Trigger.class)) {
                continue;
            }
            Handler handler = buildHandler(method);

            Trigger annotation = method.getAnnotation(Trigger.class);
            String trigger = annotation.trigger();

            handlers.put(trigger, handler);
        }
        return handlers;
    }
}
