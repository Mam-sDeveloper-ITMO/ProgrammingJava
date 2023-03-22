package commands.exceptions;

import static commands.Messages.Commands.EXECUTION_ERROR;

/**
 * That exception is thrown when command execution failed
 * 
 */
public class ExecutionError extends Exception {
    public ExecutionError(String message) {
        super(EXECUTION_ERROR.formatted(message));
    }

    public ExecutionError(String message, Throwable cause) {
        super(EXECUTION_ERROR.formatted(message), cause);
    }
}
