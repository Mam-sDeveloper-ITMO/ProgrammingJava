package s367945.lab2.blackmaiden;

import java.util.HashSet;
import java.util.Random;

import s367945.lab2.animal.human.Human;
import s367945.lab2.blackmaiden.BlackMaiden;
import s367945.lab2.body.Body;
import s367945.lab2.bodypart.bodyparts.Hand;
import s367945.lab2.bodypart.bodyparts.Head;
import s367945.lab2.bodypart.bodyparts.Leg;
import s367945.lab2.bodypart.bodyparts.Neck;
import s367945.lab2.bodypart.bodyparts.Torso;
import s367945.lab2.enums.Appearance;
import s367945.lab2.enums.Gender;
import s367945.lab2.enums.Personality;
import s367945.lab2.interfaces.Positioned;
import s367945.lab2.structures.pattern.Pattern;
import s367945.lab2.structures.point.Point;
import s367945.lab2.structures.sight.Sight;
import s367945.lab2.youngking.YoungKing;

public class BlackMaiden extends Human {
    protected static class BlackMaidenBody extends Body {
        public BlackMaidenBody() {
            super(new Point(), new Pattern(2, 6),
                    new Head(3, Appearance.GORGEOUS), new Neck(3, Appearance.ATTRACTIVE),
                    new Leg(3, Appearance.ATTRACTIVE), new Leg(3, Appearance.ATTRACTIVE),
                    new Hand(3, Appearance.ATTRACTIVE), new Hand(3, Appearance.ATTRACTIVE),
                    new Torso(3, Appearance.PRETTY));
        }
    }

    public BlackMaiden() {
        super(23, 6, "Anonymous girl", Gender.WOMEN, Personality.TRICKY);
    }

    @Override
    protected Body buildBody() {
        return new BlackMaidenBody();
    }

    @Override
    public Sight see(Positioned target) {
        Sight sight = new Sight(target, target.toString());
        return sight;
    }

    @Override
    public String say() {
        if (this.knowledge.size() == 0) {
            return "Sorry, but I don`t wont to talk with you";
        }

        Random random = new Random();
        int index = random.nextInt(this.knowledge.size());
        Object info = this.knowledge.toArray()[index];
        return "Hey, I wanna wisp you " + info;
    }

    @Override
    public void listen(String info) {
        this.knowledge.add(info);
    }

    @Override
    public boolean trust(Object obj) {
        return obj instanceof YoungKing;
    }

    @Override
    public void hug(Object obj) {
        this.say();
    }

    @Override
    public HashSet<Personality> getPersonality() {
        return this.personality;
    }
}
