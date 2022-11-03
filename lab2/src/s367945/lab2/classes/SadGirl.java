package s367945.lab2.classes;

import java.util.Set;

import s367945.lab2.abc.Creature;
import s367945.lab2.abc.Human;
import s367945.lab2.enums.Gender;
import s367945.lab2.enums.Property;
import s367945.lab2.interfaces.Breathable;
import s367945.lab2.interfaces.Positioned;

public class SadGirl extends Human {
    protected Set<String> reasonsToDie = Set.of();

    public SadGirl(Body body, int health, int age, String name, Gender gender, Property[] personality) {
        super(body, health, age, name, gender, personality);
    }

    public void cry() {
        this.addPersonality(Property.EXHAUSTED);
    }

    @Override
    public void onHug(Creature actor) {
        this.see(actor);
        if (!this.reasonsToDie.isEmpty()) {
            String reason = this.reasonsToDie.iterator().next();
            this.reasonsToDie.remove(reason);
        }
    }

    @Override
    public String say() {
        if (this.reasonsToDie.isEmpty()) {
            return "I'm sad without any reason";
        }
        return "Don`t disturb me, I`m sad due to" + this.reasonsToDie.iterator().next();
    }   

    @Override
    public void listen(String speech) {
        this.reasonsToDie.add(speech);
    }

    @Override
    public boolean trust(Object subject) {
        return subject instanceof Rain;
    }

    @Override
    public void hurt(int damage) {
        this.health -= damage * 2;
    }

    @Override
    public void heal(int amount) {
        this.health += amount;
    }

    @Override
    public void breath(Breathable source) {
        if (source instanceof Oxygen || source instanceof Fog) {
            source.onBreath(this);
        }
    }

    @Override
    public Sight see(Positioned target) {
        Sight sight = new Sight(target, Property.SAD);
        return sight;
    }
}
