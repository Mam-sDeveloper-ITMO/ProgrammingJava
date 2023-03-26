package models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

/**
 * Main entity of the collection.
 * 
 * Consists representation of human.
 * 
 * @see <a href= "https://se.ifmo.ru/courses/programming">https://se.ifmo.ru/courses/programming</a>
 * 
 */
@Builder
@EqualsAndHashCode(of = {"id"})
public class Human implements Comparable<Human> {
    @Getter @Builder.Default private long id = (new Random()).nextLong(111111, 999999);

    @Getter @NonNull private String name;

    @Getter @NonNull private Coordinates coordinates;

    @Getter @Builder.Default @NonNull private LocalDateTime creationDate = LocalDateTime.now();

    @Getter @NonNull private Boolean realHero;

    @Getter @NonNull private Boolean hasToothpick;

    @Getter @NonNull private Double impactSpeed;

    @Getter @NonNull private String soundtrackName;

    @Getter private Float minutesOfWaiting;

    @Getter private Mood mood;

    @Getter @NonNull private Car car;

    private Human(long id, String name, Coordinates coordinates, LocalDateTime creationDate,
        boolean realHero, boolean hasToothpick, double impactSpeed, String soundtrackName,
        Float minutesOfWaiting, Mood mood, Car car) {

        if (id <= 0) {
            throw new IllegalArgumentException();
        }

        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException();
        }

        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.realHero = realHero;
        this.hasToothpick = hasToothpick;
        this.impactSpeed = impactSpeed;
        this.soundtrackName = soundtrackName;
        this.minutesOfWaiting = minutesOfWaiting;
        this.mood = mood;
        this.car = car;
    }

    @Override
    public int compareTo(Human o) {
        return this.impactSpeed - o.impactSpeed > 0 ? 1 : -1;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return "Human(\n"
            + "    id=" + id + ",\n"
            + "    name=" + name + ",\n"
            + "    creationDate=" + creationDate.format(formatter) + ",\n"
            + "    coordinates=" + coordinates + ",\n"
            + "    realHero=" + realHero + ",\n"
            + "    hasToothpick=" + hasToothpick + ",\n"
            + "    impactSpeed=" + impactSpeed + ",\n"
            + "    soundtrackName=" + soundtrackName + ",\n"
            + "    minutesOfWaiting=" + minutesOfWaiting + ",\n"
            + "    mood=" + mood + ",\n"
            + "    car=" + car + System.lineSeparator()
            + ")";
    }
}
