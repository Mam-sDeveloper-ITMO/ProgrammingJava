package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

/**
 * A representation of a car that belongs to a human.
 * 
 * @see <a href="https://se.ifmo.ru/courses/programming">Programming course
 *      website</a>
 */
@Data
@AllArgsConstructor
public class Car {

    /**
     * The name of the car.
     */
    @NonNull
    private String name;
}