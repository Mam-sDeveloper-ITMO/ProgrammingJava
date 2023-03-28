package commands.exceptions;

import commands.TextResources.Commands.ExceptionsResources;

/**
 * That exception is thrown when command execution failed
 * 
 */
public class ExecutionError extends Exception {
    public ExecutionError(String message) {
        super(ExceptionsResources.EXECUTION_ERROR.formatted(message));
    }

    public ExecutionError(String message, Throwable cause) {
        super(ExceptionsResources.EXECUTION_ERROR_WITH_CAUSE.formatted(message, cause.getMessage()), cause);
    }
}
