package humandeque.manager.exceptions;

import humandeque.Messages.Manager;

public class ElementAlreadyExistsError extends Exception {
    public ElementAlreadyExistsError(long elementId) {
        super(Manager.ELEMENT_NOT_EXISTS.formatted(elementId));
    }
}
