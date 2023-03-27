package cliapp.cliclient.exceptions;

import java.util.List;

import cliapp.Messages;
import commands.requirements.Requirement;
import lombok.RequiredArgsConstructor;

/**
 * Exception thrown when the number of inline parameters is not equal to the
 * expected number of command static requirements.
 */
@RequiredArgsConstructor
public class InlineParamsError extends Exception {
    private final List<Requirement<?, ?>> requirements;

    @Override
    public String getMessage() {
        // join names of requirements in quotes by space
        String requirementsNames = requirements
                .stream()
                .map(Requirement::getName)
                .map(name -> "\"" + name + "\"")
                .reduce((name1, name2) -> name1 + " " + name2)
                .orElse("");

        return Messages.CLIClient.INLINE_PARAMS_ERROR.formatted(requirementsNames);
    }
}
