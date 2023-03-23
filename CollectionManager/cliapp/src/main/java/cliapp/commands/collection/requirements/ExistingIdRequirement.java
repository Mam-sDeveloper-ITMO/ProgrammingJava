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
public class ExistingIdRequirement extends Requirement<String, Long> {
    private static CollectionManager collectionManager;

    public ExistingIdRequirement(CollectionManager aCollectionManager) {
        super(Messages.ElementRequirements.ID, Messages.ElementRequirements.ID_DESCR,
                longValidator.and(new GreaterValidator<Long>(0L)).and(new ExistingIdValidator()));

        collectionManager = aCollectionManager;
    }

    /**
     * Validate that id does exists in collection 
     */
    private static class ExistingIdValidator implements Validator<Long, Long> {
        @Override
        public Long validate(Long value) throws ValidationError {
            if (!collectionManager.isElementExists(value)) {
                throw new ValidationError(value, Messages.ElementRequirements.ID_NOT_EXISTS);
            }
            return value;
        }
    }
}