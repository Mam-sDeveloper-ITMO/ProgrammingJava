package cliapp.commands.collection.requirements;

import cliapp.Messages;

import commands.requirements.Requirement;
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
            if (value instanceof Integer) {
                try {
                    return Mood.values()[(Integer) value];
                } catch (IllegalArgumentException e) {
                    throw new ValidationError(value, Mood.class, Messages.ElementRequirements.MOOD_BY_NUMBER_NOT_EXISTS);
                }
            } else if (value instanceof String) {
                try {
                    String moodName = (String) value;
                    moodName = moodName.toUpperCase();
                    return Mood.valueOf(moodName);
                } catch (IllegalArgumentException e) {
                    throw new ValidationError(value, Mood.class, Messages.ElementRequirements.MOOD_BY_NAME_NOT_EXISTS);
                }
            } else {
                throw new ValidationError(value, Mood.class, Messages.ElementRequirements.MOOD_VALIDATION_ERROR);
            }
        }
    }
}
