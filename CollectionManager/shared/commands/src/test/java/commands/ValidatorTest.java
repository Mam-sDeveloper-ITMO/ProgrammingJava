package commands;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;

import commands.requirements.exceptions.ValidationError;
import commands.requirements.validators.Validator;
import commands.requirements.validators.common.DateTimeValidator;
import commands.requirements.validators.common.DoubleValidator;
import commands.requirements.validators.common.FloatValidator;
import commands.requirements.validators.common.IntegerValidator;
import commands.requirements.validators.common.NotNullValidator;

public class ValidatorTest {
    /**
     * Test validator
     * 
     * Convert to positive integer
     */
    private class TestValidator implements Validator<Integer> {
        @Override
        public Integer validate(Object value) throws ValidationError {
            if (value instanceof String) {
                try {
                    Integer result = Integer.parseInt((String) value);
                    if (result > 0) {
                        return result;
                    } else {
                        throw new ValidationError(value, Integer.class, "Not positive");
                    }
                } catch (NumberFormatException e) {
                    throw new ValidationError(value, Integer.class, "Not a number");
                }
            } else if (value instanceof Integer) {
                if ((Integer) value > 0) {
                    return (Integer) value;
                } else {
                    throw new ValidationError(value, Integer.class, "Not positive");
                }
            }
            throw new ValidationError(value, Integer.class, "Can convert only String or Integer values");
        }
    }

    private TestValidator validator = new TestValidator();

    @Test
    public void testValidationError() {
        try {
            validator.validate("0");
            Assert.fail("Should throw ValidationError");
        } catch (ValidationError e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testFromString() {
        try {
            Integer result = validator.validate("1");
        } catch (ValidationError e) {
            Assert.fail("Should not throw ValidationError");
        }
    }

    @Test
    public void testFromInteger() {
        try {
            Integer result = validator.validate(10);
        } catch (ValidationError e) {
            Assert.fail("Should not throw ValidationError");
        }
    }

    @Test
    public void testIntegerValidator() {
        Validator<Integer> validator = new IntegerValidator();
        Integer value;
        try {
            value = validator.validate("1");
            assertEquals(1, value.intValue());

            value = validator.validate(1);
            assertEquals(1, value.intValue());
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    @Test
    public void testFloatValidator() {
        Validator<Float> validator = new FloatValidator();
        Float value;
        try {
            value = validator.validate("1.1");
            assertEquals(1.1f, value, 0.0001);

            value = validator.validate(1.1f);
            assertEquals(1.1f, value, 0.0001);
        } catch (Exception e) {
        }
    }

    @Test
    public void testDoubleValidator() {
        Validator<Double> validator = new DoubleValidator();
        Double value;
        try {
            value = validator.validate("1");
            assertEquals(1.0, value, 0.0001);

            value = validator.validate(1.0d);
            assertEquals(1.0, value, 0.0001);
        } catch (Exception e) {
        }
    }

    @Test
    public void testDateTimeValidator() {
        Validator<LocalDateTime> validator = new DateTimeValidator();
        LocalDateTime value;
        try {
            value = validator.validate("2017-01-01 00:00:00");
            assertEquals(2017, value.getYear());
            assertEquals(1, value.getMonthValue());
            assertEquals(1, value.getDayOfMonth());
            assertEquals(0, value.getHour());
            assertEquals(0, value.getMinute());
            assertEquals(0, value.getSecond());

            value = validator.validate("2017-01-01 00:00:00.000");
            assertEquals(2017, value.getYear());
            assertEquals(1, value.getMonthValue());
            assertEquals(1, value.getDayOfMonth());
            assertEquals(0, value.getHour());
            assertEquals(0, value.getMinute());
            assertEquals(0, value.getSecond());
        } catch (Exception e) {
        }
    }

    @Test
    public void testNotNullValidator() {
        Validator<Object> validator = new NotNullValidator();
        try {
            validator.validate("1");
            validator.validate(1);
            validator.validate(1.0);
            validator.validate(1.0f);
            validator.validate(LocalDateTime.now());
        } catch (Exception e) {
        }
    }
}
