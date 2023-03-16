package cliapp.commands.collection.requirements;

import static cliapp.Messages.ElementCommand.SOUNDTRACK_NAME;
import static cliapp.Messages.ElementCommand.SOUNDTRACK_NAME_DESCR;

import commands.requirements.Requirement;
import commands.requirements.validators.common.StringValidator;

/**
 * Requirement for the name of the soundtrack.
 */
public class SoundtrackNameRequirement extends Requirement<String> {
    public SoundtrackNameRequirement() {
        super(SOUNDTRACK_NAME, SOUNDTRACK_NAME_DESCR, new StringValidator());
    }
}
