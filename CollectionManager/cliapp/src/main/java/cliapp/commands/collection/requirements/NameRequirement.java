package cliapp.commands.collection.requirements;

import cliapp.Messages;
import commands.requirements.Requirement;
import commands.requirements.validators.common.StringValidator;

/**
 * Requirement for name of the human.
 */
public class NameRequirement extends Requirement<String> {
    public NameRequirement() {
        super(Messages.ElementRequirements.NAME, Messages.ElementRequirements.NAME_DESCR, new StringValidator());
    }
}
