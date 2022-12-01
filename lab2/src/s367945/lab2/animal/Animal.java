package s367945.lab2.animal;

import java.util.HashSet;
import java.util.Objects;

import s367945.lab2.enums.BreathSource;
import s367945.lab2.interfaces.Positioned;
import s367945.lab2.interfaces.Seeable;
import s367945.lab2.structures.pattern.Pattern;
import s367945.lab2.structures.point.Point;
import s367945.lab2.enums.Appearance;
import s367945.lab2.body.Body;

public abstract class Animal implements Seeable, Positioned {
    protected Body body;
    protected int age;
    protected int health;

    private static class DeadInteraction extends RuntimeException {
        public DeadInteraction() {
            super("Stop interact with dead animal, please");
        }
    }

    public Animal(int age, int health) {
        this.age = age;
        this.health = health;
        this.body = this.buildBody();
    }

    abstract public void breath(BreathSource source);

    abstract protected Body buildBody();

    public void die() {
        this.health = 0;
    }

    public HashSet<Appearance> getAppearance() {
        return body.getProperties();
    };

    protected void moveBody(Point position) {
        if (this.health != 0) {
            this.body.setPosition(position);
        } else {
            throw new DeadInteraction();
        }
    }

    public Pattern getSkinPattern() {
        return this.body.getSkin();
    }

    public int getAge() {
        return this.age;
    };

    public int getHealth() {
        return health;
    }

    @Override
    public Point getPosition() {
        return this.body.getPosition();
    }

    @Override
    public String toString() {
        return String.format("Animal %d years old with appearance %s", this.age, this.getAppearance());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        Animal other = (Animal) obj;
        return this.age == other.age && this.health == other.health && this.body.equals(other.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.age, this.health, this.body);
    }
}
