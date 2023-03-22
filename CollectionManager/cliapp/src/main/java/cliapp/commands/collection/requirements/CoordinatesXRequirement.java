package cliapp.commands.collection.requirements;

import cliapp.Messages;
import commands.requirements.Requirement;
import commands.requirements.exceptions.ValidationError;
import commands.requirements.validators.common.FloatValidator;

/**
 * Requirement for the X coordinate of an element.
 */
public class CoordinatesXRequirement extends Requirement<Float> {
    public CoordinatesXRequirement() {
        super(Messages.ElementRequirements.COORDINATE_X, Messages.ElementRequirements.COORDINATE_X_DESCR, new CoordinateXValidator());
    }

    private static class CoordinateXValidator extends FloatValidator {
        @Override
        public Float validate(Object value) throws ValidationError {
            Float result = super.validate(value);
            if (result <= -566) {
                throw new ValidationError(value, Float.class, "The X coordinate must be greater than -566.");
            }
            return result;
        }
    }
}
