package humandeque;

import java.io.File;

import org.junit.Test;

import humandeque.manager.local.CsvStorage;
import models.Car;
import models.Coordinates;
import models.Human;
import models.Mood;

public class CsvStorageTest {
    File resourcesDirectory = new File("src/test/resources/humandeque");

    @Test
    public void testSave() {
        HumanDeque collection = new HumanDeque();
        Human human = Human.builder()
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

        Human human2 = Human.builder()
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

        collection.add(human);
        collection.add(human2);

        CsvStorage storage = new CsvStorage(resourcesDirectory.getPath() + "/testSave.csv");
        try {
            storage.save(collection);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    @Test
    public void testLoad() {
        CsvStorage storage = new CsvStorage(resourcesDirectory.getPath() + "/testLoad.csv");
        try {
            HumanDeque collection = storage.load();
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }
}
