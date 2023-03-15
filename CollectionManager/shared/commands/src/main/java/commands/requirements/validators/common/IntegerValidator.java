package commands.requirements.validators.common;

import static commands.Messages.Validators.INTEGER_ERROR;

import commands.requirements.exceptions.ValidationError;
import commands.requirements.validators.Validator;;

/**
 * Provides validation for Integer type from String and Integer
 */
public class IntegerValidator implements Validator<Integer> {
    @Override
    public Integer validate(Object value) throws ValidationError {
        if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (NumberFormatException e) {
                throw new ValidationError(value, Integer.class, INTEGER_ERROR);
            }
        } else {
            throw new ValidationError(value, Integer.class, INTEGER_ERROR);
        }
    }
}
