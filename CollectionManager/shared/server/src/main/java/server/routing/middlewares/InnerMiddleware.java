package server.routing.middlewares;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for marking inner middleware methods.
 *
 * @see server.routing.Router
 * @see server.routing.middlewares.InnerMiddlewareFunction
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InnerMiddleware {
    /**
     * The trigger prefix for the inner middleware.
     */
    String value();
}
