package s367945.lab2.classes;

import java.util.HashSet;

import s367945.lab2.abc.PropertiesContainer;
import s367945.lab2.enums.Property;

public class Rain extends PropertiesContainer {
    protected HashSet<Property> properties;

    public Rain() {
        this.properties = new HashSet<Property>();
        this.properties.add(Property.WET);
    }
    
    @Override
    public HashSet<Property> getProperties() {
        return this.properties;
    }
}
