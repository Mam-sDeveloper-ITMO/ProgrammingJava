package s367945.lab2.fish;

import s367945.lab2.animal.Animal;
import s367945.lab2.body.Body;
import s367945.lab2.bodypart.bodyparts.Head;
import s367945.lab2.bodypart.bodyparts.Tail;
import s367945.lab2.bodypart.bodyparts.Torso;
import s367945.lab2.enums.Appearance;
import s367945.lab2.enums.BreathSource;
import s367945.lab2.interfaces.Positioned;
import s367945.lab2.interfaces.Swimable;
import s367945.lab2.location.lake.Lake;
import s367945.lab2.structures.pattern.Pattern;
import s367945.lab2.structures.point.Point;
import s367945.lab2.structures.sight.Sight;

public class Fish extends Animal implements Swimable {
    protected static class FishBody extends Body {
        public FishBody() {
            super(new Point(), new Pattern(1, 2),
                    new Head(1, Appearance.SHAPELESS),
                    new Torso(1, Appearance.SHAPELESS), new Tail(1, Appearance.SHAPELESS));
        }
    }

    public Fish(int age) {
        super(age, 3);
    }

    @Override
    public Sight see(Positioned target) {
        Sight sight = new Sight(target, "Stupid sight on " + target.toString());
        return sight;
    }

    @Override
    public void swim(Positioned target) throws IncorrectPosition {
        if (target instanceof Lake) {
            this.moveBody(getPosition());
        } else {
            throw new IncorrectPosition("Fish can't swim in " + target.toString());
        }
    }

    @Override
    public void breath(BreathSource source) {
    }

    @Override
    protected Body buildBody() {
        // TODO Auto-generated method stub
        return null;
    }

}
