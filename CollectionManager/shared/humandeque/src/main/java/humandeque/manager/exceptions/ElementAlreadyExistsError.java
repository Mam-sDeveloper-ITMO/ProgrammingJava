package humandeque.manager.exceptions;

import humandeque.TextResources.Manager.ExceptionsResources;;

/**
 * Thrown when an element with given id already exists in the collection.
 */
public class ElementAlreadyExistsError extends Exception {

    /**
     * Creates a new ElementAlreadyExistsError with the specified element id.
     * 
     * @param elementId - The id of the element that already exists.
     */
    public ElementAlreadyExistsError(long elementId) {
        super(ExceptionsResources.ELEMENT_ALREADY_EXISTS.formatted(elementId));
    }
}
