package server.routing.handlers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for marking handler methods.
 *
 * @value The trigger for the handler.
 *
 * @see server.routing.Router
 * @see server.routing.handlers.HandlerFunction
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Handler {
    /**
     * The trigger for the handler.
     */
    String value();
}
