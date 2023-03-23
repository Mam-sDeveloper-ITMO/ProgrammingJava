package cliapp.commands.collection.requirements;

import static commands.requirements.validators.common.StringValidators.notEmptyValidator;

import cliapp.Messages;
import commands.requirements.Requirement;;

/**
 * Requirement for the name of the soundtrack.
 */
public class SoundtrackNameRequirement extends Requirement<String, String> {
    public SoundtrackNameRequirement() {
        super(Messages.ElementRequirements.SOUNDTRACK_NAME, Messages.ElementRequirements.SOUNDTRACK_NAME_DESCR,
                notEmptyValidator);
    }
}
