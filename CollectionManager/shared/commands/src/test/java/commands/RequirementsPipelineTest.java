package commands;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

import commands.requirements.Requirement;
import commands.requirements.RequirementsPipeline;
import commands.requirements.RequirementsProcessor;
import commands.requirements.exceptions.RequirementAskFailed;

public class RequirementsPipelineTest {

    private static class MockProcessor implements RequirementsProcessor {

        @Override
        public <T> T processRequirement(Requirement<T> requirement) throws RequirementAskFailed {
            return (T) "test";
        }

    }

    private static class MockIncorrectProcessor implements RequirementsProcessor {

        @Override
        public <T> T processRequirement(Requirement<T> requirement) throws Exception {
            throw new IllegalArgumentException();
        }

    }

    @Test
    public void testAskRequirementWithValidRequirement() throws RequirementAskFailed, RequirementAskFailed {
        RequirementsProcessor processor = new MockProcessor();
        RequirementsPipeline pipeline = new RequirementsPipeline(processor);

        Requirement<String> requirement = new Requirement<>("name", "description", value -> value.toString());
        String value = pipeline.askRequirement(requirement);
        assertEquals("test", value);
    }

    @Test
    public void testAskRequirementWithInvalidRequirement() {
        RequirementsProcessor processor = new MockIncorrectProcessor();
        RequirementsPipeline pipeline = new RequirementsPipeline(processor);

        Requirement<String> requirement = new Requirement<>("name", "description", value -> value.toString());
        try {
            pipeline.askRequirement(requirement);
            Assert.fail("Expected a RequirementAskFailed to be thrown");
        } catch (RequirementAskFailed e) {
            // expected
        }
    }

}