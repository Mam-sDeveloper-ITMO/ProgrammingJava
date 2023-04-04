package commands.requirements.validators.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import commands.TextResources.Requirements.Common.StringValidatorsResources;
import commands.requirements.exceptions.ValidationError;
import commands.requirements.validators.Validator;

/**
 * String validators.
 */
public class StringValidators {
    /**
     * Provides validation for String from String that is not empty
     */
    public static Validator<String, String> notEmptyValidator = new Validator<String, String>() {
        @Override
        public String validate(String value) throws ValidationError {
            if (value != null & value.isEmpty()) {
                throw new ValidationError(value, StringValidatorsResources.NOT_EMPTY_VALIDATOR_ERROR.formatted(value));
            }
            return value;
        }
    };

    /**
     * Provides validation for Boolean type from String
     */
    public static Validator<String, Boolean> booleanValidator = new Validator<String, Boolean>() {
        @Override
        public Boolean validate(String value) throws ValidationError {
            String stringValue = ((String) value).toLowerCase();
            if (stringValue.equals("true")) {
                return true;
            } else if (stringValue.equals("false")) {
                return false;
            } else {
                throw new ValidationError(value, StringValidatorsResources.BOOLEAN_VALIDATOR_ERROR.formatted(value));
            }
        }
    };

    /**
     * Provides validation for Integer type from String
     */
    public static Validator<String, Integer> integerValidator = new Validator<String, Integer>() {
        @Override
        public Integer validate(String value) throws ValidationError {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                throw new ValidationError(value, StringValidatorsResources.INTEGER_VALIDATOR_ERROR.formatted(value));
            }
        }
    };

    /**
     * Provides validation for Long type from String
     */
    public static Validator<String, Long> longValidator = new Validator<String, Long>() {
        @Override
        public Long validate(String value) throws ValidationError {
            try {
                return Long.parseLong(value);
            } catch (NumberFormatException e) {
                throw new ValidationError(value, StringValidatorsResources.LONG_VALIDATOR_ERROR.formatted(value));
            }
        }
    };

    /**
     * Provides validation for Float type from String
     */
    public static Validator<String, Float> floatValidator = new Validator<String, Float>() {
        @Override
        public Float validate(String value) throws ValidationError {
            try {
                return Float.parseFloat(value);
            } catch (NumberFormatException e) {
                throw new ValidationError(value, StringValidatorsResources.FLOAT_VALIDATOR_ERROR.formatted(value));
            }
        }
    };

    /**
     * Provides validation for Double type from String
     */
    public static Validator<String, Double> doubleValidator = new Validator<String, Double>() {
        @Override
        public Double validate(String value) throws ValidationError {
            try {
                return Double.parseDouble(value);
            } catch (NumberFormatException e) {
                throw new ValidationError(value, StringValidatorsResources.DOUBLE_VALIDATOR_ERROR.formatted(value));
            }
        }
    };

    /**
     * Provides validation from String with date format yyyy-MM-dd HH:mm
     * to LocalDateTime
     */
    public static Validator<String, LocalDateTime> dateTimeValidator = new Validator<String, LocalDateTime>() {
        private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        @Override
        public LocalDateTime validate(String value) throws ValidationError {
            try {
                return LocalDateTime.parse(value, formatter);
            } catch (DateTimeParseException e) {
                throw new ValidationError(value, StringValidatorsResources.DATETIME_VALIDATOR_ERROR.formatted(value));
            }
        }
    };
}
