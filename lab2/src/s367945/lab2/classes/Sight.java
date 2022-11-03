package s367945.lab2.classes;

import java.util.Set;

import s367945.lab2.abc.PropertiesContained;
import s367945.lab2.enums.Property;
import s367945.lab2.interfaces.Positioned;

public class Sight extends PropertiesContained {
    public final Positioned target;
    protected Set<Property> properties;

    public Sight(Positioned target, Property... properties) {
        this.target = target;
        this.properties = Set.of(properties);
    }

    @Override
    public Set<Property> getProperties() {
        return this.properties;
    }
}
