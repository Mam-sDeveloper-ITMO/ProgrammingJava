package cliapp.commands.collection.requirements;

import static commands.requirements.validators.common.StringValidators.notEmptyValidator;

import cliapp.Messages;
import commands.requirements.Requirement;

/**
 * Requirement for the name of the car.
 */
public class CarNameRequirement extends Requirement<String, String> {
    public CarNameRequirement() {
        super(Messages.ElementRequirements.CAR_NAME, Messages.ElementRequirements.CAR_NAME_DESCR, notEmptyValidator);
    }
}
