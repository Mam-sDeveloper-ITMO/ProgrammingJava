package cliapp.commands.collection;

import commands.Command;
import commands.requirements.Requirement;
import commands.requirements.exceptions.ValidationError;
import commands.requirements.validators.Validator;
import humandeque.manager.CollectionManager;
import models.Mood;
import cliapp.TextResources.Commands.Collection.RequirementsResources;
import static commands.requirements.validators.common.StringValidators.*;
import static commands.requirements.validators.common.Misc.*;

/**
 * Basic class of all commands, that use CollectionManager as receiver.
 * 
 * Contains basic requirements instances for collection elements
 */
public abstract class CollectionCommand extends Command {
    protected CollectionManager collectionManager;

    public CollectionCommand(String name, String description, CollectionManager collectionManager) {
        super(name, description);
        this.collectionManager = collectionManager;
    }

    public static final Requirement<String, String> nameRequirement = new Requirement<>(
            RequirementsResources.NameRequirement.NAME,
            RequirementsResources.NameRequirement.DESCRIPTION,
            notEmptyValidator);

    public static final Requirement<String, Float> coordinateXRequirement = new Requirement<>(
            RequirementsResources.CoordinateXRequirement.NAME,
            RequirementsResources.CoordinateXRequirement.DESCRIPTION,
            floatValidator.and(new GreaterValidator<Float>(-566f)));

    public static final Requirement<String, Float> coordinateYRequirement = new Requirement<>(
            RequirementsResources.CoordinateYRequirement.NAME,
            RequirementsResources.CoordinateYRequirement.DESCRIPTION,
            floatValidator.and(new GreaterValidator<Float>(-872f)));

    public static final Requirement<String, Boolean> realHeroRequirement = new Requirement<>(
            RequirementsResources.RealHeroRequirement.NAME,
            RequirementsResources.RealHeroRequirement.DESCRIPTION,
            booleanValidator);

    public static final Requirement<String, Boolean> hasToothpickRequirement = new Requirement<>(
            RequirementsResources.HasToothpickRequirement.NAME,
            RequirementsResources.HasToothpickRequirement.DESCRIPTION,
            booleanValidator);

    public static final Requirement<String, Double> impactSpeedRequirement = new Requirement<>(
            RequirementsResources.ImpactSpeedRequirement.NAME,
            RequirementsResources.ImpactSpeedRequirement.DESCRIPTION,
            doubleValidator);

    public static final Requirement<String, String> soundtrackRequirement = new Requirement<>(
            RequirementsResources.SoundtrackRequirement.NAME,
            RequirementsResources.SoundtrackRequirement.DESCRIPTION,
            notEmptyValidator);

    public static final Requirement<String, Float> minutesOfWaitingRequirement = new Requirement<>(
            RequirementsResources.MinutesOfWaitingRequirement.NAME,
            RequirementsResources.MinutesOfWaitingRequirement.DESCRIPTION,
            new OrNullValidator<>(floatValidator));

    public static final Requirement<String, String> carNameRequirement = new Requirement<>(
            RequirementsResources.CarRequirement.NAME, RequirementsResources.CarRequirement.DESCRIPTION,
            notEmptyValidator);

    public static final Requirement<String, Mood> moodRequirement = new Requirement<>(
            RequirementsResources.MoodRequirement.NAME, RequirementsResources.MoodRequirement.DESCRIPTION,
            new OrNullValidator<>(new MoodValidator()));

    private static class MoodValidator implements Validator<String, Mood> {
        @Override
        public Mood validate(String value) throws ValidationError {
            try {
                // if value is number, try to get mood by number
                Integer number = integerValidator.validate(value);
                try {
                    return Mood.values()[number];
                } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
                    throw new ValidationError(value, RequirementsResources.MoodRequirement.BY_NUMBER_NOT_EXISTS);
                }
            } catch (ValidationError e) {
                // else, try to get mood by name
                try {
                    String moodName = (String) value;
                    moodName = moodName.toUpperCase();
                    return Mood.valueOf(moodName);
                } catch (IllegalArgumentException e1) {
                    throw new ValidationError(value, RequirementsResources.MoodRequirement.BY_NAME_NOT_EXISTS);
                }
            }
        }
    }

    /**
     * Requirement for id of element that must be in collection. Use dynamic access
     * to collectionManager
     */
    public class ExistingIdRequirement extends Requirement<String, Long> {
        public ExistingIdRequirement(CollectionManager aCollectionManager) {
            super(RequirementsResources.IdRequirement.NAME,
                    RequirementsResources.IdRequirement.DESCRIPTION,
                    longValidator.and(new GreaterValidator<Long>(0L)).and(new ExistingIdValidator()));

            collectionManager = aCollectionManager;
        }
    }

    /**
     * Validate that id does exists in collection
     */
    private class ExistingIdValidator implements Validator<Long, Long> {
        @Override
        public Long validate(Long value) throws ValidationError {
            if (!collectionManager.isElementExists(value)) {
                throw new ValidationError(value, RequirementsResources.IdRequirement.NOT_EXISTS);
            }
            return value;
        }
    }

    /**
     * Requirement for id of element must not be in collection. Use dynamic access
     * to collectionManager
     */
    public class NotExistingIdRequirement extends Requirement<String, Long> {
        public NotExistingIdRequirement(CollectionManager aCollectionManager) {
            super(RequirementsResources.IdRequirement.NAME,
                    RequirementsResources.IdRequirement.DESCRIPTION,
                    longValidator.and(new GreaterValidator<Long>(0L)).and(new NotExistingIdValidator()));

            collectionManager = aCollectionManager;
        }
    }

    /**
     * Validate that id does not exist in collection
     */
    private class NotExistingIdValidator implements Validator<Long, Long> {
        @Override
        public Long validate(Long value) throws ValidationError {
            if (collectionManager.isElementExists(value)) {
                throw new ValidationError(value, RequirementsResources.IdRequirement.EXISTS);
            }
            return value;
        }
    }
}
