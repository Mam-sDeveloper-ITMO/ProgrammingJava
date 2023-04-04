package commands;

import static org.junit.Assert.assertEquals;
import org.junit.Assert;
import org.junit.Test;
import commands.requirements.Requirement;
import commands.requirements.RequirementsPipeline;
import commands.requirements.exceptions.RequirementAskError;

public class RequirementsPipelineTest {

    private static class MockPipeline implements RequirementsPipeline {

        @Override
        public <I, O> O askRequirement(Requirement<I, O> requirement) throws RequirementAskError {
            return (O) "test";
        }

    }

    private static class MockIncorrectPipeline implements RequirementsPipeline {
        @Override
        public <I, O> O askRequirement(Requirement<I, O> requirement) throws RequirementAskError {
            throw new RequirementAskError(requirement.getName());
        }
    }

    @Test
    public void testAskRequirementWithValidRequirement()
        throws RequirementAskError, RequirementAskError {
        RequirementsPipeline pipeline = new MockPipeline();

        Requirement<String, String> requirement =
            new Requirement<>("name", "description", value -> value.toString());
        String value = pipeline.askRequirement(requirement);
        assertEquals("test", value);
    }

    @Test
    public void testAskRequirementWithInvalidRequirement() {
        RequirementsPipeline pipeline = new MockIncorrectPipeline();

        Requirement<String, String> requirement =
            new Requirement<>("name", "description", value -> value.toString());
        try {
            pipeline.askRequirement(requirement);
            Assert.fail("Expected a RequirementAskError to be thrown");
        } catch (RequirementAskError e) {
            // expected
        }
    }

}
