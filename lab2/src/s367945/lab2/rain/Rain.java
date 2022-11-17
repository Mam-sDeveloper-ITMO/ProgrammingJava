package s367945.lab2.rain;

import s367945.lab2.animal.human.Human;
import s367945.lab2.enums.BreathSource;
import s367945.lab2.interfaces.Positioned;
import s367945.lab2.interfaces.Walkable;

public class Rain implements Walkable {
    @Override
    public void walk(Positioned target) {
        if (target instanceof Human) {
            Human human = (Human) target;
            human.breath(BreathSource.FOG);
        }
    }

    @Override
    public String toString() {
        return "Rain";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Rain;
    }

    @Override
    public int hashCode() {
        return 42;
    }
}
