package cliapp.commands.collection;

import static commands.requirements.validators.common.StringValidators.booleanValidator;
import static commands.requirements.validators.common.StringValidators.doubleValidator;
import static commands.requirements.validators.common.StringValidators.floatValidator;
import static commands.requirements.validators.common.StringValidators.integerValidator;
import static commands.requirements.validators.common.StringValidators.longValidator;
import static commands.requirements.validators.common.StringValidators.notEmptyValidator;
import static textlocale.TextLocale._;

import commands.Command;
import commands.requirements.Requirement;
import commands.requirements.exceptions.ValidationError;
import commands.requirements.validators.Validator;
import commands.requirements.validators.common.Misc.GreaterValidator;
import commands.requirements.validators.common.Misc.OrNullValidator;
import humandeque.manager.CollectionManager;
import models.Mood;

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
            _("commands.collection.requirements.NameRequirement.Name"),
            _("commands.collection.requirements.NameRequirement.Description"),
            notEmptyValidator);

    /**
     * The requirement for the x-coordinate of an element.
     */
    public static final Requirement<String, Float> coordinateXRequirement = new Requirement<>(
            _("commands.collection.requirements.CoordinateXRequirement.Name"),
            _("commands.collection.requirements.CoordinateXRequirement.Description"),
            floatValidator.and(new GreaterValidator<Float>(-566f)));

    /**
     * The requirement for the y-coordinate of an element.
     */
    public static final Requirement<String, Float> coordinateYRequirement = new Requirement<>(
            _("commands.collection.requirements.CoordinateYRequirement.Name"),
            _("commands.collection.requirements.CoordinateYRequirement.Description"),
            floatValidator.and(new GreaterValidator<Float>(-872f)));

    /**
     * The requirement for the "real hero" property of an element.
     */
    public static final Requirement<String, Boolean> realHeroRequirement = new Requirement<>(
            _("commands.collection.requirements.RealHeroRequirement.Name"),
            _("commands.collection.requirements.RealHeroRequirement.Description"),
            booleanValidator);

    /**
     * The requirement for the "has toothpick" property of an element.
     */
    public static final Requirement<String, Boolean> hasToothpickRequirement = new Requirement<>(
            _("commands.collection.requirements.HasToothpickRequirement.Name"),
            _("commands.collection.requirements.HasToothpickRequirement.Description"),
            booleanValidator);

    /**
     * The requirement for the impact speed of an element.
     */
    public static final Requirement<String, Double> impactSpeedRequirement = new Requirement<>(
            _("commands.collection.requirements.ImpactSpeedRequirement.Name"),
            _("commands.collection.requirements.ImpactSpeedRequirement.Description"),
            doubleValidator);

    /**
     * The requirement for the soundtrack of an element.
     */
    public static final Requirement<String, String> soundtrackRequirement = new Requirement<>(
            _("commands.collection.requirements.SoundtrackRequirement.Name"),
            _("commands.collection.requirements.SoundtrackRequirement.Description"),
            notEmptyValidator);

    /**
     * The requirement for the minutes of waiting of an element.
     */
    public static final Requirement<String, Float> minutesOfWaitingRequirement = new Requirement<>(
            _("commands.collection.requirements.MinutesOfWaitingRequirement.Name"),
            _("commands.collection.requirements.MinutesOfWaitingRequirement.Description"),
            new OrNullValidator<>(floatValidator));

    /**
     * The requirement for the name of a car of an element.
     */
    public static final Requirement<String, String> carNameRequirement = new Requirement<>(
            _("commands.collection.requirements.CarRequirement.Name"),
            _("commands.collection.requirements.CarRequirement.Description"),
            notEmptyValidator);

    /**
     * Validates a string input to ensure that it can be converted to a Mood object.
     */
    public static final Requirement<String, Mood> moodRequirement = new Requirement<>(
            _("commands.collection.requirements.MoodRequirement.Name"),
            _("commands.collection.requirements.MoodRequirement.Description"),
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
                    throw new ValidationError(value,
                            _("commands.collection.requirements.MoodRequirement.ByNumberNotExists"));
                }
            } catch (ValidationError e) {
                // else, try to get mood by name
                try {
                    String moodName = (String) value;
                    moodName = moodName.toUpperCase();
                    return Mood.valueOf(moodName);
                } catch (IllegalArgumentException e1) {
                    throw new ValidationError(value,
                            _("commands.collection.requirements.MoodRequirement.ByNameNotExists"));
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
            super(_("commands.collection.requirements.IdRequirement.Name"),
                    _("commands.collection.requirements.IdRequirement.Description"),
                    longValidator.and(new GreaterValidator<Long>(0L)).and(new ExistingIdValidator()));
            collectionManager = aCollectionManager;
        }
    }

    /**
     * Validates a string input to ensure that it must be existing id of element in
     * collection.
     */
    private class ExistingIdValidator implements Validator<Long, Long> {
        @Override
        public Long validate(Long value) throws ValidationError {
            if (!collectionManager.isElementExists(value)) {
                throw new ValidationError(value, _("commands.collection.requirements.IdRequirement.NotExists"));
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
            super(_("commands.collection.requirements.IdRequirement.Name"),
                    _("commands.collection.requirements.IdRequirement.Description"),
                    longValidator.and(new GreaterValidator<Long>(0L)).and(new NotExistingIdValidator()));

            collectionManager = aCollectionManager;
        }
    }

    /**
     * Validates a string input to ensure that it must be not existing id of element
     * in collection.
     */
    private class NotExistingIdValidator implements Validator<Long, Long> {
        @Override
        public Long validate(Long value) throws ValidationError {
            if (collectionManager.isElementExists(value)) {
                throw new ValidationError(value, _("commands.collection.requirements.IdRequirement.Exists"));
            }
            return value;
        }
    }
}
