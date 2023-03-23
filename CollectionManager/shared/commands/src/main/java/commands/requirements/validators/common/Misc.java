package commands.requirements.validators.common;

import commands.requirements.exceptions.ValidationError;
import commands.requirements.validators.Validator;
import commands.Messages.StringValidatorsMessages;;

public class Misc {
    /**
     * Validate that object is not null and return same value.
     */
    public static class NotNullValidator<I> implements Validator<I, I> {
        @Override
        public I validate(I value) throws ValidationError {
            if (value == null) {
                throw new ValidationError(value, Object.class, StringValidatorsMessages.NULL_ERROR);
            }
            return value;
        }
    }
}
