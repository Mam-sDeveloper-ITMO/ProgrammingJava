package cliapp.cliclient.exceptions;

import static textlocale.TextLocale.t;

import java.util.List;

import commands.requirements.Requirement;
import lombok.RequiredArgsConstructor;

/**
 * Exception thrown when the number of inline parameters is not equal to the
 * expected number of command static requirements.
 */
@RequiredArgsConstructor
public class InlineParamsError extends Exception {
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

        return t("cliclient.cliclient.InlineParamsError").formatted(requirementsNames);
    }
}
