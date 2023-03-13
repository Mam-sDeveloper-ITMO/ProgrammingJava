package models;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Car of the human.
 * 
 * @see <a href=
 *      "https://se.ifmo.ru/courses/programming">https://se.ifmo.ru/courses/programming</a>
 */
@Data
@AllArgsConstructor
public class Car {
    @NotNull
    private String name;
}
