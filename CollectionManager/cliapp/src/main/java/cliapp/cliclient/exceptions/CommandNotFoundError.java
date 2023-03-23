package cliapp.cliclient.exceptions;

import cliapp.Messages.CLIClient;;

/**
 * Exception thrown when cli client cannot find command by trigger
 */
public class CommandNotFoundError extends Exception {
    public CommandNotFoundError(String trigger) {
        super(CLIClient.COMMAND_NOT_FOUND_ERROR.formatted(trigger));
    }
}
