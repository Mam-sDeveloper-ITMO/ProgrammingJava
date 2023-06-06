package cliapp.commands.collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import cliapp.TextsManager;
import commands.Command;
import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.Requirement;
import commands.requirements.RequirementsPipeline;
import commands.requirements.exceptions.RequirementAskError;
import commands.requirements.exceptions.ValidationError;
import humandeque.manager.CollectionManager;
import humandeque.manager.exceptions.ElementAlreadyExistsError;
import humandeque.manager.exceptions.ManipulationError;
import humandeque.manager.local.LocalManager;
import models.Car;
import models.Coordinates;
import models.Human;
import models.Mood;

public class CollectionCommandTest {
    @BeforeClass
    public static void setUp() throws Exception {
        TextsManager.updateTexts();
    }

    private CollectionManager collectionManager;

    private Human human = Human.builder()
            .name("TestName")
            .coordinates(new Coordinates(1.0f, 2.0f))
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

    class IdPipeline implements RequirementsPipeline {
        private Long id;

        public IdPipeline(Long id) {
            this.id = id;
        }

        @Override
        public <I, O> O askRequirement(Requirement<I, O> requirement) throws RequirementAskError {
            if (requirement.getName().equals("id")) {
                try {
                    return requirement.getValue((I) id.toString());
                } catch (ValidationError e) {
                    throw new RequirementAskError(requirement.getName());
                }
            } else {
                throw new RequirementAskError(requirement.getName());
            }
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
            params.put("Are you sure you want to clear the collection?", "true");
        }

        @Override
        public <I, O> O askRequirement(Requirement<I, O> requirement) throws RequirementAskError {
            try {
                return requirement.getValue((I) params.get(requirement.getName()));
            } catch (ValidationError e) {
                throw new RequirementAskError(requirement.getName());
            }
        }
    }

    class TestOutput implements OutputChannel {
        private String output = "";

        @Override
        public void putString(String string) {
            this.output += string + System.lineSeparator();
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
        try {
            command.execute(new TestPipeline(), output);
        } catch (ExecutionError e) {
            Assert.fail("Error handled");
        }
        assertTrue(output.getOutput(), output.getOutput().startsWith("Collection information:"));
    }

    @Test
    public void testShowCommand() {
        TestOutput output = new TestOutput();
        Command command = new ShowCommand(collectionManager);
        try {
            command.execute(new TestPipeline(), output);
        } catch (ExecutionError e) {
            Assert.fail("Error handled");
        }
        assertTrue(output.getOutput().startsWith("Collection is empty."));

        output.clear();
        try {
            collectionManager.add(human);
            try {
                command.execute(new TestPipeline(), output);
            } catch (ExecutionError e) {
                Assert.fail("Error handled");
            }
            assertTrue(output.getOutput(),
                    output.getOutput().startsWith("Collection elements:"));
        } catch (ElementAlreadyExistsError | ManipulationError e) {
            Assert.fail();
        }
    }

    @Test
    public void testAddElementCommand() {
        TestOutput output = new TestOutput();
        Command command = new AddElementCommand(collectionManager);
        try {
            command.execute(new TestPipeline(), output);
        } catch (ExecutionError e) {
            Assert.fail("Error handled");
        }
        assertTrue(output.getOutput(),
                output.getOutput().contains("Element added to collection."));

        Human human = collectionManager.getCollection().getFirst();
        assertEquals(human.getName(), "TestName");
        assertEquals(human.getCoordinates().getX(), 1.0f, 0.0001f);
        assertEquals(human.getCoordinates().getY(), 2.0f, 0.0001f);
        assertEquals(human.getRealHero(), true);
        assertEquals(human.getHasToothpick(), false);
        assertEquals(human.getImpactSpeed(), 3.0, 0.0001);
        assertEquals(human.getSoundtrackName(), "testSoundtrackName");
        assertEquals(human.getMinutesOfWaiting(), 4.0f, 0.0001f);
        assertEquals(human.getMood(), Mood.SADNESS);
        assertEquals(human.getCar().getName(), "testCarName");
        assertTrue(human.getCreationDate() != null);
    }

    @Test
    public void testUpdateElementCommand() {
        TestOutput output = new TestOutput();
        Command command = new UpdateElementCommand(collectionManager);
        List<Requirement<?, ?>> requirements = command.getStaticRequirements();
        assertEquals(requirements.size(), 1);
        assertEquals(requirements.get(0).getName(), "id");
    }

    @Test
    public void testRemoveByIdCommand() {
        TestOutput output = new TestOutput();
        Command command = new RemoveByIdCommand(collectionManager);
        List<Requirement<?, ?>> requirements = command.getStaticRequirements();
        assertEquals(requirements.size(), 1);
        assertEquals(requirements.get(0).getName(), "id");

        try {
            collectionManager.add(human);
        } catch (Exception e) {
            Assert.fail();
        }
        try {
            command.execute(new IdPipeline(human.getId()), output);
        } catch (ExecutionError e) {
            Assert.fail("Error handled");
        }
        assertEquals(collectionManager.getCollection().size(), 0);
    }

    @Test
    public void testClearCommand() {
        TestOutput output = new TestOutput();
        Command command = new ClearCommand(collectionManager);
        try {
            collectionManager.add(human);
        } catch (Exception e) {
            Assert.fail();
        }
        try {
            command.execute(new TestPipeline(), output);
        } catch (ExecutionError e) {
            Assert.fail("Error handled");
        }
        assertEquals(collectionManager.getCollection().size(), 0);
    }
}
