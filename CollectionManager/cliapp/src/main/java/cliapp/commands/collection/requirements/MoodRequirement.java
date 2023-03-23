package cliapp.commands.collection.requirements;

import static commands.requirements.validators.common.StringValidators.integerValidator;

import cliapp.Messages;
import commands.requirements.Requirement;
import commands.requirements.exceptions.ValidationError;
import commands.requirements.validators.Validator;
import models.Mood;

/**
 * Requirement for the mood of the element.
 */
public class MoodRequirement extends Requirement<String, Mood> {
    public MoodRequirement() {
        super(Messages.ElementRequirements.MOOD, Messages.ElementRequirements.MOOD_DESCR, new MoodValidator());
    }

    private static class MoodValidator implements Validator<String, Mood> {
        @Override
        public Mood validate(String value) throws ValidationError {
            try {
                // if value is number, try to get mood by number
                Integer number = integerValidator.validate(value);
                try {
                    return Mood.values()[number];
                } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
                    throw new ValidationError(value, Mood.class,
                            Messages.ElementRequirements.MOOD_BY_NUMBER_NOT_EXISTS);
                }
            } catch (ValidationError e) {
                // else, try to get mood by name
                try {
                    String moodName = (String) value;
                    moodName = moodName.toUpperCase();
                    return Mood.valueOf(moodName);
                } catch (IllegalArgumentException e1) {
                    throw new ValidationError(value, Mood.class,
                            Messages.ElementRequirements.MOOD_BY_NAME_NOT_EXISTS);
                }
            }
        }
    }
}
