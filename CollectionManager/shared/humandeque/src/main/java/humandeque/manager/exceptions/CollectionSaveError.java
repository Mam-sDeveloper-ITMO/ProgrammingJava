package humandeque.manager.exceptions;

import humandeque.TextResources.Manager.ExceptionsResources;

/**
 * Throws by CollectionStorage when collection can't be saved
 */
public class CollectionSaveError extends Exception {
    public CollectionSaveError(String message) {
        super(ExceptionsResources.COLLECTION_SAVE_ERROR.formatted(message));
    }

    public CollectionSaveError(String message, Throwable cause) {
        super(ExceptionsResources.COLLECTION_SAVE_ERROR.formatted(message), cause);
    }
}
