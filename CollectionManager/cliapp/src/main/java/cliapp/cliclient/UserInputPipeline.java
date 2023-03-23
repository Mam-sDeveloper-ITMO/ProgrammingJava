package cliapp.cliclient;

import java.util.Map;
import java.util.Scanner;

import cliapp.Messages.CLIClient;
import commands.requirements.Requirement;
import commands.requirements.RequirementsPipeline;
import commands.requirements.exceptions.RequirementAskError;
import commands.requirements.exceptions.ValidationError;
import lombok.RequiredArgsConstructor;

/**
 * Provides static requirements from inline params and dynamic requirements from
 * user input.
 */
@RequiredArgsConstructor
public class UserInputPipeline implements RequirementsPipeline {
    private final Map<String, String> staticRequirementsMap;
    private final Scanner inputScanner;

    @Override
    public <I, O> O askRequirement(Requirement<I, O> requirement) throws RequirementAskError {
        try {
            // static requirements
            if (staticRequirementsMap.containsKey(requirement.getName())) {
                return requirement.getValue((I) staticRequirementsMap.get(requirement.getName()));
            }
            // ask dynamic requirement
            System.out.print(
                    CLIClient.ASK_REQUIREMENT.formatted(requirement.getName(), requirement.getDescription()));
            // get value
            return requirement.getValue((I) inputScanner.nextLine());
        } catch (ValidationError e) {
            throw new RequirementAskError(requirement.getName(), e);
        }
    }
}
