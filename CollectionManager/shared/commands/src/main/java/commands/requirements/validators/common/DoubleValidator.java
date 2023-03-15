package commands.requirements.validators.common;

import commands.requirements.exceptions.ValidationError;
import commands.requirements.validators.Validator;

/**
 * Provides validation for Double type from String and Double
 */
public class DoubleValidator implements Validator<Double> {
    @Override
    public Double validate(Object value) throws ValidationError {
        if (value instanceof Double) {
            return (Double) value;
        } else if (value instanceof String) {
            try {
                return Double.parseDouble((String) value);
            } catch (NumberFormatException e) {
                throw new ValidationError(value, Double.class, "Value is not a double");
            }
        } else {
            throw new ValidationError(value, Double.class, "Value is not a double");
        }
    }
}