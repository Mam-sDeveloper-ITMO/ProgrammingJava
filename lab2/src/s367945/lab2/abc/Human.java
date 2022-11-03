package s367945.lab2.abc;

import java.util.Set;

import s367945.lab2.classes.Body;
import s367945.lab2.enums.Gender;
import s367945.lab2.enums.Property;

public abstract class Human extends Creature {
    protected String name;
    protected Gender gender;
    protected Set<Property> personality;

    public Human(Body body, int health, int age, String name, Gender gender, Property... personality) {
        super(body, health, age);

        this.name = name;
        this.gender = gender;
        this.personality = Set.of(personality);
    }

    public Set<Property> getAppearance() {
        return this.body.getProperties();
    }

    public Set<Property> getPersonality() {
        return this.personality;
    }

    public void addPersonality(Property personality) {
        this.personality.add(personality);
    }

    @Override
    public Set<Property> getProperties() {
        Set<Property> properties = Set.of();
        properties.addAll(this.getPersonality());
        properties.addAll(this.getAppearance());
        return properties;
    }

    abstract public String say();

    abstract public void listen(String speech);

    abstract public boolean trust(Object subject);
}
