package cliapp.commands.collection.requirements;

import static cliapp.Messages.ElementRequirements.IMPACT_SPEED;
import static cliapp.Messages.ElementRequirements.IMPACT_SPEED_DESCR;

import commands.requirements.Requirement;
import commands.requirements.validators.common.DoubleValidator;

/**
 * Requirement for the impact speed of an element.
 */
public class ImpactSpeedRequirement extends Requirement<Double> {
    public ImpactSpeedRequirement() {
        super(IMPACT_SPEED, IMPACT_SPEED_DESCR, new DoubleValidator());
    }
}
