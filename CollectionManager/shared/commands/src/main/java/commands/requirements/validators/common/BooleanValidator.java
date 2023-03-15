package commands.requirements.validators.common;

import static commands.Messages.Validators.BOOLEAN_ERROR;

import commands.requirements.exceptions.ValidationError;
import commands.requirements.validators.Validator;

/**
 * Provides validation for Boolean type from String and Boolean
 */
public class BooleanValidator implements Validator<Boolean> {
    @Override
    public Boolean validate(Object value) throws ValidationError {
        if (value instanceof Boolean) {
            return (Boolean) value;
        } else if (value instanceof String) {
            String stringValue = ((String) value).toLowerCase();
            if (stringValue.equals("true")) {
                return true;
            } else if (stringValue.equals("false")) {
                return false;
            } else {
                throw new ValidationError(value, Boolean.class, BOOLEAN_ERROR);
            }
        } else {
            throw new ValidationError(value, Boolean.class, "Value is not a boolean");
        }
    }
}
