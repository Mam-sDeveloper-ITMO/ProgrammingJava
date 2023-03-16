package cliapp.commands.collection.requirements;

import static cliapp.Messages.ElementCommand.CAR_NAME;
import static cliapp.Messages.ElementCommand.CAR_NAME_DESCR;

import commands.requirements.Requirement;
import commands.requirements.validators.common.StringValidator;

/**
 * Requirement for the name of the car.
 */
public class CarNameRequirement extends Requirement<String> {
    public CarNameRequirement() {
        super(CAR_NAME, CAR_NAME_DESCR, new StringValidator());
    }
}
