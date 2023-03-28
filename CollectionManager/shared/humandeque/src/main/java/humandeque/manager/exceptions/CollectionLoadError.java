package humandeque.manager.exceptions;

import humandeque.TextResources.Manager.ExceptionsResources;;

/**
 * Throws by CollectionStorage when collection can't be loaded
 */
public class CollectionLoadError extends Exception {
    public CollectionLoadError(String message) {
        super(ExceptionsResources.COLLECTION_LOAD_ERROR.formatted(message));
    }

    public CollectionLoadError(String message, Throwable cause) {
        super(ExceptionsResources.COLLECTION_SAVE_ERROR.formatted(message), cause);
    }
}
