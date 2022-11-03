package s367945.lab2.abc;

import s367945.lab2.classes.Body;
import s367945.lab2.classes.Sight;
import s367945.lab2.interfaces.Breathable;
import s367945.lab2.interfaces.Hugable;
import s367945.lab2.interfaces.Positioned;
import s367945.lab2.structures.Coordinates;

public abstract class Creature extends PropertiesContainer implements Positioned, Hugable {
    protected final Body body;
    protected int health;
    protected int age;

    public Creature(Body body, int health, int age) {
        this.body = body;
        this.body.setOwner(this);
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

    @Override
    public String toString() {
        String description = String.format("Creature with body %s, health %d and age %d", this.body, this.health,
                this.age);
        return description;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!super.equals(obj))
            return false;
        if (!(obj instanceof Creature))
            return false;

        Creature other = (Creature) obj;
        return this.body.equals(other.body) && this.age == other.age;
    }

    @Override
    public int hashCode() {
        return super.hashCode() + this.body.hashCode() + this.age;
    }
}
