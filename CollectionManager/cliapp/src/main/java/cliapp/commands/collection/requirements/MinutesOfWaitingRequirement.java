package cliapp.commands.collection.requirements;

import static commands.requirements.validators.common.StringValidators.floatValidator;

import cliapp.Messages.ElementRequirements;
import commands.requirements.Requirement;
import commands.requirements.validators.common.Misc.OrNullValidator;

/**
 * Requirement for the number of minutes of waiting.
 */
public class MinutesOfWaitingRequirement extends Requirement<String, Float> {
    public MinutesOfWaitingRequirement() {
        super(ElementRequirements.MINUTES_OF_WAITING, ElementRequirements.MINUTES_OF_WAITING_DESCR,
                new OrNullValidator<>(floatValidator));
    }
}
