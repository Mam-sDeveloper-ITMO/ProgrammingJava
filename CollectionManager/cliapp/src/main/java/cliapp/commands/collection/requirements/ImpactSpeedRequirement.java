package cliapp.commands.collection.requirements;

import cliapp.Messages;
import commands.requirements.Requirement;
import commands.requirements.validators.common.DoubleValidator;

/**
 * Requirement for the impact speed of an element.
 */
public class ImpactSpeedRequirement extends Requirement<Double> {
    public ImpactSpeedRequirement() {
        super(Messages.ElementRequirements.IMPACT_SPEED, Messages.ElementRequirements.IMPACT_SPEED_DESCR, new DoubleValidator());
    }
}
