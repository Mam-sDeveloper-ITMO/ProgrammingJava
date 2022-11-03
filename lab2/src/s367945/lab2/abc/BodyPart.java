package s367945.lab2.abc;

import java.util.HashSet;
import java.util.Arrays;

import s367945.lab2.enums.Property;
import s367945.lab2.interfaces.Hugable;

public abstract class BodyPart extends PropertiesContainer implements Hugable {
    protected final HashSet<Property> properties;
    
    public BodyPart(Property... properties) {
        this.properties = new HashSet<Property>(Arrays.asList(properties));
    }
    
    @Override
    public HashSet<Property> getProperties() {
        return this.properties;
    }

    @Override
    public void onHug(Creature actor) {
        // Do nothing cause it's a body part and if you want specific action try to hug body or it`s owner
    }

    @Override
    public String toString() {
        String description = String.format("%s with properties %s", this.getClass().getSimpleName(),
                this.getProperties().toString());
        return description;
    }
}
