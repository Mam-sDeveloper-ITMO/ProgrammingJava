package humandeque.manager.exceptions;

import static humandeque.manager.Messages.Manager.ELEMENT_NOT_EXISTS;

public class ElementAlreadyExists extends Exception {
    public ElementAlreadyExists(long elementId) {
        super(ELEMENT_NOT_EXISTS.formatted(elementId));
    }
}
