package cliapp.cliclient.exceptions;

import textlocale.TextLocale;
import textlocale.TextSupplier;

/**
 * Exception thrown when the CLI client cannot find a command by its trigger.
 */
public class CommandNotFoundError extends Exception {
    static TextSupplier ts = TextLocale.getPackage("cliclient.exceptions")::getText;

    /**
     * Constructs a new CommandNotFoundError with the specified trigger.
     *
     * @param trigger the trigger that was not found
     */
    public CommandNotFoundError(String trigger) {
        super(ts.t("CommandNotFoundError", trigger));
    }
}
