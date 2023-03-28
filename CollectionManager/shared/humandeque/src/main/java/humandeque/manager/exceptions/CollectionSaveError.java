package humandeque.manager.exceptions;

import humandeque.TextResources.Manager.ExceptionsResources;

/**
 * Thrown when collection can't be saved to storage.
 */
public class CollectionSaveError extends Exception {

    /**
     * Creates a new CollectionSaveError with the specified detail message.
     * 
     * @param message - The detail message.
     */
    public CollectionSaveError(String message) {
        super(ExceptionsResources.COLLECTION_SAVE_ERROR.formatted(message));
    }

    /**
     * Creates a new CollectionSaveError with the specified detail message and
     * cause.
     * 
     * @param message - The detail message.
     * @param cause   - The cause of this exception.
     */
    public CollectionSaveError(String message, Throwable cause) {
        super(ExceptionsResources.COLLECTION_SAVE_ERROR.formatted(message), cause);
    }
}
