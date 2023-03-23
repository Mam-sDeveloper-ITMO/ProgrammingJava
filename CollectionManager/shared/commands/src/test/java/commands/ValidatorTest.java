package commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;

import commands.requirements.exceptions.ValidationError;
import commands.requirements.validators.Validator;
import static commands.requirements.validators.common.StringValidators.*;
import static commands.requirements.validators.common.Misc.*;

public class ValidatorTest {
    /**
     * Test validator
     * 
     * Convert to positive integer
     */
    private class TestValidator implements Validator<String, Integer> {
        @Override
        public Integer validate(String value) throws ValidationError {
            if (value instanceof String) {
                try {
                    Integer result = Integer.parseInt((String) value);
                    if (result > 0) {
                        return result;
                    } else {
                        throw new ValidationError(value, "Not positive");
                    }
                } catch (NumberFormatException e) {
                    throw new ValidationError(value, "Not a number");
                }
            }
            throw new ValidationError(value, "Can convert only String");
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
            Integer result = validator.validate("10");
            assertEquals(result, Integer.valueOf(10));
        } catch (ValidationError e) {
            Assert.fail("Should not throw ValidationError");
        }
    }

    @Test
    public void testIntegerValidator() {
        Integer value;
        try {
            value = integerValidator.validate("1");
            assertEquals(1, value.intValue());
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    @Test
    public void testFloatValidator() {
        Float value;
        try {
            value = floatValidator.validate("1.1");
            assertEquals(1.1f, value, 0.0001);
        } catch (Exception e) {
        }
    }

    @Test
    public void testDoubleValidator() {
        Double value;
        try {
            value = doubleValidator.validate("1");
            assertEquals(1.0, value, 0.0001);
        } catch (Exception e) {
        }
    }

    @Test
    public void testDateTimeValidator() {
        LocalDateTime value;
        try {
            value = dateTimeValidator.validate("2017-01-01 00:00:00");
            assertEquals(2017, value.getYear());
            assertEquals(1, value.getMonthValue());
            assertEquals(1, value.getDayOfMonth());
            assertEquals(0, value.getHour());
            assertEquals(0, value.getMinute());
            assertEquals(0, value.getSecond());

            value = dateTimeValidator.validate("2017-01-01 12:00:00.000");
            assertEquals(2017, value.getYear());
            assertEquals(1, value.getMonthValue());
            assertEquals(1, value.getDayOfMonth());
            assertEquals(12, value.getHour());
            assertEquals(0, value.getMinute());
            assertEquals(0, value.getSecond());
        } catch (Exception e) {
        }
    }

    @Test
    public void testNotEmptyValidator() {
        try {
            String value = notEmptyValidator.validate("1");
            assertEquals("1", value);
        } catch (Exception e) {
        }
    }

    @Test
    public void testNotNullValidator() {
        try {
            String value = new NotNullValidator<String>().validate("1");
            assertEquals("1", value);
        } catch (Exception e) {
        }
        try {
            String value = new NotNullValidator<String>().validate(null);
            Assert.fail("Should throw ValidationError");
        } catch (Exception e) {
        }
    }

    @Test
    public void testOrNullValidator() {
        try {
            Validator<String, Integer> validator = new OrNullValidator<>(integerValidator);
            assertNull(validator.validate("35asfawsffs"));
        } catch (Exception e) {
            Assert.fail("Exception  handled");
        }
        try {
            Validator<String, Integer> validator = new OrNullValidator<>(integerValidator);
            assertEquals(validator.validate("35"), Integer.valueOf(35));
        } catch (Exception e) {
            Assert.fail("Exception  handled");
        }
    }
    @Test
    public void testValidatorsChain() {
        try {
            Integer value = (new NotNullValidator<String>()).and(integerValidator).validate("10");
            assertEquals(Integer.valueOf(10), value);
        } catch (ValidationError e) {
            Assert.fail("Exception  handled");
        }
    }
}
