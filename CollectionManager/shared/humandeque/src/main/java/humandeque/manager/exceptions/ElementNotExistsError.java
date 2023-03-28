package humandeque.manager.exceptions;

import humandeque.TextResources.Manager.ExceptionsResources;

/**
 * Thrown when element with given id already exists in collection
 */
public class ElementNotExistsError extends Exception {
    public ElementNotExistsError(long elementId) {
        super(ExceptionsResources.ELEMENT_NOT_EXISTS.formatted(elementId));
    }
}
