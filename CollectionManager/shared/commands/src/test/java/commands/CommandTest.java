package commands;

import java.util.List;

import org.junit.Test;

import commands.exceptions.ExecutionError;
import commands.requirements.Requirement;
import commands.requirements.RequirementsPipeline;
import commands.requirements.RequirementsProcessor;
import commands.requirements.validators.common.IntegerValidator;

public class CommandTest {
    class CommandImpl extends Command {
        public CommandImpl(Float age, RequirementsPipeline pipeline) {
            super("Test command", "test descr" + age, pipeline);
        }

        @Override
        public List<Requirement<?>> getStaticRequirements() {
            return List.of(new Requirement<>("Float", "Some Float", new IntegerValidator()));
        }

        @Override
        public void execute() throws ExecutionError {
            try {
                System.out.println(
                        getPipeline()
                                .askRequirement(new Requirement<>("Integer", "Some Integer", new IntegerValidator())));
            } catch (Exception e) {
                throw new ExecutionError("Failed to execute command", e);
            }
        }
    }

    class RequirementsProcessorImpl implements RequirementsProcessor {
        @Override
        public <T> T processRequirement(Requirement<T> requirement) throws Exception {
            if (requirement.getName().equals("Integer")) {
                return (T) Integer.valueOf(1);
            }
            return null;
        }

    }

    @Test
    public void test() {
        CommandImpl command = new CommandImpl(1.0f, new RequirementsPipeline(new RequirementsProcessorImpl()));
        command.execute();
    }
}
