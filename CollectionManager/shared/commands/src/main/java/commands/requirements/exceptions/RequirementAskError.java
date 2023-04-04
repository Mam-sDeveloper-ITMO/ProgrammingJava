package commands.requirements.exceptions;

import commands.TextResources.Requirements.ExceptionsResources;;

/**
 * /**
 * Occurs when RequirementPipeline cannot correctly ask requirement, for example
 * due to many attempts in CLI.
 */
public class RequirementAskError extends Exception {

    /**
     * Constructs a RequirementAskError with the specified requirementName.
     * 
     * @param requirementName - the name of the requirement that caused the error
     */
    public RequirementAskError(String requirementName) {
        super(ExceptionsResources.REQUIREMENT_ASK_ERROR.formatted(requirementName));
    }

    /**
     * Constructs a RequirementAskError with the specified requirementName and
     * cause.
     * 
     * @param requirementName - the name of the requirement that caused the error
     * @param cause           - the cause of this error
     */
    public RequirementAskError(String requirementName, Throwable cause) {
        super(
                ExceptionsResources.REQUIREMENT_ASK_ERROR_WITH_CAUSE.formatted(
                        requirementName,
                        cause.getMessage()),
                cause);
    }

    /**
     * Constructs a RequirementAskError with the specified requirementName and
     * message.
     * 
     * @param requirementName - the name of the requirement that caused the error
     * @param message         - a detail message describing the error
     */
    public RequirementAskError(String requirementName, String message) {
        super(ExceptionsResources.REQUIREMENT_ASK_ERROR_WITH_MESSAGE.formatted(requirementName, message));
    }

    /**
     * Constructs a RequirementAskError with the specified requirementName, message,
     * and cause.
     * 
     * @param requirementName - the name of the requirement that caused the error
     * @param message         - a detail message describing the error
     * @param cause           - the cause of this error
     */
    public RequirementAskError(String requirementName, String message, Throwable cause) {
        super(
                ExceptionsResources.REQUIREMENT_ASK_ERROR_WITH_MESSAGE_AND_CAUSE.formatted(
                        requirementName,
                        message,
                        cause.getMessage()),
                cause);
    }
}
