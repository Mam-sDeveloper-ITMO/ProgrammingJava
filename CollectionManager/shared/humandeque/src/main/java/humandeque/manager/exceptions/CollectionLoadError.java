package humandeque.manager.exceptions;

import humandeque.Messages;

/**
 * Throws by CollectionStorage when collection can't be loaded
 */
public class CollectionLoadError extends Exception {
    public CollectionLoadError(String message) {
        super(Messages.Manager.COLLECTION_LOAD_ERROR.formatted(message));
    }

    public CollectionLoadError(String message, Throwable cause) {
        super(Messages.Manager.COLLECTION_SAVE_ERROR.formatted(message), cause);
    }
}
