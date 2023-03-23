package commands.requirements.validators;

import commands.requirements.exceptions.ValidationError;
import lombok.RequiredArgsConstructor;

/*
 * Functional interface for validation and converting requirements values.
 * 
 * You can also combine validators.
 */
@FunctionalInterface
public interface Validator<I, O> {
    /**
     * Validate value.
     * 
     * If valid, return value of <T> type.
     * Except throws ValidationError
     * 
     * @param value - value to validate
     */
    O validate(I value) throws ValidationError;

    /**
     * Internal class that helps aggregate two validators in one
     */
    @RequiredArgsConstructor
    public class ChainValidator<K, L, M> implements Validator<K, M> {
        private Validator<K, L> baseValidator;
        private Validator<L, M> mixinValidator;

        public ChainValidator(Validator<K, L> baseValidator, Validator<L, M> mixinValidator) {
            this.baseValidator = baseValidator;
            this.mixinValidator = mixinValidator;
        }

        public M validate(K value) throws ValidationError {
            L baseValue = baseValidator.validate(value);
            return mixinValidator.validate(baseValue);
        }
    }

    /**
     * Return ChainValidator that include current and additional validation rule
     * 
     * Can be used as chain of validators like
     * {@code validator1.and(new Validator2()).and(new Validator3()).and(new NotNullValidator())}
     */
    default <M> Validator<I, M> and(Validator<O, M> mixinValidator) {
        return new ChainValidator<>(this, mixinValidator);
    }
}
