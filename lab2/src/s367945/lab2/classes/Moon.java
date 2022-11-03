package s367945.lab2.classes;

import java.util.HashSet;

import s367945.lab2.abc.PropertiesContainer;
import s367945.lab2.enums.Color;
import s367945.lab2.enums.Property;
import s367945.lab2.interfaces.Patterned;

public class Moon extends PropertiesContainer implements Patterned {
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

    @Override
    public String toString() {
        return "Moon with surface " + this.surface;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!super.equals(obj))
            return false;
        if (!(obj instanceof Moon))
            return false;

        Moon other = (Moon) obj;
        return this.surface.equals(other.surface);
    }

    @Override
    public int hashCode() {
        return super.hashCode() + this.surface.hashCode();
    }
}
