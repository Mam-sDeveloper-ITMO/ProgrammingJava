package s367945.lab2.abc;

import s367945.lab2.classes.Body;
import s367945.lab2.classes.Sight;
import s367945.lab2.interfaces.Breathable;
import s367945.lab2.interfaces.Hugable;
import s367945.lab2.interfaces.Positioned;
import s367945.lab2.structures.Coordinates;

public abstract class Creature extends PropertiesContained implements Positioned, Hugable {
    protected final Body body;
    protected int health;
    protected int age;

    public Creature(Body body, int health, int age) {
        this.body = body;
        this.health = health;
        this.age = age;
    }

    abstract public void hurt(int damage);

    abstract public void heal(int amount);

    abstract public void breath(Breathable source);

    abstract public Sight see(Positioned target);

    @Override
    public Coordinates getPosition() {
        return this.body.getPosition();
    }
}
