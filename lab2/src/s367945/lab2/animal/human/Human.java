package s367945.lab2.animal.human;

import java.util.HashSet;
import java.util.Objects;

import s367945.lab2.enums.BreathSource;
import s367945.lab2.enums.Gender;
import s367945.lab2.enums.Personality;
import s367945.lab2.interfaces.Positioned;
import s367945.lab2.interfaces.Walkable;
import s367945.lab2.animal.Animal;

abstract public class Human extends Animal implements Walkable {
    protected String name;
    protected Gender gender;
    protected HashSet<Personality> personality;
    protected HashSet<String> knowledge;

    public Human(int age, int health, String name, Gender gender, Personality... personalities) {
        super(age, health);
        this.name = name;
        this.gender = gender;

        // Need to add each one personality because
        // Set.of and Set.addAll can throw exception for duplicates
        this.personality = new HashSet<Personality>();
        for (Personality personality : personalities) {
            this.personality.add(personality);
        }

        this.knowledge = new HashSet<String>();
    }

    abstract public String say();

    abstract public void listen(String info);

    abstract public boolean trust(Object obj);

    abstract public void hug(Object obj);

    abstract public HashSet<Personality> getPersonality();

    public void walk(Positioned target) {
        this.moveBody(target.getPosition());
    }

    public void breath(BreathSource source) {
        if (source == BreathSource.OXYGEN) {
            this.health += 2;
        } else {
            this.health -= 3;
        }
    }

    @Override
    public String toString() {
        return String.format("Human %s, %d years old", this.name, this.age);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Human)) {
            return false;
        }

        Human human = (Human) obj;

        return this.name.equals(human.name) && super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, super.hashCode());
    }
}
