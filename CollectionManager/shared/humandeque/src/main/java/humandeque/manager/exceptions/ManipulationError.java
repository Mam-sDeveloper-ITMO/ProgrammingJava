package humandeque.manager.exceptions;

import humandeque.TextResources.Manager.ExceptionsResources;

/**
 * Thrown when an error occurs during an any collection manipulation.
 */
public class ManipulationError extends Exception {
    /**
     * Creates a new ManipulationError.
     */
    public ManipulationError(String message) {
        super(ExceptionsResources.MANIPULATION_ERROR.formatted(message));
    }
}
