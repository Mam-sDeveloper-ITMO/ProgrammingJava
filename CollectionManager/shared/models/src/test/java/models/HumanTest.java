package models;

import org.junit.Test;

public class HumanTest {
    // test creation of human with all fields
    @Test
    public void testHumanCreation() {
        Human human = new Human();
        human.setId(1);
        human.setName("name");
        // human.setCoordinates(new Coordinates());
        human.setCreationDate(java.time.LocalDateTime.now());
        human.setRealHero(true);
        human.setHasToothpick(true);
        human.setImpactSpeed(1.0);
        human.setSoundtrackName("soundtrackName");
        human.setMinutesOfWaiting(1.0f);
        // human.setMood(new Mood());
        // human.setCar(new Car());
    }
}