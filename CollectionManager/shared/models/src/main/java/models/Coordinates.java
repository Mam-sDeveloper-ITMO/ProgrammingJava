package models;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * The coordinates of a human.
 * 
 * @see <a href="https://se.ifmo.ru/courses/programming">Programming course
 *      website</a>
 */
@Data
@Builder
public class Coordinates {

    /**
     * The x-coordinate of the human.
     */
    @NonNull
    private Float x;

    /**
     * The y-coordinate of the human.
     */
    @NonNull
    private Float y;

    /**
     * Constructs a new instance of Coordinates.
     * 
     * @param x The x-coordinate of the human.
     * @param y The y-coordinate of the human.
     * @throws IllegalArgumentException If x is less than or equal to -566, or y is
     *                                  less than or equal to -872.
     */
    public Coordinates(Float x, Float y) {
        if (x <= -566) {
            throw new IllegalArgumentException("x cannot be less than or equal to -566");
        }
        if (y <= -872) {
            throw new IllegalArgumentException("y cannot be less than or equal to -872");
        }
        this.x = x;
        this.y = y;
    }
}