package commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import commands.requirements.Requirement;
import commands.requirements.Validator;
import commands.requirements.exceptions.ValidationError;

public class RequirementTest {

    // String Requirement Tests

    @Test
    public void testStringRequirementWithValidValue() throws ValidationError {
        Validator<String> stringValidator = value -> {
            if (!(value instanceof String)) {
                throw new ValidationError(value, String.class, "Value is not a string");
            }
            return (String) value;
        };

        Requirement<String> stringRequirement = new Requirement<>("name", "description", stringValidator);

        String validValue = "hello";
        stringRequirement.setValue(validValue);
        assertEquals(validValue, stringRequirement.getValue());
    }

    @Test
    public void testStringRequirementWithInvalidValue() {
        Validator<String> stringValidator = value -> {
            if (!(value instanceof String)) {
                throw new ValidationError(value, String.class, "Value is not a string");
            }
            return (String) value;
        };

        Requirement<String> stringRequirement = new Requirement<>("name", "description", stringValidator);

        Object invalidValue = 123;
        try {
            stringRequirement.setValue(invalidValue);
            fail("Expected a ValidationError to be thrown");
        } catch (ValidationError e) {
        }
    }

    // Integer Requirement Tests

    @Test
    public void testIntegerRequirementWithValidValue() throws ValidationError {
        Validator<Integer> integerValidator = value -> {
            if (!(value instanceof Integer)) {
                throw new ValidationError(value, Integer.class, "Value is not an integer");
            }
            return (Integer) value;
        };

        Requirement<Integer> integerRequirement = new Requirement<>("name", "description", integerValidator);

        Integer validValue = 123;
        integerRequirement.setValue(validValue);
        assertEquals(validValue, integerRequirement.getValue());
    }

    @Test
    public void testIntegerRequirementWithInvalidValue() {
        Validator<Integer> integerValidator = value -> {
            if (!(value instanceof Integer)) {
                throw new ValidationError(value, Integer.class, "Value is not an integer");
            }
            return (Integer) value;
        };

        Requirement<Integer> integerRequirement = new Requirement<>("name", "description", integerValidator);

        Object invalidValue = "hello";
        try {
            integerRequirement.setValue(invalidValue);
            fail("Expected a ValidationError to be thrown");
        } catch (ValidationError e) {
        }
    }
}
