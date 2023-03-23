package commands.requirements.validators.common;

import commands.Messages.MiscValidatorsMessages;
import commands.requirements.exceptions.ValidationError;
import commands.requirements.validators.Validator;
import lombok.RequiredArgsConstructor;

public class Misc {
    /**
     * Validate that object is not null and return same value.
     */
    public static class NotNullValidator<I> implements Validator<I, I> {
        @Override
        public I validate(I value) throws ValidationError {
            if (value == null) {
                throw new ValidationError(value, Object.class, MiscValidatorsMessages.NULL_ERROR.formatted(value));
            }
            return value;
        }
    }

    /**
     * Validate that object greater than other
     */
    @RequiredArgsConstructor
    public static class GreaterValidator<I extends Comparable<I>> implements Validator<I, I> {
        private final I other;

        @Override
        public I validate(I value) throws ValidationError {
            if (value.compareTo(other) <= 0) {
                throw new ValidationError(value, Object.class,
                        MiscValidatorsMessages.GREATER_ERROR.formatted(value, other));
            }
            return value;
        }
    }

    /**
     * Validate that object lower than other
     */
    @RequiredArgsConstructor
    public static class LowerValidator<I extends Comparable<I>> implements Validator<I, I> {
        private final I other;

        @Override
        public I validate(I value) throws ValidationError {
            if (value.compareTo(other) >= 0) {
                throw new ValidationError(value, Object.class,
                        MiscValidatorsMessages.LOWER_ERROR.formatted(value, other));
            }
            return value;
        }
    }
}
