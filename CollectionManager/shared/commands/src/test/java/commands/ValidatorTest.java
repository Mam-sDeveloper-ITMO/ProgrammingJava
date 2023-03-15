package commands;

import org.junit.Assert;
import org.junit.Test;

import commands.requirements.Validator;
import commands.requirements.exceptions.ValidationError;

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
}
