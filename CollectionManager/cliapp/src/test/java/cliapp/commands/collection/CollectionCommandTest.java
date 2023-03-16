package cliapp.commands.collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import commands.Command;
import commands.OutputChannel;
import commands.requirements.Requirement;
import commands.requirements.RequirementsPipeline;
import commands.requirements.exceptions.RequirementAskError;
import commands.requirements.exceptions.ValidationError;
import humandeque.manager.CollectionManager;
import humandeque.manager.local.LocalManager;

public class CollectionCommandTest {
    private CollectionManager collectionManager;

    {
        try {
            collectionManager = new LocalManager();
        } catch (Exception e) {
            e.printStackTrace();
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

    class TestOutput implements OutputChannel {
        private String output = "";

        @Override
        public void putString(String string) {
            this.output += string + "\n";
            System.out.println(string);
        }

        public String getOutput() {
            return output;
        }

        public void clear() {
            output = "";
        }
    }

    private TestOutput output = new TestOutput();

    @Test
    public void testInfoCommand() {
        Command command = new InfoCommand(collectionManager);
        command.execute(new TestPipeline(), output);
        assertTrue(output.getOutput(), output.getOutput().startsWith("Collection information:"));
    }
}
