package humandeque.manager.exceptions;

import humandeque.Messages.Manager;

/**
 * Thrown when element with given id already exists in collection
 */
public class ElementNotExistsError extends Exception {
    public ElementNotExistsError(long elementId) {
        super(Manager.ELEMENT_NOT_EXISTS.formatted(elementId));
    }
}
