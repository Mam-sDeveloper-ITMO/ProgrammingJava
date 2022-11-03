package s367945.lab2.classes;

import java.util.HashSet;
import java.util.Arrays;

import s367945.lab2.abc.PropertiesContained;
import s367945.lab2.enums.Property;
import s367945.lab2.interfaces.Positioned;

public class Sight extends PropertiesContained {
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
}
