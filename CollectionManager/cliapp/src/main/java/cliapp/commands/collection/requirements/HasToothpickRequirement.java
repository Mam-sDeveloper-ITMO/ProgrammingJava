package cliapp.commands.collection.requirements;

import static commands.requirements.validators.common.StringValidators.booleanValidator;

import cliapp.Messages;
import commands.requirements.Requirement;

/**
 * Requirement for the "has toothpick" flag of an element.
 */
public class HasToothpickRequirement extends Requirement<String, Boolean> {
    public HasToothpickRequirement() {
        super(Messages.ElementRequirements.HAS_TOOTHPICK, Messages.ElementRequirements.HAS_TOOTHPICK_DESCR,
                booleanValidator);
    }
}
