package commands.requirements.validators;

import commands.requirements.exceptions.ValidationError;

/*
 * Functional interface for validation and converting requirements values.
 * 
 * You can also combine validators.
 */
@FunctionalInterface
public interface Validator<T> {
    /**
     * Validate value.
     * 
     * If valid, return value of <T> type.
     * Except throws ValidationError
     * 
     * @param value - value to validate
     */
    T validate(Object value) throws ValidationError;

    /**
     * Internal class that helps aggregate two validators
     */
    public class ChainValidator<K, L> implements Validator<L> {
        private Validator<K> baseValidator;
        private Validator<L> mixinValidator;

        public ChainValidator(Validator<K> baseValidator, Validator<L> mixinValidator) {
            this.baseValidator = baseValidator;
            this.mixinValidator = mixinValidator;
        }

        public L validate(Object value) throws ValidationError {
            K baseValue = baseValidator.validate(value);
            return mixinValidator.validate(baseValue);
        }
    }

    /**
     * Return ChainValidator that include current and additional validation rule.
     * 
     * Can be used as chain of validators like
     * {@code validator1.and(new Validator2()).and(new Validator3())}
     */
    default <K> Validator<K> and(Validator<K> mixinValidator) {
        return new ChainValidator<>(this, mixinValidator);
    }
}
