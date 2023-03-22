package cliapp.commands.collection.requirements;

import cliapp.Messages;
import commands.requirements.Requirement;
import commands.requirements.validators.common.StringValidator;

/**
 * Requirement for the name of the soundtrack.
 */
public class SoundtrackNameRequirement extends Requirement<String> {
    public SoundtrackNameRequirement() {
        super(Messages.ElementRequirements.SOUNDTRACK_NAME, Messages.ElementRequirements.SOUNDTRACK_NAME_DESCR,
                new StringValidator());
    }
}
