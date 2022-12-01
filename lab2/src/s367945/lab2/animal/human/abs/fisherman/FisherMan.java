package s367945.lab2.animal.human.abs.fisherman;

import s367945.lab2.animal.human.Human;
import s367945.lab2.enums.Gender;
import s367945.lab2.enums.Personality;
import s367945.lab2.interfaces.Fisher;


public abstract class FisherMan extends Human implements Fisher {
    public FisherMan(int age, int health, String name, Gender gender, Personality... personalities) {
        super(age, health, name, gender, personalities);
    }
}
