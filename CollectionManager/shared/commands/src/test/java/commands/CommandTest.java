package commands;

import java.util.List;

import org.junit.Test;

import commands.exceptions.ExecutionError;
import commands.requirements.Requirement;
import commands.requirements.RequirementsPipeline;
import commands.requirements.exceptions.RequirementAskError;
import commands.requirements.validators.common.IntegerValidator;

public class CommandTest {
    class CommandImpl extends Command {
        public CommandImpl(Float age) {
            super("Test command", "test descr" + age);
        }

        @Override
        public List<Requirement<?>> getStaticRequirements() {
            return List.of(new Requirement<>("Float", "Some Float", new IntegerValidator()));
        }

        @Override
        public void execute(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
            try {
                Integer age = pipeline
                        .askRequirement(new Requirement<Integer>("Integer", "Some Integer", new IntegerValidator()));
                output.putString(age.toString());
            } catch (Exception e) {
                throw new ExecutionError("Failed to execute command", e);
            }
        }
    }

    class RequirementsPipelineImpl implements RequirementsPipeline {
        @Override
        public <T> T askRequirement(Requirement<T> requirement) throws RequirementAskError {
            if (requirement.getName().equals("Integer")) {
                return (T) Integer.valueOf(1);
            }
            return null;
        }

    }

    @Test
    public void test() {
        CommandImpl command = new CommandImpl(1.0f);
        command.execute(new RequirementsPipelineImpl(), System.out::println);
    }
}
