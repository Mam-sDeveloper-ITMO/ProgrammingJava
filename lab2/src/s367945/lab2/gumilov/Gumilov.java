package s367945.lab2.gumilov;

import java.util.HashSet;
import java.util.Random;

import s367945.lab2.animal.human.Human;
import s367945.lab2.animal.human.interfaces.Narrator;
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

public class Gumilov extends Human implements Narrator {
    private HashSet<String> stories;

    public Gumilov() {
        super(21, 12, "Nicolai Gumilov", Gender.MAN, Personality.DREAMY, Personality.ACTIVE);
        this.stories = new HashSet<>();
    }

    @Override
    protected Body buildBody() {
        Body body = new Body(
                new Point(), new Pattern(1, 2),
                new Head(2, Appearance.COMMON), new Neck(2, Appearance.COMMON),
                new Leg(2, Appearance.COMMON), new Leg(2, Appearance.COMMON),
                new Hand(2, Appearance.WELL_BUILT), new Hand(2, Appearance.WELL_BUILT),
                new Torso(2, Appearance.WELL_BUILT));

        return body;
    }

    @Override
    public Sight see(Positioned target) {
        Sight sight = new Sight(target, "Attentive sight on " + target);
        return sight;
    }

    @Override
    public String say() {
        Random random = new Random();
        if (random.nextBoolean()) {
            if (this.knowledge.size() == 0) {
                return "Can you tell me something?";
            }

            int index = random.nextInt(this.knowledge.size());
            Object info = this.knowledge.toArray()[index];
            return "I know one interesting thing: " + info;
        }
        return this.tellStory();
    }

    @Override
    public void listen(String info) {
        this.knowledge.add(info);
        this.learnStory(info);
    }

    @Override
    public void learnStory(String story) {
        this.stories.add(story);
    }

    @Override
    public String tellStory() {
        if (this.stories.size() == 0) {
            return "I don't have any stories";
        }

        Random random = new Random();
        int index = random.nextInt(this.stories.size());
        Object info = this.stories.toArray()[index];
        return "I know one interesting thing: " + info;
    }

    @Override
    public boolean trust(Object obj) {
        return this.knowledge.contains(obj) || this.stories.contains(obj);
    }

    @Override
    public void hug(Object obj) {
        this.personality.add(Personality.HAPPY);
        this.tellStory();
    }

    @Override
    public HashSet<Personality> getPersonality() {
        return this.personality;
    }
}
