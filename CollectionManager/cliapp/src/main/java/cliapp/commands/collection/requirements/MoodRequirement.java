package cliapp.commands.collection.requirements;

import static cliapp.Messages.ElementCommand.MOOD;
import static cliapp.Messages.ElementCommand.MOOD_DESCR;
import static cliapp.Messages.ElementCommand.MOOD_VALIDATION_ERROR;
import static cliapp.Messages.ElementCommand.MOOD_BY_NAME_NOT_EXISTS;
import static cliapp.Messages.ElementCommand.MOOD_BY_NUMBER_NOT_EXISTS;

import commands.requirements.Requirement;
import commands.requirements.exceptions.ValidationError;
import commands.requirements.validators.Validator;
import models.Mood;

/**
 * Requirement for the mood of the element.
 */
public class MoodRequirement extends Requirement<Mood> {
    public MoodRequirement() {
        super(MOOD, MOOD_DESCR, new MoodValidator());
    }

    private static class MoodValidator implements Validator<Mood> {
        @Override
        public Mood validate(Object value) throws ValidationError {
            if (value instanceof Integer) {
                try {
                    return Mood.values()[(Integer) value];
                } catch (IllegalArgumentException e) {
                    throw new ValidationError(value, Mood.class, MOOD_BY_NUMBER_NOT_EXISTS);
                }
            } else if (value instanceof String) {
                try {
                    String moodName = (String) value;
                    moodName = moodName.toUpperCase();
                    return Mood.valueOf(moodName);
                } catch (IllegalArgumentException e) {
                    throw new ValidationError(value, Mood.class, MOOD_BY_NAME_NOT_EXISTS);
                }
            } else {
                throw new ValidationError(value, Mood.class, MOOD_VALIDATION_ERROR);
            }
        }
    }
}
