package s367945.lab2.classes;

import java.util.Random;

import s367945.lab2.abc.Animal;
import s367945.lab2.abc.Creature;
import s367945.lab2.enums.Property;
import s367945.lab2.interfaces.Breathable;
import s367945.lab2.interfaces.Positioned;
import s367945.lab2.structures.Coordinates;

public class Giraffe extends Animal {
    public Giraffe(Body body, int health, int age, Pattern skin) {
        super(body, health, age, skin);
    }

    public void walk() {
        Random random = new Random();
        int maxDist = 100;
        Coordinates offset = new Coordinates(
                random.nextInt(-maxDist, maxDist),
                random.nextInt(-maxDist, maxDist),
                0
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
        this.health += amount * 2;
    }

    @Override
    public void breath(Breathable source) {
        if (source instanceof Oxygen) {
            this.health += 1;
            source.onBreath(this);
        }
    }

    @Override
    public Sight see(Positioned target) {
        Sight sight = new Sight(target, Property.CLEAN);
        return sight;
    }    
}
