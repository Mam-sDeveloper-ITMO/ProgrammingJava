package cliapp.commands.collection;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import cliapp.commands.collection.ElementCommand;
import commands.Command;
import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.Requirement;
import commands.requirements.RequirementsPipeline;
import commands.requirements.exceptions.RequirementAskError;
import commands.requirements.exceptions.ValidationError;
import humandeque.manager.CollectionManager;
import humandeque.manager.local.LocalManager;
import models.Human;

public class ElementCommandTest {
    class TestCommand extends ElementCommand {
        public TestCommand(String name, String description, CollectionManager collectionManager) {
            super(name, description, collectionManager);
        }

        @Override
        public void execute(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
            try {
                Human human = askHuman(pipeline, output);
                output.putString(human.getName());
            } catch (RequirementAskError e) {
                throw new ExecutionError("Error while asking human", e);
            }
        }
    }

    class TestPipeline implements RequirementsPipeline {
        Map<String, String> params = new HashMap<>();

        public TestPipeline() {
            params.put("name", "testName");
            params.put("x coordinate", "1");
            params.put("y coordinate", "2");
            params.put("real hero", "true");
            params.put("has toothpick", "false");
            params.put("impact speed", "3");
            params.put("soundtrack name", "testSoundtrackName");
            params.put("minutes of waiting", "4");
            params.put("mood", "SADNESS");
            params.put("car name", "testCarName");
        }

        @Override
        public <T> T askRequirement(Requirement<T> requirement) throws RequirementAskError {
            try {
                return requirement.getValue(params.get(requirement.getName()));
            } catch (ValidationError e) {
                throw new RequirementAskError(e.getMessage());
            }
        }
    }

    @Test
    public void testElementCommand() throws ExecutionError, RequirementAskError {
        try {
            CollectionManager collectionManager = new LocalManager();
            Command command = new TestCommand("test", "test", collectionManager);
            command.execute(new TestPipeline(), System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}
