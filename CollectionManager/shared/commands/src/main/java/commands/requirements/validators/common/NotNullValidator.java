package commands.requirements.validators.common;

import static commands.Messages.Validators.NULL_ERROR;

import commands.requirements.exceptions.ValidationError;
import commands.requirements.validators.Validator;

/**
 * Validate that object is not null and return same value.
 */
public class NotNullValidator implements Validator<Object> {
    @Override
    public Object validate(Object value) throws ValidationError {
        if (value == null) {
            throw new ValidationError(value, Object.class, NULL_ERROR);
        }
        return value;
    }
}