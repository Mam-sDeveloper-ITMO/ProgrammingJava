package cliapp.commands.cli.requirements;

import static commands.requirements.validators.common.StringValidators.booleanValidator;

import cliapp.TextResources.Commands.Cli.SetFuzzyCommandResources.FuzzyModeRequirementResources;
import commands.requirements.Requirement;

/**
 * Requirement for cli fuzzy mode used by SetFuzzyCommand.
 */
public class FuzzyModeRequirement extends Requirement<String, Boolean> {
    public FuzzyModeRequirement() {
        super(
                FuzzyModeRequirementResources.NAME,
                FuzzyModeRequirementResources.DESCRIPTION,
                booleanValidator);
    }
}
