package models;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * Coordinates of the human.
 * 
 * @see <a href= "https://se.ifmo.ru/courses/programming">https://se.ifmo.ru/courses/programming</a>
 */
@Data
@Builder
public class Coordinates {
    @NonNull private Float x;

    @NonNull private Float y;

    public Coordinates(Float x, Float y) {
        if (x <= -566) {
            throw new IllegalArgumentException();
        }
        if (y <= -872) {
            throw new IllegalArgumentException();
        }
        this.x = x;
        this.y = y;
    }
}
