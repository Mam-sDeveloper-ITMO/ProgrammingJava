package commands.requirements;

import commands.requirements.exceptions.ValidationError;

/*
 * Functional interface for validation and converting requirements values.
 */
@FunctionalInterface
public interface Validator<T> {
    /**
     * Validate value.
     * 
     * If valid, return value of <T> type.
     * Except throws ValidationError
     * 
     */
    T validate(Object value) throws ValidationError;
}
