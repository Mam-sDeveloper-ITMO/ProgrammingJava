package commands.requirements.exceptions;

import static commands.Messages.Requirements.VALIDATION_ERROR;

/**
 * Throw by Validator when cannot convert taken value to target type
 */
public class ValidationError extends Exception {
    public ValidationError(Object value, Class<?> outputType, String message) {
        super(VALIDATION_ERROR.formatted(value, outputType, message));
    }
}
