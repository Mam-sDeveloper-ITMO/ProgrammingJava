package humandeque.manager.exceptions;

import humandeque.TextResources.Manager.ExceptionsResources;;

/**
 * Thrown when collection can't be loaded from storage.
 */
public class CollectionLoadError extends Exception {

    /**
     * Creates a new CollectionLoadError with the specified detail message.
     * 
     * @param message - The detail message.
     */
    public CollectionLoadError(String message) {
        super(ExceptionsResources.COLLECTION_LOAD_ERROR.formatted(message));
    }

    /**
     * Creates a new CollectionLoadError with the specified detail message and
     * cause.
     * 
     * @param message - The detail message.
     * @param cause   - The cause of this exception.
     */
    public CollectionLoadError(String message, Throwable cause) {
        super(ExceptionsResources.COLLECTION_SAVE_ERROR.formatted(message), cause);
    }
}
