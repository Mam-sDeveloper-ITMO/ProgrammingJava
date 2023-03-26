package commands.requirements;

import commands.requirements.exceptions.ValidationError;
import commands.requirements.validators.Validator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Requirement encapsulates one Command param and it validation.
 * 
 * Can be used for providing command context from outer sources.
 */
@RequiredArgsConstructor
public class Requirement<I, O> {
    @Getter private final String name;

    @Getter private final String description;

    private final Validator<I, O> validator;

    /**
     * Validate and return value if possible.
     * 
     * @param value
     * @throws ValidationError
     */
    public O getValue(I value) throws ValidationError {
        return validator.validate(value);
    }
}
