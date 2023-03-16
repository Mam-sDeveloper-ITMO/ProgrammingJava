package cliapp.commands.collection.requirements;

import static cliapp.Messages.ElementCommand.NAME;
import static cliapp.Messages.ElementCommand.NAME_DESCR;

import commands.requirements.Requirement;
import commands.requirements.validators.common.StringValidator;

/**
 * Requirement for name of the human.
 */
public class NameRequirement extends Requirement<String> {
    public NameRequirement() {
        super(NAME, NAME_DESCR, new StringValidator());
    }
}
