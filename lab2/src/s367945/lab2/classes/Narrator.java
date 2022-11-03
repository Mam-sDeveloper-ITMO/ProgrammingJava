package s367945.lab2.classes;

import java.util.Random;
import java.util.HashSet;
import java.util.Iterator;

import s367945.lab2.abc.Creature;
import s367945.lab2.abc.Human;
import s367945.lab2.enums.Gender;
import s367945.lab2.enums.Property;
import s367945.lab2.interfaces.Breathable;
import s367945.lab2.interfaces.Positioned;
import s367945.lab2.structures.Coordinates;

public class Narrator extends Human {
    protected HashSet<String> stories = new HashSet<String>();

    public Narrator(Body body, int health, int age, String name, Gender gender, Property... personality) {
        super(body, health, age, name, gender, personality);
    }

    public void walk() {
        Random random = new Random();
        int maxDist = 10;
        Coordinates offset = new Coordinates(
                random.nextInt(-maxDist, maxDist),
                random.nextInt(-maxDist, maxDist),
                0);
        this.body.offset(offset);
        for (String story : this.stories) {
            this.listen(story);
        }
    }

    @Override
    public void onHug(Creature actor) {
        this.addPersonality(Property.HAPPY);
    }

    @Override
    public String say() {
        if (this.stories.isEmpty()) {
            return "I have no stories to tell";
        } else {
            Random random = new Random();
            int indx = random.nextInt(0, this.stories.size());
            for (String story : this.stories) {
                if (indx == 0) {
                    return story;
                }
                indx--;
            }
            return "";
        }
    }

    @Override
    public void listen(String speech) {
        this.stories.add(speech);
    }

    @Override
    public boolean trust(Object subject) {
        if (subject instanceof Human) {
            return true;
        } else if (this.stories.contains(subject)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void hurt(int damage) {
        this.health -= damage;
    }

    @Override
    public void heal(int amount) {
        this.health += amount;
    }

    @Override
    public void breath(Breathable source) {
        if (source instanceof Oxygen) {
            source.onBreath(this);
        }
    }

    @Override
    public Sight see(Positioned target) {
        Sight sight = new Sight(target, Property.ACCURATE, Property.INTELLIGENT);
        return sight;
    }
}
