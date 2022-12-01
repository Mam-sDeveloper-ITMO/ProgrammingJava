package s367945.lab2.nekowoman;

import s367945.lab2.animal.human.Human;

import java.util.HashSet;
import java.util.Random;

import s367945.lab2.animal.human.Human;
import s367945.lab2.animal.human.interfaces.SadGirl;
import s367945.lab2.body.Body;
import s367945.lab2.bodypart.bodyparts.Hand;
import s367945.lab2.bodypart.bodyparts.Head;
import s367945.lab2.bodypart.bodyparts.Leg;
import s367945.lab2.bodypart.bodyparts.Neck;
import s367945.lab2.bodypart.bodyparts.Tail;
import s367945.lab2.bodypart.bodyparts.Torso;
import s367945.lab2.enums.Appearance;
import s367945.lab2.enums.BreathSource;
import s367945.lab2.enums.Gender;
import s367945.lab2.enums.Personality;
import s367945.lab2.interfaces.Positioned;
import s367945.lab2.structures.pattern.Pattern;
import s367945.lab2.structures.point.Point;
import s367945.lab2.structures.sight.Sight;

public class NekoWoman extends Human {
    protected static class NekoWomanBody extends Body {
        public NekoWomanBody() {
            super(new Point(), new Pattern(1, 3),
                    new Head(1, Appearance.BEAUTIFUL), new Neck(1, Appearance.BEAUTIFUL),
                    new Leg(1, Appearance.BEAUTIFUL), new Leg(1, Appearance.BEAUTIFUL),
                    new Hand(1, Appearance.THIN), new Hand(1, Appearance.THIN),
                    new Torso(1, Appearance.BEAUTIFUL), new Tail(3, Appearance.BEAUTIFUL));
        }
    }

    public NekoWoman(int age, int health, String name, Personality... personalities) {
        super(age, health, name, Gender.MUTOGENDER, personalities);
    }

    public String moan() {
        return "Meeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeew";
    }

    public String sigh() {
        this.breath(BreathSource.OXYGEN);
        return "ah";
    }

    @Override
    public Sight see(Positioned target) {
        Sight sight = new Sight(target, "Cat sight on " + target);
        return sight;
    }

    @Override
    public String say() {
        if (this.knowledge.size() == 0) {
            return "Nya";
        }

        Random random = new Random();
        int index = random.nextInt(this.knowledge.size());
        Object phrase = this.knowledge.toArray()[index];
        return phrase + ", nya :3";
    }

    @Override
    public void listen(String info) {
        this.knowledge.add(info);
    }

    @Override
    public boolean trust(Object obj) {
        return this.knowledge.contains(obj);
    }

    @Override
    public void hug(Object obj) {
        if (obj instanceof Human) {
            Human human = (Human) obj;
            human.hug(this);
        }
    }

    @Override
    public HashSet<Personality> getPersonality() {
        return this.personality;
    }

    @Override
    protected Body buildBody() {
        return new NekoWomanBody();
    }
}
