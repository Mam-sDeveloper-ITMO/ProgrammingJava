package cliapp.commands.collection.requirements;

import static commands.requirements.validators.common.StringValidators.doubleValidator;

import cliapp.Messages;
import commands.requirements.Requirement;

/**
 * Requirement for the impact speed of an element.
 */
public class ImpactSpeedRequirement extends Requirement<String, Double> {
    public ImpactSpeedRequirement() {
        super(Messages.ElementRequirements.IMPACT_SPEED, Messages.ElementRequirements.IMPACT_SPEED_DESCR,
                doubleValidator);
    }
}
