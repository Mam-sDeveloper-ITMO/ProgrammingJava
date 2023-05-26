package cliapp.cliclient.exceptions;

import static textlocale.TextLocale._;

/**
 * Exception thrown when the CLI client cannot find a command by its trigger.
 */
public class CommandNotFoundError extends Exception {

    /**
     * Constructs a new CommandNotFoundError with the specified trigger.
     *
     * @param trigger the trigger that was not found
     */
    public CommandNotFoundError(String trigger) {
        super(_("cliclient.cliclient.CommandNotFoundError").formatted(trigger));
    }
}
