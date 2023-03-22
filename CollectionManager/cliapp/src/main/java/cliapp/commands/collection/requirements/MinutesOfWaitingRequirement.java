package cliapp.commands.collection.requirements;

import cliapp.Messages;
import commands.requirements.Requirement;
import commands.requirements.validators.common.FloatValidator;

/**
 * Requirement for the number of minutes of waiting.
 */
public class MinutesOfWaitingRequirement extends Requirement<Float> {
    public MinutesOfWaitingRequirement() {
        super(Messages.ElementRequirements.MINUTES_OF_WAITING, Messages.ElementRequirements.MINUTES_OF_WAITING_DESCR, new FloatValidator());
    }
}
