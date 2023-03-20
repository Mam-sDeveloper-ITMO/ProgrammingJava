package commands.requirements.exceptions;

import static commands.Messages.Requirements.REQUIREMENT_ASK_ERROR;
import static commands.Messages.Requirements.REQUIREMENT_ASK_ERROR_WITH_MESSAGE;;

/**
 * Occurred when RequirementPipeline cannot correctly ask requirement,
 * for example due to many attempts in CLI
 */
public class RequirementAskError extends Exception {
    public RequirementAskError(String requirementName) {
        super(REQUIREMENT_ASK_ERROR.formatted(requirementName));
    }

    public RequirementAskError(String requirementName, Exception cause) {
        super(REQUIREMENT_ASK_ERROR.formatted(requirementName), cause);
    }

    public RequirementAskError(String requirementName, String message) {
        super(REQUIREMENT_ASK_ERROR_WITH_MESSAGE.formatted(requirementName, message));
    }

    public RequirementAskError(String requirementName, String message, Exception cause) {
        super(REQUIREMENT_ASK_ERROR_WITH_MESSAGE.formatted(requirementName, message), cause);
    }
}
