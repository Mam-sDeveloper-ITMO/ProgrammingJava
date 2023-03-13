package models;

import java.time.LocalDateTime;
import java.util.Random;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Main entity of the collection.
 * 
 * Consists representation of human.
 * 
 * @see <a href=
 *      "https://se.ifmo.ru/courses/programming">https://se.ifmo.ru/courses/programming</a>
 * 
 */
@Data
@Builder
@RequiredArgsConstructor
public class Human {
    @Builder.Default
    private long id = (new Random()).nextLong(0, Long.MAX_VALUE);

    @NonNull
    private String name;

    @NonNull
    private Coordinates coordinates;

    @Builder.Default
    private LocalDateTime creationDate = LocalDateTime.now();

    private boolean realHero;

    private boolean hasToothpick;

    private double impactSpeed;

    @NonNull
    private String soundtrackName;

    private Float minutesOfWaiting;

    private Mood mood;

    @NonNull
    private Car car;

    private Human(long id, String name, Coordinates coordinates, LocalDateTime creationDate, boolean realHero,
            boolean hasToothpick, double impactSpeed, String soundtrackName, Float minutesOfWaiting, Mood mood,
            Car car) {

        if (id <= 0) {
            throw new IllegalArgumentException();
        }

        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException();
        }

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
}
