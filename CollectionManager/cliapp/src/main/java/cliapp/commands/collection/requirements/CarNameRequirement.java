package cliapp.commands.collection.requirements;

import cliapp.Messages;

import commands.requirements.Requirement;
import commands.requirements.validators.common.StringValidator;

/**
 * Requirement for the name of the car.
 */
public class CarNameRequirement extends Requirement<String> {
    public CarNameRequirement() {
        super(Messages.ElementRequirements.CAR_NAME, Messages.ElementRequirements.CAR_NAME_DESCR, new StringValidator());
    }
}
