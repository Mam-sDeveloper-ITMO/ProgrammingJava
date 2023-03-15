package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

/**
 * Car of the human.
 * 
 * @see <a href=
 *      "https://se.ifmo.ru/courses/programming">https://se.ifmo.ru/courses/programming</a>
 */
@Data
@AllArgsConstructor
public class Car {
    @NonNull
    private String name;
}
