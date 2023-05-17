package server.routing.middlewares;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for marking outer middleware methods.
 *
 * @see server.routing.Router
 * @see server.routing.middlewares.OuterMiddlewareFunction
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OuterMiddleware {
    /**
     * The trigger prefix for the outer middleware.
     */
    String value();
}
