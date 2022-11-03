package s367945.lab2.classes;

import java.util.HashSet;

import s367945.lab2.abc.PropertiesContained;
import s367945.lab2.enums.Property;

public class Rain extends PropertiesContained {
    protected HashSet<Property> properties;

    @Override
    public HashSet<Property> getProperties() {
        return this.properties;
    }
}
