package humandeque.manager.exceptions;

import static humandeque.TextResources.Manager.ExceptionsResources;

/**
 * Thrown when collection is empty 
 */
public class EmptyCollectionError extends Exception {
    public EmptyCollectionError() {
        super(ExceptionsResources.EMPTY_COLLECTION);
    }
}
