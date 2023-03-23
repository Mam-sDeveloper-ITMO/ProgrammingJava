package cliapp.cliclient.exceptions;

import cliapp.Messages.CLIClient;

/**
 * Exception thrown when the number of inline parameters is not equal to the
 * expected number of command static requirements.
 */
public class InlineParamsCountError extends Exception {
    public InlineParamsCountError(int expected, int actual) {
        super(CLIClient.COMMAND_NOT_FOUND_ERROR.formatted(expected, actual));
    }
}
