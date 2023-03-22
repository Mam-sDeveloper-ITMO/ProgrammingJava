package cliapp.commands.collection.requirements;

import cliapp.Messages;
import commands.requirements.Requirement;
import commands.requirements.exceptions.ValidationError;
import commands.requirements.validators.common.FloatValidator;

/**
 * Requirement for the Y coordinate of an element.
 */
public class CoordinatesYRequirement extends Requirement<Float> {
    public CoordinatesYRequirement() {
        super(Messages.ElementRequirements.COORDINATE_Y, Messages.ElementRequirements.COORDINATE_Y_DESCR, new CoordinateYValidator());
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
