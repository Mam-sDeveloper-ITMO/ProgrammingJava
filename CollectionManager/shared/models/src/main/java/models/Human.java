package models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

/**
 * A representation of a human.
 *
 * @see <a href="https://se.ifmo.ru/courses/programming">Programming course
 *      website</a>
 */
@Builder
@EqualsAndHashCode(of = { "id" })
public class Human implements Comparable<Human>, Serializable {

    /**
     * The id of the human.
     */
    @Getter
    @Builder.Default
    private long id = (new Random()).nextLong(111111, 999999);

    /**
     * The name of the human.
     */
    @Getter
    @NonNull
    private String name;

    /**
     * The coordinates of the human.
     */
    @Getter
    @NonNull
    private Coordinates coordinates;

    /**
     * The creation date of the human.
     */
    @Getter
    @Builder.Default
    @NonNull
    private LocalDateTime creationDate = LocalDateTime.now();

    /**
     * A flag indicating if the human is a real hero.
     */
    @Getter
    @NonNull
    private Boolean realHero;

    /**
     * A flag indicating if the human has a toothpick.
     */
    @Getter
    @NonNull
    private Boolean hasToothpick;

    /**
     * The impact speed of the human.
     */
    @Getter
    @NonNull
    private Double impactSpeed;

    /**
     * The soundtrack name of the human.
     */
    @Getter
    @NonNull
    private String soundtrackName;

    /**
     * The minutes of waiting of the human.
     */
    @Getter
    private Float minutesOfWaiting;

    /**
     * The mood of the human.
     */
    @Getter
    private Mood mood;

    /**
     * The car of the human.
     */
    @Getter
    @NonNull
    private Car car;

    /**
     * Constructs a new Human object with the specified characteristics.
     *
     * Used by {@link Human.Builder} to create a new instance of Human.
     *
     * @param id               the unique identifier of the human
     * @param name             the name of the human
     * @param coordinates      the coordinates of the human
     * @param creationDate     the date and time of the human's creation
     * @param realHero         determines whether the human is a real hero
     * @param hasToothpick     determines whether the human has a toothpick
     * @param impactSpeed      the speed of the human's impact
     * @param soundtrackName   the name of the human's soundtrack
     * @param minutesOfWaiting the number of minutes the human has been waiting
     * @param mood             the mood of the human
     * @param car              the car of the human
     * @throws IllegalArgumentException if {@code id} is less than or equal to 0, or
     *                                  if
     *                                  {@code name} is {@code null} or empty
     */
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

    /**
     * Compares this Human object to another based on impact speed.
     *
     * @param o the other Human object to compare to
     * @return 1 if this Human's impact speed is greater than the other, -1 if it's
     *         smaller, or 0 if they're equal
     */
    @Override
    public int compareTo(Human o) {
        return this.impactSpeed - o.impactSpeed > 0 ? 1 : -1;
    }

    /**
     * Returns a string representation of this Human object.
     *
     * @return a string representation of this Human object
     */
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
