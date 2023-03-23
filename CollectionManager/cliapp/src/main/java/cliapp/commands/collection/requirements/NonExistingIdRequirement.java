package cliapp.commands.collection.requirements;

import static commands.requirements.validators.common.StringValidators.longValidator;

import cliapp.Messages;
import commands.requirements.Requirement;
import commands.requirements.exceptions.ValidationError;
import commands.requirements.validators.Validator;
import commands.requirements.validators.common.Misc.GreaterValidator;
import humandeque.manager.CollectionManager;

/**
 * Requirement for human Id. Checks if id is correct and exists in collection.
 */
public class NonExistingIdRequirement extends Requirement<String, Long> {
    private static CollectionManager collectionManager;

    public NonExistingIdRequirement(CollectionManager aCollectionManager) {
        super(Messages.ElementRequirements.ID, Messages.ElementRequirements.ID_DESCR,
                longValidator.and(new GreaterValidator<Long>(0L)).and(new NonExistingIdValidator()));

        collectionManager = aCollectionManager;
    }

    /**
     * Validate that id does not exist in collection
     */
    private static class NonExistingIdValidator implements Validator<Long, Long> {
        @Override
        public Long validate(Long value) throws ValidationError {
            if (collectionManager.isElementExists(value)) {
                throw new ValidationError(value, Integer.class, Messages.ElementRequirements.ID_NOT_EXISTS);
            }
            return value;
        }
    }
}