package s367945.lab2.abc;

import s367945.lab2.enums.Property;
import java.util.Set;

public abstract class PropertiesContained implements Comparable<PropertiesContained> {
    abstract public Set<Property> getProperties();

    public boolean hasProperty(Property property) {
        return getProperties().contains(property);
    }

    public int getPropertiesValue() {
        return getProperties().stream().mapToInt(property -> property.value).sum();
    }

    public int compareTo(PropertiesContained other) {
        return this.getPropertiesValue() - other.getPropertiesValue();
    }
}
