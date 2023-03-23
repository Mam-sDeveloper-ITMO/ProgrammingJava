package humandeque.manager.exceptions;

import humandeque.Messages.Manager;

public class ElementNotExistsError extends Exception {
    public ElementNotExistsError(long elementId) {
        super(Manager.ELEMENT_NOT_EXISTS.formatted(elementId));
    }
}
