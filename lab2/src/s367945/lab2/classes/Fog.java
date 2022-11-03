package s367945.lab2.classes;

import s367945.lab2.abc.Creature;
import s367945.lab2.abc.Human;
import s367945.lab2.interfaces.Breathable;
import s367945.lab2.enums.Property;

public class Fog implements Breathable {
    @Override
    public void onBreath(Creature actor) {
        if (actor instanceof Human) {
            Human human = (Human) actor;
            human.addPersonality(Property.SAD);
        }
    }

    @Override
    public String toString() {
        return "Heavy fog that makes you sad";
    }
}
