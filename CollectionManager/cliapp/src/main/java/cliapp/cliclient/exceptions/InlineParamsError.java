package cliapp.cliclient.exceptions;

import java.util.List;

import commands.requirements.Requirement;
import lombok.RequiredArgsConstructor;
import textlocale.TextLocale;
import textlocale.TextSupplier;

/**
 * Exception thrown when the number of inline parameters is not equal to the
 * expected number of command static requirements.
 */
@RequiredArgsConstructor
public class InlineParamsError extends Exception {
    static TextSupplier ts = TextLocale.getPackage("cliclient.exceptions")::getText;

    private final List<Requirement<?, ?>> requirements;

    /**
     * Returns a message that describes the error, including the names of the
     * requirements that were not satisfied.
     *
     * @return a String containing the error message
     */
    @Override
    public String getMessage() {
        // join names of requirements in quotes by space
        String requirementsNames = requirements
                .stream()
                .map(Requirement::getName)
                .map(name -> "\"" + name + "\"")
                .reduce((name1, name2) -> name1 + " " + name2)
                .orElse("");

        return ts.t("InlineParamsError", requirementsNames);
    }
}
