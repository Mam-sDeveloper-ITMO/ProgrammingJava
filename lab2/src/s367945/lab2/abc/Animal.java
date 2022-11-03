package s367945.lab2.abc;

import s367945.lab2.interfaces.Patterned;

import java.util.HashSet;

import s367945.lab2.classes.Body;
import s367945.lab2.classes.Pattern;
import s367945.lab2.enums.Property;

public abstract class Animal extends Creature implements Patterned {
    public final Pattern skin;

    public Animal(Body body, int health, int age, Pattern skin) {
        super(body, health, age);
        this.skin = skin;
    }

    @Override
    public int compareTo(PropertiesContained other) {
        if (other instanceof Patterned) {
            Patterned otherPatterned = (Patterned) other;
            return this.skin.compareTo(otherPatterned.getPattern());
        }
        return super.compareTo(other);
    }

    @Override
    public Pattern getPattern() {
        return this.skin;
    }

    @Override
    public HashSet<Property> getProperties() {
        HashSet<Property> properties = new HashSet<Property>();
        properties.addAll(this.body.getProperties());
        properties.addAll(this.skin.getProperties());
        return properties;
    }
}
