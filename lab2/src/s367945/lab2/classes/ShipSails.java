package s367945.lab2.classes;

import java.util.HashSet;
import java.util.Arrays;

import s367945.lab2.abc.PropertiesContainer;
import s367945.lab2.enums.Property;

public class ShipSails extends PropertiesContainer {
    protected HashSet<Property> properties = new HashSet<Property>();
    
    public ShipSails(Property... properties) {
        super();
        this.properties.addAll(new HashSet<Property>(Arrays.asList(properties)));

    }
    
    @Override
    public HashSet<Property> getProperties() {
        return this.properties;
    }
}
