package s367945.lab2.animal.human.abs.dwarf;

import s367945.lab2.animal.human.Human;
import s367945.lab2.body.Body;
import s367945.lab2.bodypart.bodyparts.Hand;
import s367945.lab2.bodypart.bodyparts.Head;
import s367945.lab2.bodypart.bodyparts.Leg;
import s367945.lab2.bodypart.bodyparts.Neck;
import s367945.lab2.bodypart.bodyparts.Torso;
import s367945.lab2.enums.Appearance;
import s367945.lab2.enums.Gender;
import s367945.lab2.enums.Personality;
import s367945.lab2.structures.pattern.Pattern;
import s367945.lab2.structures.point.Point;

public abstract class Dwarf extends Human {
    protected static class DwarfBody extends Body {
        public DwarfBody() {
            super(new Point(), new Pattern(1, 1),
                    new Head(1, Appearance.LARGE), new Neck(1, Appearance.LARGE),
                    new Leg(1, Appearance.LARGE), new Leg(1, Appearance.LARGE),
                    new Hand(1, Appearance.LARGE), new Hand(1, Appearance.LARGE),
                    new Torso(1, Appearance.LARGE));
        }
    }

    public Dwarf(int age, int health, String name, Gender gender, Personality... personalities) {
        super(age, health, name, gender, personalities);
    }

    @Override
    protected Body buildBody() {
        return new DwarfBody();
    }
}
