package cliapp.commands.collection.requirements;

import cliapp.Messages;
import commands.requirements.Requirement;
import commands.requirements.validators.common.BooleanValidator;

/**
 * Requirement for the "has toothpick" flag of an element.
 */
public class HasToothpickRequirement extends Requirement<Boolean> {
    public HasToothpickRequirement() {
        super(Messages.ElementRequirements.HAS_TOOTHPICK, Messages.ElementRequirements.HAS_TOOTHPICK_DESCR, new BooleanValidator());
    }
}
