package cliapp.commands.collection.requirements;

import cliapp.Messages;
import commands.requirements.Requirement;
import commands.requirements.exceptions.ValidationError;
import commands.requirements.validators.common.LongValidator;
import humandeque.manager.CollectionManager;

/**
 * Requirement for human Id. Checks if id is correct and exists in collection.
 */
public class IdRequirement extends Requirement<Long> {
    private static CollectionManager collectionManager;

    public IdRequirement(CollectionManager aCollectionManager) {
        super(Messages.ElementRequirements.ID, Messages.ElementRequirements.ID_DESCR, new IdValidator());
        collectionManager = aCollectionManager;
    }

    private static class IdValidator extends LongValidator {
        @Override
        public Long validate(Object value) throws ValidationError {
            Long result = new LongValidator().validate(value);
            if (result <= 0) {
                throw new ValidationError(value, Integer.class, Messages.ElementRequirements.ID_NOT_POSITIVE);
            }
            if (!collectionManager.isElementExists(result)) {
                throw new ValidationError(value, Integer.class, Messages.ElementRequirements.ID_NOT_EXISTS);
            }
            return result;
        }
    }
}