package commands.requirements.validators.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import commands.requirements.exceptions.ValidationError;
import commands.requirements.validators.Validator;

/**
 * Provides validation for LocalDateTime type
 * from String in format yyyy-MM-dd HH:mm and LocalDateTime
 */
public class DateTimeValidator implements Validator<LocalDateTime> {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public LocalDateTime validate(Object value) throws ValidationError {
        if (value instanceof LocalDateTime) {
            return (LocalDateTime) value;
        } else if (value instanceof String) {
            try {
                return LocalDateTime.parse((String) value, formatter);
            } catch (DateTimeParseException e) {
                throw new ValidationError(value, LocalDateTime.class, "Value is not a LocalDateTime");
            }
        } else {
            throw new ValidationError(value, LocalDateTime.class, "Value is not a LocalDateTime");
        }
    }
}