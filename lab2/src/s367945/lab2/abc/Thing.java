package s367945.lab2.abc;

import java.util.HashSet;
import java.util.Arrays;

import s367945.lab2.enums.Property;
import s367945.lab2.interfaces.Hugable;
import s367945.lab2.interfaces.Moveable;
import s367945.lab2.structures.Coordinates;

public abstract class Thing extends PropertiesContainer implements Moveable, Hugable {
    protected HashSet<Property> properties;
    protected Creature owner;
    protected Coordinates position;
    
    abstract public void interact(Creature actor);

    public Thing(Coordinates position, Property... properties) {
        this.position = position;
        this.properties = new HashSet<Property>(Arrays.asList(properties));
    }

    public Creature getOwner() {
        return this.owner;
    }

    public void setOwner(Creature owner) {
        this.owner = owner;
    }

    @Override
    public Coordinates getPosition() {
        return this.position;
    }

    @Override
    public void move(Coordinates coords) {
        this.position = coords;
    }

    @Override
    public void offset(Coordinates coords) {
        this.position = this.position.byOffset(coords);
    }

    @Override
    public HashSet<Property> getProperties() {
        return this.properties;
    }

    @Override
    public String toString() {
        String description = String.format("Thing of %s with properties %s", this.owner, this.properties.toString());
        return description;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!super.equals(obj))
            return false;
        if (!(obj instanceof Thing))
            return false;
            
        Thing other = (Thing) obj;
        return this.owner.equals(other.owner);
    }

    @Override
    public int hashCode() {
        return super.hashCode() + this.owner.hashCode();
    }
}
