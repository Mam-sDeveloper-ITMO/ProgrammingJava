package s367945.lab2.abc;

import java.util.HashSet;
import java.util.Arrays;

import s367945.lab2.classes.Body;
import s367945.lab2.enums.Gender;
import s367945.lab2.enums.Property;
import s367945.lab2.interfaces.Hugable;

public abstract class Human extends Creature {
    protected String name;
    protected Gender gender;
    protected HashSet<Property> personality;

    public Human(Body body, int health, int age, String name, Gender gender, Property... personality) {
        super(body, health, age);

        this.name = name;
        this.gender = gender;
        this.personality = new HashSet<Property>(Arrays.asList(personality));
    }

    public HashSet<Property> getAppearance() {
        return this.body.getProperties();
    }

    public HashSet<Property> getPersonality() {
        return this.personality;
    }

    public void addPersonality(Property personality) {
        this.personality.add(personality);
    }
    
    public void hug(Hugable other) {
        other.onHug(this);
    }

    @Override
    public HashSet<Property> getProperties() {
        HashSet<Property> properties = new HashSet<Property>();
        properties.addAll(this.getPersonality());
        properties.addAll(this.getAppearance());
        return properties;
    }

    abstract public String say();

    abstract public void listen(String speech);

    abstract public boolean trust(Object subject);

    @Override
    public String toString() {
        String description = String.format("%s with personality %s", this.name, this.personality);
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
        if (!(obj instanceof Human))
            return false;

        Human other = (Human) obj;
        return this.name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return super.hashCode() + this.name.hashCode();
    }
}
