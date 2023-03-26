package cliapp.commands.cli.requirements;

import static commands.requirements.validators.common.StringValidators.booleanValidator;

import cliapp.Messages;
import commands.requirements.Requirement;

/**
 * Requirement for cli fuzzy mode used by SetFuzzyCommand.
 */
public class FuzzyModeRequirement extends Requirement<String, Boolean> {
    public FuzzyModeRequirement() {
        super(
            Messages.SetFuzzyCommand.FUZZY_MODE_REQUIREMENT_NAME,
            Messages.SetFuzzyCommand.FUZZY_MODE_REQUIREMENT_DESCRIPTION,
            booleanValidator);
    }
}
