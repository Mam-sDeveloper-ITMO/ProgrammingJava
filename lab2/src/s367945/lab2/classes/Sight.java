package s367945.lab2.classes;

import java.util.HashSet;
import java.util.Arrays;

import s367945.lab2.abc.PropertiesContainer;
import s367945.lab2.enums.Property;
import s367945.lab2.interfaces.Positioned;

public class Sight extends PropertiesContainer {
    public final Positioned target;
    protected HashSet<Property> properties;

    public Sight(Positioned target, Property... properties) {
        this.target = target;
        this.properties = new HashSet<Property>(Arrays.asList(properties));
    }

    @Override
    public HashSet<Property> getProperties() {
        return this.properties;
    }

    @Override
    public String toString() {
        return "Sight at " + this.target;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!super.equals(obj))
            return false;
        if (!(obj instanceof Sight))
            return false;

        Sight other = (Sight) obj;
        return this.target.equals(other.target);
    }

    @Override
    public int hashCode() {
        return super.hashCode() + this.target.hashCode();
    }
}
