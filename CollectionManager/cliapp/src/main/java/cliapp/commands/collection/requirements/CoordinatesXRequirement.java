package cliapp.commands.collection.requirements;

import static commands.requirements.validators.common.StringValidators.floatValidator;

import cliapp.Messages;
import commands.requirements.Requirement;
import commands.requirements.validators.common.Misc.GreaterValidator;

/**
 * Requirement for the X coordinate of an element.
 */
public class CoordinatesXRequirement extends Requirement<String, Float> {
    public CoordinatesXRequirement() {
        super(Messages.ElementRequirements.COORDINATE_X, Messages.ElementRequirements.COORDINATE_X_DESCR,
                floatValidator.and(new GreaterValidator<Float>(-566f)));
    }
}
