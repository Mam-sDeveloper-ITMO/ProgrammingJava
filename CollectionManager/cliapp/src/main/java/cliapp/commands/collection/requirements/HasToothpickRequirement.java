package cliapp.commands.collection.requirements;

import static cliapp.Messages.ElementRequirements.HAS_TOOTHPICK;
import static cliapp.Messages.ElementRequirements.HAS_TOOTHPICK_DESCR;

import commands.requirements.Requirement;
import commands.requirements.validators.common.BooleanValidator;

/**
 * Requirement for the "has toothpick" flag of an element.
 */
public class HasToothpickRequirement extends Requirement<Boolean> {
    public HasToothpickRequirement() {
        super(HAS_TOOTHPICK, HAS_TOOTHPICK_DESCR, new BooleanValidator());
    }
}
