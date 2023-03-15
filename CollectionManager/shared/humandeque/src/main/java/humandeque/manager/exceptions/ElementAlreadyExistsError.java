package humandeque.manager.exceptions;

import static humandeque.Messages.Manager.ELEMENT_NOT_EXISTS;

public class ElementAlreadyExistsError extends Exception {
    public ElementAlreadyExistsError(long elementId) {
        super(ELEMENT_NOT_EXISTS.formatted(elementId));
    }
}
