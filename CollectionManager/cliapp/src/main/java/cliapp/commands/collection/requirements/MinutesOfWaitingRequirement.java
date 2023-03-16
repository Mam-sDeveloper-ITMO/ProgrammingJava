package cliapp.commands.collection.requirements;

import static cliapp.Messages.ElementRequirements.MINUTES_OF_WAITING;
import static cliapp.Messages.ElementRequirements.MINUTES_OF_WAITING_DESCR;

import commands.requirements.Requirement;
import commands.requirements.validators.common.FloatValidator;

/**
 * Requirement for the number of minutes of waiting.
 */
public class MinutesOfWaitingRequirement extends Requirement<Float> {
    public MinutesOfWaitingRequirement() {
        super(MINUTES_OF_WAITING, MINUTES_OF_WAITING_DESCR, new FloatValidator());
    }
}
