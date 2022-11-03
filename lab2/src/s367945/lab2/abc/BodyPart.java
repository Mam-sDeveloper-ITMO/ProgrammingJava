package s367945.lab2.abc;

import java.util.Set;

import s367945.lab2.enums.Property;

public abstract class BodyPart extends PropertiesContained {
    protected final Set<Property> properties;
    
    public BodyPart(Property... properties) {
        this.properties = Set.of(properties);
    }
    
    @Override
    public Set<Property> getProperties() {
        return this.properties;
    }
}
