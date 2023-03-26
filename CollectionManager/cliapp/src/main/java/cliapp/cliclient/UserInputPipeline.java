package cliapp.cliclient;

import java.util.Map;
import java.util.Scanner;
import cliapp.Messages;
import commands.requirements.Requirement;
import commands.requirements.RequirementsPipeline;
import commands.requirements.exceptions.RequirementAskError;
import commands.requirements.exceptions.ValidationError;
import lombok.RequiredArgsConstructor;

/**
 * Provides static requirements from inline params and dynamic requirements from user input.
 */
@RequiredArgsConstructor
public class UserInputPipeline implements RequirementsPipeline {
    private final Map<String, String> staticRequirementsMap;

    private final Scanner inputScanner;

    private final int askRequirementAttempts;

    /**
     * Ask requirement and ignore ValidationError. If ValidationError occurs, ask requirement again.
     * If ValidationError occurs more than askRequirementAttempts times, throw an error.
     */
    private <I, O> O askRequirementWithAttempts(Requirement<I, O> requirement)
        throws RequirementAskError {

        int attempts = askRequirementAttempts;
        while (attempts > 0) {
            System.out.print(
                Messages.CLIClient.ASK_REQUIREMENT.formatted(
                    requirement.getName(),
                    requirement.getDescription()));

            try {
                // try get value
                return requirement.getValue((I) inputScanner.nextLine());
            } catch (ValidationError e) {
                // if ValidationError occurs, ask requirement again and decrease attempts
                attempts--;
                if (attempts == 0) {
                    // if attempts are over throw error
                    break;
                }
                // wrap error and show warning with count of left attempts
                Exception exceptionWrapper = new RequirementAskError(requirement.getName(), e);
                System.out.println(
                    Messages.CLIClient.ASK_REQUIREMENT_ERROR_WITH_ATTEMPTS.formatted(
                        exceptionWrapper.getMessage(),
                        attempts));
            }
        }
        throw new RequirementAskError(requirement.getName(),
            Messages.CLIClient.ASK_REQUIREMENT_ATTEMPTS_ERROR);
    }

    // for (int i = 0; i < askRequirementAttempts; i++) {
    // try {
    // System.out.print(
    // Messages.CLIClient.ASK_REQUIREMENT.formatted(
    // requirement.getName(),
    // requirement.getDescription()));
    // return requirement.getValue((I) inputScanner.nextLine());
    // } catch (ValidationError e) {
    // int attemptsLeft = askRequirementAttempts - i - 1;
    // // if attempts are over throw error
    // if (attemptsLeft == 0) {
    // break;
    // }
    // // wrap error and show warning with count of left attempts
    // Exception exceptionWrapper = new RequirementAskError(requirement.getName(),
    // e);
    // System.out.println(
    // Messages.CLIClient.ASK_REQUIREMENT_ERROR_WITH_ATTEMPTS.formatted(
    // exceptionWrapper.getMessage(),
    // askRequirementAttempts - i - 1));
    // }
    // }
    // // if attempts are over, throw an error
    // throw new RequirementAskError(requirement.getName(),
    // Messages.CLIClient.ASK_REQUIREMENT_ATTEMPTS_ERROR);
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
