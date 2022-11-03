package s367945.lab2.classes;

import java.util.HashSet;

import s367945.lab2.abc.PropertiesContained;
import s367945.lab2.enums.Color;
import s367945.lab2.enums.Property;
import s367945.lab2.interfaces.Patterned;

public class Moon extends PropertiesContained implements Patterned {
    private Pattern surface = new Pattern(Color.GRAY, Property.GREAT);
    
    public void splitAndSwing() {
        this.surface = new Pattern(Color.GRAY, Property.MAGIC);
    }

    @Override
    public Pattern getPattern() {
        return this.surface;
    }

    @Override
    public HashSet<Property> getProperties() {
        return this.surface.getProperties();
    }
}
