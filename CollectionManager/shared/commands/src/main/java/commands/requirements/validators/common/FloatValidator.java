package commands.requirements.validators.common;

import commands.requirements.exceptions.ValidationError;
import commands.requirements.validators.Validator;

/**
 * Provides validation for Float type from String and Float.
 */
public class FloatValidator implements Validator<Float> {
    @Override
    public Float validate(Object value) throws ValidationError {
        if (value instanceof Float) {
            return (Float) value;
        } else if (value instanceof String) {
            try {
                return Float.parseFloat((String) value);
            } catch (NumberFormatException e) {
                throw new ValidationError(value, Float.class, "Value is not a float");
            }
        } else {
            throw new ValidationError(value, Float.class, "Value is not a float");
        }
    }
}