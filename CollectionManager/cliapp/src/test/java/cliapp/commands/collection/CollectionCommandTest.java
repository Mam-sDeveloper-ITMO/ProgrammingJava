package cliapp.commands.collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import commands.Command;
import commands.OutputChannel;
import commands.requirements.Requirement;
import commands.requirements.RequirementsPipeline;
import commands.requirements.exceptions.RequirementAskError;
import commands.requirements.exceptions.ValidationError;
import humandeque.manager.CollectionManager;
import humandeque.manager.local.LocalManager;
import models.Car;
import models.Coordinates;
import models.Human;
import models.Mood;

public class CollectionCommandTest {
    private CollectionManager collectionManager;
    private Human human = Human.builder()
            .name("TestName")
            .coordinates(new Coordinates(1.0f, 2.0f))
            .creationDate(null)
            .realHero(true)
            .hasToothpick(false)
            .impactSpeed(1.0)
            .soundtrackName("Civilian Defense")
            .minutesOfWaiting(1.0f)
            .mood(Mood.SADNESS)
            .car(new Car("TestCar"))
            .build();

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
            params.put("name", "TestName");
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

    @Test
    public void testInfoCommand() {
        TestOutput output = new TestOutput();
        Command command = new InfoCommand(collectionManager);
        command.execute(new TestPipeline(), output);
        assertTrue(output.getOutput(), output.getOutput().startsWith("Collection information:"));
    }

    @Test
    public void testShowCommand() {
        TestOutput output = new TestOutput();
        Command command = new ShowCommand(collectionManager);
        command.execute(new TestPipeline(), output);
        assertEquals(output.getOutput(), "Collection is empty.\n");

        output.clear();
        try {
            collectionManager.add(human);
            command.execute(new TestPipeline(), output);
            assertTrue(output.getOutput(), output.getOutput().startsWith("Collection elements:\nHuman(id="));
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testAddElementCommand() {
        TestOutput output = new TestOutput();
        Command command = new AddElementCommand(collectionManager);
        command.execute(new TestPipeline(), output);
        assertTrue(output.getOutput(), output.getOutput().contains("Element added to collection.\n"));

        Human human = collectionManager.getCollection().getFirst();
        assertEquals(human.getName(), "TestName");
        assertEquals(human.getCoordinates().getX(), 1.0f, 0.0001f);
        assertEquals(human.getCoordinates().getY(), 2.0f, 0.0001f);
        assertEquals(human.isRealHero(), true);
        assertEquals(human.isHasToothpick(), false);
        assertEquals(human.getImpactSpeed(), 3.0, 0.0001);
        assertEquals(human.getSoundtrackName(), "testSoundtrackName");
        assertEquals(human.getMinutesOfWaiting(), 4.0f, 0.0001f);
        assertEquals(human.getMood(), Mood.SADNESS);
        assertEquals(human.getCar().getName(), "testCarName");
        assertTrue(human.getCreationDate() != null);
    }
}
