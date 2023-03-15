package humandeque.manager.exceptions;

import static humandeque.Messages.Manager.ELEMENT_NOT_EXISTS;

public class ElementNotExistsError extends Exception {
    public ElementNotExistsError(long elementId) {
        super(ELEMENT_NOT_EXISTS.formatted(elementId));
    }
}
