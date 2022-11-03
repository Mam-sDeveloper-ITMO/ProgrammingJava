package s367945.lab2.classes;

import java.util.Random;

import s367945.lab2.abc.Animal;
import s367945.lab2.abc.Creature;
import s367945.lab2.enums.Property;
import s367945.lab2.interfaces.Breathable;
import s367945.lab2.interfaces.Positioned;
import s367945.lab2.structures.Coordinates;

public class Bird extends Animal {
    public Bird(Body body, int health, int age, Pattern skin) {
        super(body, health, age, skin);
    }

    public void fly() {
        Random random = new Random();
        int maxDist = 1000;
        Coordinates offset = new Coordinates(
                random.nextInt(-maxDist, maxDist),
                random.nextInt(-maxDist, maxDist),
                random.nextInt(-maxDist, maxDist)
        );
        this.body.offset(offset);
    }

    @Override
    public void onHug(Creature actor) {
        this.body.interact(actor);
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
        Sight sight = new Sight(target, Property.ACCURATE);
        return sight;
    }    
}
