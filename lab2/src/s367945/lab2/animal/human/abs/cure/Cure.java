package s367945.lab2.animal.human.abs.cure;

import s367945.lab2.animal.human.Human;
import s367945.lab2.animal.human.interfaces.Monk;
import s367945.lab2.enums.Gender;
import s367945.lab2.enums.Personality;


public abstract class Cure extends Human implements Monk {
    public Cure(int age, int health, String name, Gender gender, Personality... personalities) {
        super(age, health, name, gender, personalities);
    }
}
