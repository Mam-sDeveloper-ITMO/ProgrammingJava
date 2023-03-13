package humandeque;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import humandeque.manager.local.LocalManager;
import models.Car;
import models.Coordinates;
import models.Human;
import models.Mood;

public class LocalManagerTest {
    File resourcesDirectory = new File("src/test/resources/humandeque");
    LocalManager manager;
    Human testHuman = Human.builder()
            .name("test")
            .coordinates(new Coordinates(1.f, 1.f))
            .realHero(true)
            .hasToothpick(true)
            .impactSpeed(1)
            .minutesOfWaiting(1.f)
            .mood(Mood.FRENZY)
            .car(new Car("testCar"))
            .build();

    @Before
    @Test
    public void setUp() throws Exception {
        resourcesDirectory.mkdir();
        manager = new LocalManager(resourcesDirectory.getPath() + "/testLoad.csv");
        try {
            manager.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertNotNull(manager.getCollection());
    }

    @Test
    public void testAdd() {
        manager.add(testHuman);
        assertNotEquals(manager.getCollection().size(), 0);
    }

    @Test
    public void testRemove() {
        manager.add(testHuman);
        manager.remove(testHuman.getId());
        assertNotEquals(manager.getCollection().size(), 1);
    }

    @Test
    public void testUpdate() {
        manager.add(testHuman);
        Human updatedHuman = Human.builder()
                .id(1)
                .name("test")
                .coordinates(new Coordinates(1.f, 1.f))
                .realHero(true)
                .hasToothpick(true)
                .impactSpeed(1)
                .minutesOfWaiting(1.f)
                .mood(Mood.FRENZY)
                .car(new Car("testCar"))
                .build();
        manager.update(updatedHuman);
    }

    @Test
    public void testClear() {
        manager.add(testHuman);
        manager.clear();
        assertNotEquals(manager.getCollection().size(), 1);
    }

    @Test
    public void testSave() {
        manager.add(testHuman);
        try {
            manager.save();
        } catch (Exception e) {
            // TODO: handle exception
        }
        assertNotEquals(manager.getCollection().size(), 0);
    }
}
