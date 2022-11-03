package s367945.lab2.classes;

import s367945.lab2.abc.Creature;
import s367945.lab2.interfaces.Breathable;

public class Oxygen implements Breathable {
    @Override
    public void onBreath(Creature actor) {
        actor.heal(10);
    }

    @Override
    public String toString() {
        return "Just oxygen";
    }
}
    
