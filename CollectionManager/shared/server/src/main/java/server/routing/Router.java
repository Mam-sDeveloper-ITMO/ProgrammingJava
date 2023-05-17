package server.routing;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import lombok.NonNull;
import server.requests.Request;
import server.responses.Response;
import server.routing.exceptions.UnhandledRequest;
import server.routing.handlers.Handler;
import server.routing.handlers.HandlerFunction;
import server.routing.handlers.exceptions.IncorrectHandlerParams;
import server.routing.handlers.exceptions.IncorrectHandlerReturns;
import server.routing.middlewares.InnerMiddleware;
import server.routing.middlewares.InnerMiddlewareFunction;
import server.routing.middlewares.OuterMiddleware;
import server.routing.middlewares.OuterMiddlewareFunction;
import server.routing.middlewares.exceptions.IncorrectInnerMiddlewareParams;
import server.routing.middlewares.exceptions.IncorrectInnerMiddlewareReturns;
import server.routing.middlewares.exceptions.IncorrectOuterMiddlewareParams;
import server.routing.middlewares.exceptions.IncorrectOuterMiddlewareReturns;

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
    private final LinkedHashMap<String, HandlerFunction> handlers;

    /**
     * Inner middleware triggers prefixes and inner middleware functions
     * Middlewares sorted by prefix length in descending order
     */
    @Getter
    @NonNull
    private final LinkedHashMap<String, InnerMiddlewareFunction> innerMiddlewares;

    /**
     * Outer middleware triggers prefixes and outer middleware functions
     * Middlewares sorted by prefix length in descending order
     */
    @Getter
    @NonNull
    private final LinkedHashMap<String, OuterMiddlewareFunction> outerMiddlewares;

    /**
     * Router constructor with default prefix
     *
     * @param eventPrefix prefix string that is used to determine whether the
     * @throws IncorrectHandlerParams  if handler method has incorrect signature
     * @throws IncorrectHandlerReturns if handler method has incorrect signature
     */
    public Router() {
        this("");
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
        this.innerMiddlewares = defineInnerMiddlewares();
        this.outerMiddlewares = defineOuterMiddlewares();
    }

    /**
     * Resolve if trigger is handled by this router
     *
     * Return the trigger without the router prefix if it is handled by this
     * router, empty optional otherwise
     *
     * @param trigger
     * @return trigger without the router prefix if it is handled by this
     */
    public Optional<String> resolveTrigger(String trigger) {
        if (!this.triggersPrefix.isEmpty()) {
            if (!trigger.startsWith(this.triggersPrefix + ".")) {
                return Optional.empty();
            }
            trigger = trigger.substring(this.triggersPrefix.length() + 1);
        }
        return Optional.of(trigger);
    }

    /**
     * Resolves handler for the given trigger
     * If handler for the given trigger is not found, throws UnhandledRequest
     *
     * @param trigger string that is used to determine which handler should be
     *                called
     * @return handler function
     * @throws UnhandledRequest if the trigger does not start with the prefix
     */
    public HandlerFunction resolveHandler(String trigger) throws UnhandledRequest {
        if (!this.handlers.containsKey(trigger)) {
            throw new UnhandledRequest();
        }
        return this.handlers.get(trigger);
    }

    /**
     * Resolves inner middleware for the given trigger
     * If middleware for the given trigger is not found, returns empty optional
     *
     * @param trigger string that is used to determine which inner middleware
     *                should be called
     * @return inner middleware function
     */
    public Optional<InnerMiddlewareFunction> resolveInnerMiddleware(String trigger) {
        for (String prefix : this.innerMiddlewares.keySet()) {
            if (trigger.startsWith(prefix + ".") || trigger.equals(prefix) || prefix.isEmpty()) {
                return Optional.of(this.innerMiddlewares.get(prefix));
            }
        }
        return Optional.empty();
    }

    /**
     * Resolve outer middleware for the given trigger
     * If middleware for the given trigger is not found, returns empty optional
     *
     * @param trigger string that is used to determine which outer middleware
     *                should be called
     * @return outer middleware function
     */
    public Optional<OuterMiddlewareFunction> resolveOuterMiddleware(String trigger) {
        for (String prefix : this.outerMiddlewares.keySet()) {
            if (trigger.startsWith(prefix + ".") || trigger.equals(prefix) || prefix.isEmpty()) {
                return Optional.of(this.outerMiddlewares.get(prefix));
            }
        }
        return Optional.empty();
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
    private LinkedHashMap<String, HandlerFunction> defineHandlers() {
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
        // sort handlers by trigger length in descending order
        LinkedHashMap<String, HandlerFunction> sortedHandlers = new LinkedHashMap<>();
        handlers.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.comparingInt(String::length).reversed()))
                .forEachOrdered(x -> sortedHandlers.put(x.getKey(), x.getValue()));

        return sortedHandlers;
    }

    /**
     * Build inner middleware function for the given method
     *
     * @param method method that is annotated with @InnerMiddleware
     * @return inner middleware function
     * @throws IncorrectInnerMiddlewareParams  if inner middleware method has
     *                                         incorrect signature
     * @throws IncorrectInnerMiddlewareReturns if inner middleware method has
     *                                         incorrect signature
     */
    private InnerMiddlewareFunction buildInnerMiddleware(Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length != 2
                || !HandlerFunction.class.isAssignableFrom(parameterTypes[0])
                || !Request.class.isAssignableFrom(parameterTypes[1])) {
            throw new IncorrectInnerMiddlewareParams();
        }

        Class<?> returnType = method.getReturnType();
        if (returnType != Response.class) {
            throw new IncorrectInnerMiddlewareReturns();
        }

        method.setAccessible(true);
        InnerMiddlewareFunction innerMiddleware = (handler, request) -> {
            try {
                return (Response) method.invoke(this, handler, request);
            } catch (Exception e) {
                // never happens (I hope)
                return null;
            }
        };
        return innerMiddleware;
    }

    /**
     * Parse all methods of the class and build inner middlewares for the methods
     * annotated with @InnerMiddleware
     *
     * @return map of inner middlewares with trigger strings as keys
     * @throws IncorrectHandlerParams  if handler method has incorrect signature
     * @throws IncorrectHandlerReturns if handler method has incorrect signature
     */
    private LinkedHashMap<String, InnerMiddlewareFunction> defineInnerMiddlewares() {
        HashMap<String, InnerMiddlewareFunction> innerMiddlewares = new HashMap<String, InnerMiddlewareFunction>();

        Method[] methods = this.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (!method.isAnnotationPresent(InnerMiddleware.class)) {
                continue;
            }
            InnerMiddlewareFunction innerMiddleware = buildInnerMiddleware(method);

            InnerMiddleware annotation = method.getAnnotation(InnerMiddleware.class);
            String triggersPrefix = annotation.value();

            innerMiddlewares.put(triggersPrefix, innerMiddleware);
        }
        // sort inner middlewares by trigger length in descending order
        LinkedHashMap<String, InnerMiddlewareFunction> sortedInnerMiddlewares = new LinkedHashMap<>();
        innerMiddlewares.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.comparingInt(String::length).reversed()))
                .forEachOrdered(x -> sortedInnerMiddlewares.put(x.getKey(), x.getValue()));

        return sortedInnerMiddlewares;
    }

    /**
     * Build outer middleware function for the given method
     *
     * @param method method that is annotated with @OuterMiddleware
     * @return middleware function
     * @throws IncorrectOuterMiddlewareParams  if outer middleware method has
     *                                         incorrect signature
     * @throws IncorrectOuterMiddlewareReturns if outer middleware method has
     *                                         incorrect signature
     */
    private OuterMiddlewareFunction buildOuterMiddleware(Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length != 1 || !Response.class.isAssignableFrom(parameterTypes[0])) {
            throw new IncorrectOuterMiddlewareParams();
        }

        Class<?> returnType = method.getReturnType();
        if (returnType != Response.class) {
            throw new IncorrectOuterMiddlewareReturns();
        }

        method.setAccessible(true);
        OuterMiddlewareFunction middleware = (response) -> {
            try {
                return (Response) method.invoke(this, response);
            } catch (Exception e) {
                // never happens (I hope)
                return null;
            }
        };
        return middleware;
    }

    /**
     * Parse all methods of the class and build outer middlewares for the methods
     * annotated with @OuterMiddleware
     *
     * @return map of outer middlewares with trigger strings as keys
     * @throws IncorrectHandlerParams  if handler method has incorrect signature
     * @throws IncorrectHandlerReturns if handler method has incorrect signature
     */
    private LinkedHashMap<String, OuterMiddlewareFunction> defineOuterMiddlewares() {
        HashMap<String, OuterMiddlewareFunction> outerMiddlewares = new HashMap<String, OuterMiddlewareFunction>();

        Method[] methods = this.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (!method.isAnnotationPresent(OuterMiddleware.class)) {
                continue;
            }
            OuterMiddlewareFunction outerMiddleware = buildOuterMiddleware(method);

            OuterMiddleware annotation = method.getAnnotation(OuterMiddleware.class);
            String triggersPrefix = annotation.value();

            outerMiddlewares.put(triggersPrefix, outerMiddleware);
        }
        // sort outer middlewares by trigger length in descending order
        LinkedHashMap<String, OuterMiddlewareFunction> sortedOuterMiddlewares = new LinkedHashMap<>();
        outerMiddlewares.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.comparingInt(String::length).reversed()))
                .forEachOrdered(x -> sortedOuterMiddlewares.put(x.getKey(), x.getValue()));

        return sortedOuterMiddlewares;
    }
}
