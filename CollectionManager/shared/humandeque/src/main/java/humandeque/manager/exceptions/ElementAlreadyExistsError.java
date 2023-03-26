package humandeque.manager.exceptions;

import humandeque.Messages.Manager;

/**
 * Thrown when element with given id already exists in collection
 */
public class ElementAlreadyExistsError extends Exception {
    public ElementAlreadyExistsError(long elementId) {
        super(Manager.ELEMENT_NOT_EXISTS.formatted(elementId));
    }
}
