package s367945.lab2.abc;

import s367945.lab2.enums.Property;
import java.util.HashSet;

public abstract class PropertiesContainer implements Comparable<PropertiesContainer> {
    abstract public HashSet<Property> getProperties();

    public boolean hasProperty(Property property) {
        return getProperties().contains(property);
    }

    public int getPropertiesValue() {
        return getProperties().stream().mapToInt(Property::getValue).sum();
    }

    public int compareTo(PropertiesContainer other) {
        return this.getPropertiesValue() - other.getPropertiesValue();
    }

    @Override
    public String toString() {
        String description = String.format("%s with properties %s", this.getClass().getSimpleName(), this.getProperties().toString());
        return description;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof PropertiesContainer))
            return false;
        PropertiesContainer other = (PropertiesContainer) obj;
        return this.getProperties().equals(other.getProperties());
    }

    @Override
    public int hashCode() {
        return getProperties().hashCode();
    }
}
