package cliapp.commands.collection.requirements;

import static commands.requirements.validators.common.StringValidators.floatValidator;

import cliapp.Messages;
import commands.requirements.Requirement;
import commands.requirements.validators.common.Misc.GreaterValidator;

/**
 * Requirement for the Y coordinate of an element.
 */
public class CoordinatesYRequirement extends Requirement<String, Float> {
    public CoordinatesYRequirement() {
        super(Messages.ElementRequirements.COORDINATE_Y, Messages.ElementRequirements.COORDINATE_Y_DESCR,
                floatValidator.and(new GreaterValidator<Float>(-872f)));
    }
}
