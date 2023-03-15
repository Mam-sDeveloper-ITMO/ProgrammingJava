package commands.requirements.validators.common;

import commands.requirements.exceptions.ValidationError;
import commands.requirements.validators.Validator;

/**
 * Provides validation for String type from any type
 */
public class StringValidator implements Validator<String> {
    @Override
    public String validate(Object value) throws ValidationError {
        return value.toString();
    }
}
