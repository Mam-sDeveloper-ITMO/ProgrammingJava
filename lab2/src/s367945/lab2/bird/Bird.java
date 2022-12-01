package s367945.lab2.bird;

import s367945.lab2.animal.Animal;
import s367945.lab2.body.Body;
import s367945.lab2.bodypart.bodyparts.Head;
import s367945.lab2.bodypart.bodyparts.Leg;
import s367945.lab2.bodypart.bodyparts.Tail;
import s367945.lab2.bodypart.bodyparts.Torso;
import s367945.lab2.bodypart.bodyparts.Wing;
import s367945.lab2.enums.Appearance;
import s367945.lab2.enums.BreathSource;
import s367945.lab2.interfaces.Flyable;
import s367945.lab2.interfaces.Positioned;
import s367945.lab2.interfaces.Walkable;
import s367945.lab2.structures.pattern.Pattern;
import s367945.lab2.structures.point.Point;
import s367945.lab2.structures.sight.Sight;

public class Bird extends Animal implements Walkable, Flyable {
    protected static class BirdBody extends Body {
        public BirdBody() {
            super(new Point(), new Pattern(1, 2),
                    new Head(1, Appearance.ELEGANT),
                    new Leg(1, Appearance.ELEGANT), new Leg(1, Appearance.ELEGANT),
                    new Wing(1, Appearance.ELEGANT), new Wing(1, Appearance.ELEGANT),
                    new Torso(1, Appearance.SLIM), new Tail(1, Appearance.ELEGANT));
        }
    }

    public Bird(int age) {
        super(age, 1);
    }

    @Override
    protected Body buildBody() {
        return new BirdBody();
    }

    @Override
    public void fly(Positioned target) {
        this.moveBody(target.getPosition());
    }

    @Override
    public Sight see(Positioned target) {
        Sight sight = new Sight(target, "Sharped sight on " + target.toString());
        return sight;
    }

    @Override
    public void walk(Positioned target) {
        Point targetPosition = target.getPosition();
        int offset = Math.abs(targetPosition.x - this.getPosition().x) +
                Math.abs(targetPosition.y - this.getPosition().y);
        if (offset <= 10 && targetPosition.z <= 0) {
            this.moveBody(target.getPosition());
        }
    }

    @Override
    public void breath(BreathSource source) {
        if (source == BreathSource.OXYGEN) {
            this.health += 1;
        } else {
            this.health -= 1;
        }
    }
}
