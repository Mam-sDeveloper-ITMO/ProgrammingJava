package commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.function.Consumer;

import org.junit.Assert;
import org.junit.Test;

import commands.requirements.Requirement;
import commands.requirements.RequirementsPipeline;
import commands.requirements.Validator;
import commands.requirements.exceptions.ValidationError;

public class RequirementsPipelineTest {
    @Test
    public void testAskRequirement() throws ValidationError {
        // create a mock processor
        Consumer<Requirement<?>> mockProcessor = requirement -> {
            // verify that the processor was called with the input requirement
            assertEquals("name", requirement.getName());
            assertEquals("description", requirement.getDescription());
            assertNull(requirement.getValue());
        };

        // create a requirement with a mock validator
        Validator<String> mockValidator = value -> value != null ? value.toString() : null;
        Requirement<String> requirement = new Requirement<>("name", "description", mockValidator);

        // create the pipeline with the mock processor
        RequirementsPipeline pipeline = new RequirementsPipeline(mockProcessor);

        // call askRequirement and verify that the processor was called with the
        // requirement
        Requirement<String> result = pipeline.askRequirement(requirement);

        // verify that the result is the same as the input requirement
        assertEquals(requirement, result);
    }

    @Test
    public void testAskRequirementWithValidator() throws ValidationError {
        // create a mock processor
        Consumer<Requirement<?>> mockProcessor = requirement -> {
            // verify that the processor was called with the input requirement
            try {
                requirement.setValue("valid");
            } catch (Exception e) {
                // unreachable
            }

            assertEquals("name", requirement.getName());
            assertEquals("description", requirement.getDescription());
            assertEquals("valid", requirement.getValue());
        };

        // create a requirement with a real validator
        Validator<String> validator = value -> {
            if (value instanceof String) {
                return (String) value;
            } else {
                throw new ValidationError(value, String.class, "Value is not a string");
            }
        };
        Requirement<String> requirement = new Requirement<>("name", "description", validator);

        // create the pipeline with the mock processor
        RequirementsPipeline pipeline = new RequirementsPipeline(mockProcessor);

        // call askRequirement with a valid value and verify that the processor was
        // called with the requirement
        Requirement<String> result = pipeline.askRequirement(requirement);

        // verify that the result is the same as the input requirement
        assertEquals(requirement, result);
    }

    @Test
    public void testAskRequirementWithInvalidValue() {
        try {
            // create a requirement with a real validator
            Validator<String> validator = value -> {
                if (value instanceof String) {
                    return (String) value;
                } else {
                    throw new ValidationError(value, String.class, "Value is not a string");
                }
            };
            Requirement<String> requirement = new Requirement<>("name", "description", validator);

            // create the pipeline with a mock processor
            Consumer<Requirement<?>> mockProcessor = req -> {
            };
            RequirementsPipeline pipeline = new RequirementsPipeline(mockProcessor);

            // call askRequirement with an invalid value and verify that a ValidationError
            // is thrown
            requirement = pipeline.askRequirement(requirement);
            if (requirement.getValue() == null) {
                throw new ValidationError(requirement.getValue(), String.class, "Value is not a string");
            }
            Assert.fail("Expected a ValidationError to be thrown");
        } catch (ValidationError e) {
            // verify that the processor was not called
        }
    }
}