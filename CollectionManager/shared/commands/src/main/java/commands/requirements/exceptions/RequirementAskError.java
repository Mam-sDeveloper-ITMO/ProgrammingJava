package commands.requirements.exceptions;

import commands.TextResources.Requirements.ExceptionsResources;;

/**
 * Occurred when RequirementPipeline cannot correctly ask requirement, for
 * example due to many
 * attempts in CLI
 */
public class RequirementAskError extends Exception {
    public RequirementAskError(String requirementName) {
        super(ExceptionsResources.REQUIREMENT_ASK_ERROR.formatted(requirementName));
    }

    public RequirementAskError(String requirementName, Exception cause) {
        super(
                ExceptionsResources.REQUIREMENT_ASK_ERROR_WITH_CAUSE.formatted(
                        requirementName,
                        cause.getMessage()),
                cause);
    }

    public RequirementAskError(String requirementName, String message) {
        super(ExceptionsResources.REQUIREMENT_ASK_ERROR_WITH_MESSAGE.formatted(requirementName, message));
    }

    public RequirementAskError(String requirementName, String message, Exception cause) {
        super(
                ExceptionsResources.REQUIREMENT_ASK_ERROR_WITH_MESSAGE_AND_CAUSE.formatted(
                        requirementName,
                        message,
                        cause.getMessage()),
                cause);
    }
}
