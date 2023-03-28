package humandeque.manager.exceptions;

import humandeque.TextResources.Manager.ExceptionsResources;

/**
 * Thrown when an element with given id doesn't exist in the collection.
 */
public class ElementNotExistsError extends Exception {

    /**
     * Creates a new ElementNotExistsError with the specified element id.
     * 
     * @param elementId - The id of the element that doesn't exist.
     */
    public ElementNotExistsError(long elementId) {
        super(ExceptionsResources.ELEMENT_NOT_EXISTS.formatted(elementId));
    }
}