package cliapp.cliclient.exceptions;

import static textlocale.TextLocale.t;

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
        super(t("cliclient.cliclient.CommandNotFoundError").formatted(trigger));
    }
}
