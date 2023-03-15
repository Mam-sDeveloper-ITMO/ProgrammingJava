package commands.requirements;

import commands.requirements.exceptions.ValidationError;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Requirement encapsulates one Command param and it validation.
 * 
 * Can be used for providing command context from outer sources.
 * 

 * @param <T> type of requirement value
 */
@RequiredArgsConstructor
public class Requirement<T> {
    @Getter
    private final String name;

    @Getter
    private final String description;

    private final Validator<T> validator;

    /**
     * Validate and return value if possible.
     * 
     * @param value
     * @throws ValidationError
     */
    public T getValue(Object value) throws ValidationError {
        return validator.validate(value);
    }
}
