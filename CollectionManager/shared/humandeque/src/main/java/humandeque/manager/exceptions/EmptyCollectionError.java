package humandeque.manager.exceptions;

import static humandeque.Messages.Manager;

/**
 * Thrown when collection is empty 
 */
public class EmptyCollectionError extends Exception {
    public EmptyCollectionError() {
        super(Manager.EMPTY_COLLECTION);
    }
}
