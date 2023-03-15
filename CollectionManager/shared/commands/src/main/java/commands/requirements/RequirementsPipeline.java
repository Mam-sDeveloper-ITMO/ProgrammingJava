package commands.requirements;

import commands.requirements.exceptions.RequirementAskFailed;
import lombok.RequiredArgsConstructor;

/**
 * This class used for dynamic supply of requirements values to command from
 * external source.
 * 
 * For example, in CLI application you can use this class for asking user to
 * input param, in UI - to show input field, or send request to server.
 */
@RequiredArgsConstructor
public class RequirementsPipeline {
    private final RequirementsProcessor processor;

    /**
     * Ask for requirement value from external source.
     * 
     * @param requirement - requirement for process.
     * @return
     */
    public <T> T askRequirement(Requirement<T> requirement) throws RequirementAskFailed {
        try {
            T value = processor.processRequirement(requirement);
            return value;
        } catch (Exception e) {
            throw new RequirementAskFailed();
        }
    }
}
