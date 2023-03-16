package cliapp.commands.collection.requirements;

import static cliapp.Messages.ElementCommand.REAL_HERO;
import static cliapp.Messages.ElementCommand.REAL_HERO_DESCR;

import commands.requirements.Requirement;
import commands.requirements.validators.common.BooleanValidator;

/**
 * Requirement for the "real hero" flag of an element.
 */
public class RealHeroRequirement extends Requirement<Boolean> {
    public RealHeroRequirement() {
        super(REAL_HERO, REAL_HERO_DESCR, new BooleanValidator());
    }
}
