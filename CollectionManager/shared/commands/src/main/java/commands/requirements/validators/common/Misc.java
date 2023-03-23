package commands.requirements.validators.common;

import commands.Messages.MiscValidatorsMessages;
import commands.requirements.exceptions.ValidationError;
import commands.requirements.validators.Validator;
import lombok.RequiredArgsConstructor;

public class Misc {
    /**
     * Wrap validator and return null if ValidationError was ocurred.
     */
    @RequiredArgsConstructor
    public static class OrNullValidator<I, O> implements Validator<I, O> {
        private final Validator<I, O> baseValidator;

        @Override
        public O validate(I value) throws ValidationError {
            try {
                return baseValidator.validate(value);
            } catch (ValidationError e) {
                return null;
            }
        }
    }

    /**
     * Validate that object is not null and return same value.
     */
    public static class NotNullValidator<I> implements Validator<I, I> {
        @Override
        public I validate(I value) throws ValidationError {
            if (value == null) {
                throw new ValidationError(value, MiscValidatorsMessages.NULL_ERROR.formatted(value));
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
                throw new ValidationError(value, MiscValidatorsMessages.GREATER_ERROR.formatted(value, other));
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
                throw new ValidationError(value, MiscValidatorsMessages.LOWER_ERROR.formatted(value, other));
            }
            return value;
        }
    }
}
