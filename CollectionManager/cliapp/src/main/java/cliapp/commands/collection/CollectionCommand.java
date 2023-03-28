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

    /**
     * The receiver of the command.
     */
    protected CollectionManager collectionManager;

    /**
     * Constructs a new CollectionCommand.
     *
     * @param name              the name of the command
     * @param description       the description of the command
     * @param collectionManager the receiver of the command
     */
    public CollectionCommand(String name, String description, CollectionManager collectionManager) {
        super(name, description);
        this.collectionManager = collectionManager;
    }

    /**
     * The requirement for the name of an element.
     */
    public static final Requirement<String, String> nameRequirement = new Requirement<>(
            RequirementsResources.NameRequirement.NAME,
            RequirementsResources.NameRequirement.DESCRIPTION,
            notEmptyValidator);

    /**
     * The requirement for the x-coordinate of an element.
     */
    public static final Requirement<String, Float> coordinateXRequirement = new Requirement<>(
            RequirementsResources.CoordinateXRequirement.NAME,
            RequirementsResources.CoordinateXRequirement.DESCRIPTION,
            floatValidator.and(new GreaterValidator<Float>(-566f)));

    /**
     * The requirement for the y-coordinate of an element.
     */
    public static final Requirement<String, Float> coordinateYRequirement = new Requirement<>(
            RequirementsResources.CoordinateYRequirement.NAME,
            RequirementsResources.CoordinateYRequirement.DESCRIPTION,
            floatValidator.and(new GreaterValidator<Float>(-872f)));

    /**
     * The requirement for the "real hero" property of an element.
     */
    public static final Requirement<String, Boolean> realHeroRequirement = new Requirement<>(
            RequirementsResources.RealHeroRequirement.NAME,
            RequirementsResources.RealHeroRequirement.DESCRIPTION,
            booleanValidator);

    /**
     * The requirement for the "has toothpick" property of an element.
     */
    public static final Requirement<String, Boolean> hasToothpickRequirement = new Requirement<>(
            RequirementsResources.HasToothpickRequirement.NAME,
            RequirementsResources.HasToothpickRequirement.DESCRIPTION,
            booleanValidator);

    /**
     * The requirement for the impact speed of an element.
     */
    public static final Requirement<String, Double> impactSpeedRequirement = new Requirement<>(
            RequirementsResources.ImpactSpeedRequirement.NAME,
            RequirementsResources.ImpactSpeedRequirement.DESCRIPTION,
            doubleValidator);

    /**
     * The requirement for the soundtrack of an element.
     */
    public static final Requirement<String, String> soundtrackRequirement = new Requirement<>(
            RequirementsResources.SoundtrackRequirement.NAME,
            RequirementsResources.SoundtrackRequirement.DESCRIPTION,
            notEmptyValidator);

    /**
     * The requirement for the minutes of waiting of an element.
     */
    public static final Requirement<String, Float> minutesOfWaitingRequirement = new Requirement<>(
            RequirementsResources.MinutesOfWaitingRequirement.NAME,
            RequirementsResources.MinutesOfWaitingRequirement.DESCRIPTION,
            new OrNullValidator<>(floatValidator));

    /**
     * The requirement for the name of a car of an element.
     */
    public static final Requirement<String, String> carNameRequirement = new Requirement<>(
            RequirementsResources.CarRequirement.NAME, RequirementsResources.CarRequirement.DESCRIPTION,
            notEmptyValidator);

    /**
     * Validates a string input to ensure that it can be converted to a Mood object.
     */
    public static final Requirement<String, Mood> moodRequirement = new Requirement<>(
            RequirementsResources.MoodRequirement.NAME, RequirementsResources.MoodRequirement.DESCRIPTION,
            new OrNullValidator<>(new MoodValidator()));

    /**
     * Validates a string input to ensure that it can be converted to a Mood object.
     */
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
        /**
         * Constructor of ExistingIdRequirement.
         *
         * @param aCollectionManager The collection manager used for validating if the
         *                           id exists in the collection.
         */
        public ExistingIdRequirement(CollectionManager aCollectionManager) {
            super(RequirementsResources.IdRequirement.NAME,
                    RequirementsResources.IdRequirement.DESCRIPTION,
                    longValidator.and(new GreaterValidator<Long>(0L)).and(new ExistingIdValidator()));

            collectionManager = aCollectionManager;
        }
    }

    /**
     * Validates a string input to ensure that it must be existing id of element in collection.
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
        /**
         * Constructor of ExistingIdRequirement.
         *
         * @param aCollectionManager The collection manager used for validating if the
         *                           id exists in the collection.
         */
        public NotExistingIdRequirement(CollectionManager aCollectionManager) {
            super(RequirementsResources.IdRequirement.NAME,
                    RequirementsResources.IdRequirement.DESCRIPTION,
                    longValidator.and(new GreaterValidator<Long>(0L)).and(new NotExistingIdValidator()));

            collectionManager = aCollectionManager;
        }
    }

    /**
     * Validates a string input to ensure that it must be not existing id of element in collection.
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
