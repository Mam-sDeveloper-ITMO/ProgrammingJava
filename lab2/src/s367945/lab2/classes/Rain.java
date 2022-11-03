package s367945.lab2.classes;

import java.util.Set;

import s367945.lab2.abc.PropertiesContained;
import s367945.lab2.enums.Property;

public class Rain extends PropertiesContained {
    protected Set<Property> properties;

    @Override
    public Set<Property> getProperties() {
        return this.properties;
    }
}
