package s367945.lab2.giraffe;

import s367945.lab2.animal.Animal;
import s367945.lab2.body.Body;
import s367945.lab2.bodypart.bodyparts.Head;
import s367945.lab2.bodypart.bodyparts.Leg;
import s367945.lab2.bodypart.bodyparts.Neck;
import s367945.lab2.bodypart.bodyparts.Tail;
import s367945.lab2.bodypart.bodyparts.Torso;
import s367945.lab2.enums.Appearance;
import s367945.lab2.enums.BreathSource;
import s367945.lab2.interfaces.Positioned;
import s367945.lab2.interfaces.Runnable;
import s367945.lab2.interfaces.Walkable;
import s367945.lab2.location.Location;
import s367945.lab2.shipsail.ShipSail;
import s367945.lab2.structures.pattern.Pattern;
import s367945.lab2.structures.point.Point;
import s367945.lab2.structures.sight.Sight;

public class Giraffe extends Animal implements Runnable, Walkable {
    public Giraffe(int age) {
        super(age, 10);
    }

    public void hide(Location target) {
        this.walk(target);
    }
    
    @Override
    protected Body buildBody() {
        Body body = new Body(
                new Point(), new Pattern(4, 4),
                new Head(6, Appearance.ELEGANT), new Neck(6, Appearance.ELEGANT),
                new Leg(6, Appearance.ELEGANT), new Leg(6, Appearance.ELEGANT),
                new Leg(6, Appearance.ELEGANT), new Leg(6, Appearance.ELEGANT),
                new Torso(6, Appearance.SLIM), new Tail(1, Appearance.ELEGANT));

        return body;
    }

    @Override
    public Sight see(Positioned target) {
        if (target.getPosition().z > 0) {
            return new Sight(target, "Can't see " + target.toString() + " because it is too high");
        } else {
            return new Sight(target, "Elegant sight on " + target.toString());
        }
    }

    @Override
    public void walk(Positioned target) {
        if (target.getPosition().z <= 1) {
            this.moveBody(target.getPosition());
        }
    }

    @Override
    public void run(Positioned target) {
        if (target.getPosition().z <= 1) {
            this.moveBody(target.getPosition());
        }
    }

    @Override
    public void breath(BreathSource source) {
        if (source == BreathSource.OXYGEN) {
            this.health += 1;
        } else {
            this.health -= 3;
        }
    }

    @Override
    public int getAge() {
        return this.age;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ShipSail) {
            return true;
        }
        return super.equals(obj);
    }
}
