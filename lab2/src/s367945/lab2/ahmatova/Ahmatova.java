package s367945.lab2.ahmatova;

import java.util.HashSet;
import java.util.Random;

import s367945.lab2.animal.human.Human;
import s367945.lab2.animal.human.interfaces.SadGirl;
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
import s367945.lab2.rain.Rain;
import s367945.lab2.structures.pattern.Pattern;
import s367945.lab2.structures.point.Point;
import s367945.lab2.structures.sight.Sight;

public class Ahmatova extends Human implements SadGirl {
    public Ahmatova() {
        super(18, 6, "Anna Ahmatova", Gender.WOMEN, Personality.SAD);
    }

    protected Body buildBody() {
        Body body = new Body(
                new Point(), new Pattern(1, 3),
                new Head(1, Appearance.BEAUTIFUL), new Neck(1, Appearance.BEAUTIFUL),
                new Leg(1, Appearance.BEAUTIFUL), new Leg(1, Appearance.BEAUTIFUL),
                new Hand(1, Appearance.THIN), new Hand(1, Appearance.THIN),
                new Torso(1, Appearance.BEAUTIFUL));

        return body;
    }

    @Override
    public Sight see(Positioned target) {
        Sight sight = new Sight(target, "Sad sight on " + target);
        return sight;
    }

    @Override
    public void cry() {
        this.personality.add(Personality.CALM);
        this.personality.remove(Personality.SAD);
    }

    @Override
    public String say() {
        if (this.knowledge.size() == 0) {
            return "I just wanna die";
        }

        Random random = new Random();
        int index = random.nextInt(this.knowledge.size());
        Object reasonToDie = this.knowledge.toArray()[index];
        return "I have one reason to die " + reasonToDie;
    }

    @Override
    public void listen(String info) {
        this.knowledge.add(info);
    }

    @Override
    public boolean trust(Object obj) {
        return obj instanceof Rain;
    }

    @Override
    public void hug(Object obj) {
        this.personality.add(Personality.CHEERFUL);
        if (obj instanceof Human) {
            Human human = (Human) obj;
            human.listen("Thanks");
        }
    }

    @Override
    public HashSet<Personality> getPersonality() {
        HashSet<Personality> personalities = new HashSet<>();
        personalities.add(Personality.CALM);
        return personalities;
    }
}
