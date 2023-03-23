package cliapp.commands.collection.requirements;

import static commands.requirements.validators.common.StringValidators.floatValidator;

import cliapp.Messages;
import commands.requirements.Requirement;

/**
 * Requirement for the number of minutes of waiting.
 */
public class MinutesOfWaitingRequirement extends Requirement<String, Float> {
    public MinutesOfWaitingRequirement() {
        super(Messages.ElementRequirements.MINUTES_OF_WAITING, Messages.ElementRequirements.MINUTES_OF_WAITING_DESCR,
                floatValidator);
    }
}
