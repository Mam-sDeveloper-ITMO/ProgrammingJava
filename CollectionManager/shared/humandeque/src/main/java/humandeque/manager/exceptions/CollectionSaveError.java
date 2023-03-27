package humandeque.manager.exceptions;

import humandeque.Messages;

/**
 * Throws by CollectionStorage when collection can't be saved
 */
public class CollectionSaveError extends Exception {
    public CollectionSaveError(String message) {
        super(Messages.Manager.COLLECTION_SAVE_ERROR.formatted(message));
    }

    public CollectionSaveError(String message, Throwable cause) {
        super(Messages.Manager.COLLECTION_SAVE_ERROR.formatted(message), cause);
    }
}
