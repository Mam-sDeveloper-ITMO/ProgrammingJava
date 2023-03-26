package cliapp.commands.collection.requirements;

import cliapp.Messages;
import commands.requirements.Requirement;
import commands.requirements.exceptions.ValidationError;
import commands.requirements.validators.Validator;

public class SortOrderRequirement extends Requirement<String, Boolean> {
    public SortOrderRequirement() {
        super(
            Messages.PrintSortedCommand.ORDER_REQUIREMENT_NAME,
            Messages.PrintSortedCommand.ORDER_REQUIREMENT_DESCRIPTION,
            new ExistingIdValidator());
    }

    /**
     * Validate that string is des or asc
     * 
     * @return true if string is des and false if asc
     */
    private static class ExistingIdValidator implements Validator<String, Boolean> {
        @Override
        public Boolean validate(String value) throws ValidationError {
            if (value.equals("des")) {
                return true;
            } else if (value.equals("asc")) {
                return false;
            } else {
                throw new ValidationError(value,
                    Messages.PrintSortedCommand.ORDER_REQUIREMENT_ERROR);
            }
        }
    }
}
