package commands.requirements.exceptions;

import commands.TextResources.Requirements.ExceptionsResources;

/**
 * Thrown by Validator when cannot convert taken value to target type.
 */
public class ValidationError extends Exception {

    /**
     * Constructs a ValidationError with the specified value and message.
     * 
     * @param value   - the value that caused the error
     * @param message - a detail message describing the error
     */
    public ValidationError(Object value, String message) {
        super(ExceptionsResources.VALIDATION_ERROR.formatted(value, message));
    }
}