package commands.requirements.validators.common;

import commands.requirements.exceptions.ValidationError;
import commands.requirements.validators.Validator;

/**
 * Provides validation for Long type from String and Long.
 */
public class LongValidator implements Validator<Long> {
    @Override
    public Long validate(Object value) throws ValidationError {
        if (value instanceof Long) {
            return (Long) value;
        } else if (value instanceof String) {
            try {
                return Long.parseLong((String) value);
            } catch (NumberFormatException e) {
                throw new ValidationError(value, Long.class, "Value is not a long");
            }
        } else {
            throw new ValidationError(value, Long.class, "Value is not a long");
        }
    }
}