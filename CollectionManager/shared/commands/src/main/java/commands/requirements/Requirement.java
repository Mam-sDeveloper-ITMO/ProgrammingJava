package commands.requirements;

import commands.requirements.exceptions.ValidationError;
import commands.requirements.validators.Validator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * A requirement encapsulates one Command parameter and its validation.
 * 
 * It can be used to provide command context from outer sources.
 */
@RequiredArgsConstructor
public class Requirement<I, O> {

    /**
     * The name of the requirement.
     */
    @Getter
    private final String name;

    /**
     * A description of the requirement.
     */
    @Getter
    private final String description;

    /**
     * The validator that will be used to validate the requirement.
     */
    private final Validator<I, O> validator;

    /**
     * Validate the input value and return the output value if possible.
     * 
     * @param value - the value to validate
     * @return the output value
     * @throws ValidationError if the value is invalid
     */
    public O getValue(I value) throws ValidationError {
        return validator.validate(value);
    }
}
