package s367945.lab2.classes;

import java.util.HashSet;
import java.util.Arrays;

import s367945.lab2.abc.PropertiesContained;
import s367945.lab2.enums.Color;
import s367945.lab2.enums.Property;

public class Pattern extends PropertiesContained {
    public final Color color;
    private HashSet<Property> properties;

    public Pattern(Color color, Property... properties) {
        this.color = color;
        this.properties = new HashSet<Property>(Arrays.asList(properties));
    }

    @Override
    public HashSet<Property> getProperties() {
        return this.properties;
    }
}
