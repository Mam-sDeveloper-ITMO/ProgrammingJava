package s367945.lab2.lion;

import javax.tools.DocumentationTool.Location;

import s367945.lab2.animal.Animal;
import s367945.lab2.body.Body;
import s367945.lab2.bodypart.bodyparts.Head;
import s367945.lab2.bodypart.bodyparts.Leg;
import s367945.lab2.bodypart.bodyparts.Neck;
import s367945.lab2.bodypart.bodyparts.Tail;
import s367945.lab2.bodypart.bodyparts.Torso;
import s367945.lab2.enums.Appearance;
import s367945.lab2.enums.BreathSource;
import s367945.lab2.interfaces.Killer;
import s367945.lab2.interfaces.Positioned;
import s367945.lab2.interfaces.Runnable;
import s367945.lab2.interfaces.Walkable;
import s367945.lab2.structures.pattern.Pattern;
import s367945.lab2.structures.point.Point;
import s367945.lab2.structures.sight.Sight;

public class Lion extends Animal implements Walkable, Runnable, Killer {
    protected static class LionBody extends Body {
        public LionBody() {
            super(
                    new Point(), new Pattern(4, 3),
                    new Head(4, Appearance.ELEGANT), new Neck(4, Appearance.ELEGANT),
                    new Leg(4, Appearance.ELEGANT), new Leg(4, Appearance.ELEGANT),
                    new Leg(4, Appearance.ELEGANT), new Leg(4, Appearance.ELEGANT),
                    new Torso(4, Appearance.SLIM), new Tail(4, Appearance.ELEGANT));
        }
    }

    public Lion(int age) {
        super(age, 20);
    }

    @Override
    public void kill(Animal victim) {
        if (victim.getHealth() < this.health) {
            victim.die();
        }
    }

    @Override
    protected Body buildBody() {
        return new LionBody();
    }

    @Override
    public Sight see(Positioned target) {
        return new Sight(target, "Elegant sight on " + target.toString());
    }

    @Override
    public void walk(Positioned target) throws IncorrectPosition {
        if (target.getPosition().z <= 3) {
            this.moveBody(target.getPosition());
        } else {
            throw new IncorrectPosition("Target is too high");
        }
    }

    @Override
    public void run(Positioned target) {
        if (target.getPosition().z <= 4) {
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
}
