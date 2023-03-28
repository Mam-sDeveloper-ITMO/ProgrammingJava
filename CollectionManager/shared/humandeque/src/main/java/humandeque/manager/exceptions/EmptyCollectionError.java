package humandeque.manager.exceptions;

import static humandeque.TextResources.Manager.ExceptionsResources;

/**
 * Thrown when the collection is empty.
 */
public class EmptyCollectionError extends Exception {

    /**
     * Creates a new EmptyCollectionError.
     */
    public EmptyCollectionError() {
        super(ExceptionsResources.EMPTY_COLLECTION);
    }
}