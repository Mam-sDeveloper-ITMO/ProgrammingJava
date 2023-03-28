package commands.requirements.exceptions;

import commands.TextResources.Requirements.ExceptionsResources;

/**
 * Throw by Validator when cannot convert taken value to target type
 */
public class ValidationError extends Exception {
    public ValidationError(Object value, String message) {
        super(ExceptionsResources.VALIDATION_ERROR.formatted(value, message));
    }
}
