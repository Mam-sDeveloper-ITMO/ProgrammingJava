package cliapp.commands.collection.requirements;

import cliapp.Messages;

import commands.requirements.Requirement;
import commands.requirements.validators.common.IntegerValidator;
import commands.requirements.exceptions.ValidationError;
import commands.requirements.validators.Validator;
import models.Mood;

/**
 * Requirement for the mood of the element.
 */
public class MoodRequirement extends Requirement<Mood> {
    public MoodRequirement() {
        super(Messages.ElementRequirements.MOOD, Messages.ElementRequirements.MOOD_DESCR, new MoodValidator());
    }

    private static class MoodValidator implements Validator<Mood> {
        @Override
        public Mood validate(Object value) throws ValidationError {
            try {
                // if value is number, try to get mood by number
                Integer number = new IntegerValidator().validate(value);
                try {
                    return Mood.values()[number];
                } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
                    throw new ValidationError(value, Mood.class,
                            Messages.ElementRequirements.MOOD_BY_NUMBER_NOT_EXISTS);
                }
            } catch (ValidationError e) {
                // if value is string, try to get mood by name
                if (value instanceof String) {
                    try {
                        String moodName = (String) value;
                        moodName = moodName.toUpperCase();
                        return Mood.valueOf(moodName);
                    } catch (IllegalArgumentException e1) {
                        throw new ValidationError(value, Mood.class,
                                Messages.ElementRequirements.MOOD_BY_NAME_NOT_EXISTS);
                    }
                } else {
                    throw new ValidationError(value, Mood.class, Messages.ElementRequirements.MOOD_VALIDATION_ERROR);
                }
            }
        }
    }
}
