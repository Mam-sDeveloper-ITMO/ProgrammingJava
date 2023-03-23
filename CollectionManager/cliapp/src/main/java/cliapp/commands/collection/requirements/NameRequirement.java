package cliapp.commands.collection.requirements;

import static commands.requirements.validators.common.StringValidators.notEmptyValidator;

import cliapp.Messages;
import commands.requirements.Requirement;;

/**
 * Requirement for name of the human.
 */
public class NameRequirement extends Requirement<String, String> {
    public NameRequirement() {
        super(Messages.ElementRequirements.NAME, Messages.ElementRequirements.NAME_DESCR, notEmptyValidator);
    }
}
