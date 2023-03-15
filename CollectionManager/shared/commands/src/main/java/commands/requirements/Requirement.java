package commands.requirements;

import commands.requirements.exceptions.ValidationError;
import lombok.Data;

/**
 * Requirement encapsulates one Command params and it validation.
 * 
 * Can be used for providing command context from outer sources.
 * 
 * @param <T> type of requirement value
 */
@Data
public class Requirement<T> {
    private final String name;

    private final String description;

    private final Validator<T> validator;

    private T value;

    /**
     * Validate and set value if possible.
     * 
     * @param value
     * @throws ValidationError
     */
    public void setValue(Object value) throws ValidationError {
        this.value = validator.validate(value);
    }
}
