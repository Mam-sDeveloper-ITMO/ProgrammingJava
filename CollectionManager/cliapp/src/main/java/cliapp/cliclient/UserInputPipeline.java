package cliapp.cliclient;

import java.util.Map;
import java.util.function.Supplier;

import cliapp.TextsManager;
import cliapp.utils.TextColor;
import commands.requirements.Requirement;
import commands.requirements.RequirementsPipeline;
import commands.requirements.exceptions.RequirementAskError;
import commands.requirements.exceptions.ValidationError;
import lombok.RequiredArgsConstructor;
import textlocale.text.TextSupplier;

/**
 * This class provides requirements for commands by asking the user for input.
 */
@RequiredArgsConstructor
public class UserInputPipeline implements RequirementsPipeline {
    static TextSupplier ts = TextsManager.getTexts().getPackage("cliclient.user_input_pipeline")::getText;

    /**
     * Map with names of static requirements and inline params that user input.
     */
    private final Map<String, String> staticRequirementsMap;

    /**
     * The number of attempts to ask a requirement before throwing an error.
     */
    private final int askRequirementAttempts;

    /**
     *
     * A supplier that provides user input from the command line in a safe way.
     */
    private final Supplier<String> userInputSupplier;

    /**
     * Show warning with cause of asking fail and left count of attempts
     *
     * @param requirement the processed requirement.
     * @param attempts    left attempts
     * @param error       error of requirement asking
     */
    private void showAttemptWarning(Requirement<?, ?> requirement, Integer attempts, Throwable error) {
        // wrap error and show warning
        Exception exceptionWrapper = new RequirementAskError(requirement.getName(), error);
        System.out.println(TextColor.getColoredString(exceptionWrapper.getMessage(), TextColor.RED));
        // ask to new try with count of left attempts
        String askText = ts.t("AskRequirementWithAttempts", attempts);
        System.out.println();
        System.out.println(TextColor.getColoredString(askText, TextColor.CYAN));
    }

    /**
     * This method asks a requirement from the user and retries if there are
     * validation errors.
     *
     * @param <I>         the input type for the requirement.
     * @param <O>         the output type for the requirement.
     * @param requirement the requirement to ask from the user.
     * @return the output value of the requirement.
     * @throws RequirementAskError if a ValidationError occurs more than
     *                             askRequirementAttempts times.
     */
    private <I, O> O askRequirementWithAttempts(Requirement<I, O> requirement)
            throws RequirementAskError {

        int attempts = askRequirementAttempts;
        do {
            System.out.print(
                    ts.t("AskRequirement", requirement.getName(), requirement.getDescription()));
            try {
                // try get value
                return requirement.getValue((I) userInputSupplier.get());
            } catch (ValidationError e) {
                // if ValidationError occurs, ask requirement again and decrease attempts
                attempts--;
                showAttemptWarning(requirement, attempts, e);
            }
        } while (attempts > 0);

        // if attempt exceeded
        throw new RequirementAskError(requirement.getName(),
                ts.t("AskRequirementAttemptsError"));
    }

    /**
     * This method asks a requirement from the user.
     * If the requirement is a static requirement, it returns the value from the
     * map.
     * If the requirement is a dynamic requirement, it calls
     * askRequirementWithAttempts().
     *
     * @param <I>         the input type for the requirement.
     * @param <O>         the output type for the requirement.
     * @param requirement the requirement to ask from the user.
     * @return the output value of the requirement.
     * @throws RequirementAskError if there is an error asking the requirement.
     */
    @Override
    public <I, O> O askRequirement(Requirement<I, O> requirement) throws RequirementAskError {
        try {
            if (staticRequirementsMap.containsKey(requirement.getName())) {
                // static requirements
                return requirement.getValue((I) staticRequirementsMap.get(requirement.getName()));
            } else {
                // dynamic requirements
                return askRequirementWithAttempts(requirement);
            }
        } catch (ValidationError e) {
            throw new RequirementAskError(requirement.getName(), e);
        }
    }
}
