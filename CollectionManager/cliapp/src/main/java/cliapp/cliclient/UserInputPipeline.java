package cliapp.cliclient;

import java.util.Map;
import java.util.Scanner;

import commands.requirements.Requirement;
import commands.requirements.RequirementsPipeline;
import commands.requirements.exceptions.RequirementAskError;
import commands.requirements.exceptions.ValidationError;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserInputPipeline implements RequirementsPipeline {
    private final Map<String, String> staticRequirementsMap;
    private final Scanner inputScanner;

    @Override
    public <T> T askRequirement(Requirement<T> requirement) throws RequirementAskError {
        try {
            if (staticRequirementsMap.containsKey(requirement.getName())) {
                return requirement.getValue(staticRequirementsMap.get(requirement.getName()));
            }
            return requirement.getValue(inputScanner.nextLine());
        } catch (ValidationError e) {
            throw new RequirementAskError(requirement.getName(), e);
        }
    }
}
