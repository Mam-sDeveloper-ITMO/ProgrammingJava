package commands.requirements.validators;

import commands.requirements.exceptions.ValidationError;
import lombok.RequiredArgsConstructor;

/**
 * Functional interface for validation and converting requirements values.
 * 
 * You can also combine validators using the {@link #and(Validator)} method.
 */
@FunctionalInterface
public interface Validator<I, O> {
    /**
     * Validate value.
     * 
     * If valid, return value of <T> type.
     * Except throws {@link ValidationError}.
     * 
     * @param value - value to validate.
     * @return validated value of <T> type.
     * @throws ValidationError if the value is invalid.
     */
    O validate(I value) throws ValidationError;

    /**
     * Internal class that helps aggregate two validators in one.
     */
    @RequiredArgsConstructor
    public class ChainValidator<K, L, M> implements Validator<K, M> {
        private final Validator<K, L> baseValidator;
        private final Validator<L, M> mixinValidator;

        public M validate(K value) throws ValidationError {
            L baseValue = baseValidator.validate(value);
            return mixinValidator.validate(baseValue);
        }
    }

    /**
     * Return {@link ChainValidator} that includes the current and additional
     * validation rules.
     * 
     * Can be used as a chain of validators like
     * {@code validator1.and(new Validator2()).and(new Validator3()).and(new NotNullValidator())}.
     * 
     * @param mixinValidator - additional validator to add to the chain.
     * @return a {@link ChainValidator} containing the current and additional
     *         validation rules.
     */
    default <M> Validator<I, M> and(Validator<O, M> mixinValidator) {
        return new ChainValidator<>(this, mixinValidator);
    }
}
