package cliapp.commands.collection.requirements;

import static cliapp.Messages.ElementCommand.COORDINATE_Y;
import static cliapp.Messages.ElementCommand.COORDINATE_Y_DESCR;

import commands.requirements.Requirement;
import commands.requirements.exceptions.ValidationError;
import commands.requirements.validators.common.FloatValidator;

/**
 * Requirement for the Y coordinate of an element.
 */
public class CoordinatesYRequirement extends Requirement<Float> {
    public CoordinatesYRequirement() {
        super(COORDINATE_Y, COORDINATE_Y_DESCR, new CoordinateYValidator());
    }

    private static class CoordinateYValidator extends FloatValidator {
        @Override
        public Float validate(Object value) throws ValidationError {
            Float result = super.validate(value);
            if (result <= -872) {
                throw new ValidationError(value, Float.class, "The Y coordinate must be greater than -872.");
            }
            return result;
        }
    }
}
