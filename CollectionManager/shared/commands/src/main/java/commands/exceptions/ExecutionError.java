package commands.exceptions;

import commands.Messages.Commands;

/**
 * That exception is thrown when command execution failed
 * 
 */
public class ExecutionError extends Exception {
    public ExecutionError(String message) {
        super(Commands.EXECUTION_ERROR.formatted(message));
    }

    public ExecutionError(String message, Throwable cause) {
        super(Commands.EXECUTION_ERROR_WITH_CAUSE.formatted(message, cause.getMessage()), cause);
    }
}
