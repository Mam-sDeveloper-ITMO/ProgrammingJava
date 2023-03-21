package cliapp.cliclient.exceptions;

import cliapp.Messages;

/**
 * Exception thrown when cli client cannot find command by trigger
 */
public class CommandNotFoundError extends Exception {
    public CommandNotFoundError(String trigger) {
        super(Messages.CLIClient.COMMAND_NOT_FOUND_ERROR.formatted(trigger));
    }
}
