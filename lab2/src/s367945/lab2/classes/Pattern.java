package s367945.lab2.classes;

import java.util.Set;

import s367945.lab2.abc.PropertiesContained;
import s367945.lab2.enums.Color;
import s367945.lab2.enums.Property;

public class Pattern extends PropertiesContained {
    public final Color color;
    public final String description;
    private Set<Property> properties;

    public Pattern(Color color, String description, Property... properties) {
        this.color = color;
        this.description = description;
        this.properties = Set.of();
    }

    @Override
    public Set<Property> getProperties() {
        return this.properties;
    }
}
