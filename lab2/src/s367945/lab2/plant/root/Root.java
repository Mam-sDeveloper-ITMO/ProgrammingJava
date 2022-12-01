package s367945.lab2.plant.root;

import s367945.lab2.bodypart.bodyparts.Hand;
import s367945.lab2.enums.BreathSource;
import s367945.lab2.interfaces.Positioned;
import s367945.lab2.interfaces.Walkable;
import s367945.lab2.plant.Plant;

public class Root extends Plant implements Walkable {
    public Root(String name) {
        super(name);
    }

    @Override
    public BreathSource photosynthesize(BreathSource source) {
        return BreathSource.FOG;
    }

    @Override
    public void walk(Positioned target) throws IncorrectPosition {
        this.position = target.getPosition();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Hand) {
            return true;
        }
        return super.equals(obj);
    }
}
