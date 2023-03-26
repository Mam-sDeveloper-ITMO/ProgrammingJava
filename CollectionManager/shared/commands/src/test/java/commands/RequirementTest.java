package commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;
import commands.requirements.Requirement;
import commands.requirements.exceptions.ValidationError;
import commands.requirements.validators.Validator;

public class RequirementTest {
    @Test
    public void testGetValueWithValidValue() throws ValidationError {
        // create a requirement with a real validator
        Validator<String, String> validator = value -> {
            if (value instanceof String) {
                return (String) value;
            } else {
                throw new ValidationError(value, "Value is not a string");
            }
        };
        Requirement<String, String> requirement =
            new Requirement<>("name", "description", validator);

        // call getValue with a valid value and verify that the result is correct
        String result = requirement.getValue("valid");
        assertEquals("valid", result);
    }

    @Test
    public void testGetValueWithInvalidValue() {
        // create a requirement with a real validator
        Validator<Object, String> validator = value -> {
            if (value instanceof String) {
                return (String) value;
            } else {
                throw new ValidationError(value, "Value is not a string");
            }
        };
        Requirement<Object, String> requirement =
            new Requirement<>("name", "description", validator);

        // call getValue with an invalid value and verify that a ValidationError is thrown
        try {
            requirement.getValue(1);
            fail("Expected a ValidationError to be thrown");
        } catch (ValidationError e) {
            // expected
        }
    }
}
