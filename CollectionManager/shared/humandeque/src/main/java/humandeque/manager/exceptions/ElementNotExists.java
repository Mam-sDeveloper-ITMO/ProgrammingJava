package humandeque.manager.exceptions;

import static humandeque.manager.Messages.Manager.ELEMENT_NOT_EXISTS;

public class ElementNotExists extends Exception {
    public ElementNotExists(long elementId) {
        super(ELEMENT_NOT_EXISTS.formatted(elementId));
    }
}
