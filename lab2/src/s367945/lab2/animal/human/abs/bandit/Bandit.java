package s367945.lab2.animal.human.abs.bandit;

import s367945.lab2.animal.human.Human;
import s367945.lab2.enums.Gender;
import s367945.lab2.enums.Personality;
import s367945.lab2.interfaces.Killer;
import s367945.lab2.interfaces.Thief;

public abstract class Bandit extends Human implements Killer, Thief {
    public Bandit(int age, int health, String name, Gender gender, Personality... personalities) {
        super(age, health, name, gender, personalities);
    }   
}
