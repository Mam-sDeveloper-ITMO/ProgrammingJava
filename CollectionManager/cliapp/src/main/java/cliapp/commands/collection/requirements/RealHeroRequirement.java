package cliapp.commands.collection.requirements;

import cliapp.Messages;
import commands.requirements.Requirement;
import commands.requirements.validators.common.BooleanValidator;

/**
 * Requirement for the "real hero" flag of an element.
 */
public class RealHeroRequirement extends Requirement<Boolean> {
    public RealHeroRequirement() {
        super(Messages.ElementRequirements.REAL_HERO, Messages.ElementRequirements.REAL_HERO_DESCR, new BooleanValidator());
    }
}
