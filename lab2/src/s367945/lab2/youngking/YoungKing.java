package s367945.lab2.youngking;

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

public class YoungKing extends Human {
    protected static class YoungKingBody extends Body {
        public YoungKingBody() {
            super(new Point(), new Pattern(2, 6),
                    new Head(3, Appearance.STRONG), new Neck(3, Appearance.STRONG),
                    new Leg(3, Appearance.STRONG), new Leg(3, Appearance.STRONG),
                    new Hand(3, Appearance.STRONG), new Hand(3, Appearance.STRONG),
                    new Torso(3, Appearance.STRONG));
        }
    }

    public YoungKing() {
        super(18, 6, "King of Mysterious Grounds Umputun III",
                Gender.MAN, Personality.PASSIONATE, Personality.NARCISSISTIC);
    }

    @Override
    protected Body buildBody() {
        return new YoungKingBody();
    }

    @Override
    public Sight see(Positioned target) {
        Sight sight = new Sight(target, "Sad sight on " + target);
        return sight;
    }

    @Override
    public String say() {
        if (this.knowledge.size() == 0) {
            return "I have nothing to say, slave";
        }

        Random random = new Random();
        int index = random.nextInt(this.knowledge.size());
        Object command = this.knowledge.toArray()[index];
        return "Command you to " + command;
    }

    @Override
    public void listen(String info) {
        this.knowledge.add(info);
    }

    @Override
    public boolean trust(Object obj) {
        return obj instanceof BlackMaiden;
    }

    @Override
    public void hug(Object obj) {
        this.say();
    }

    @Override
    public HashSet<Personality> getPersonality() {
        HashSet<Personality> personalities = new HashSet<>();
        personalities.add(Personality.GREAT);
        return personalities;
    }
}
