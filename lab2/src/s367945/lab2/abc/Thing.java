package s367945.lab2.abc;

import java.util.Set;

import s367945.lab2.enums.Property;
import s367945.lab2.interfaces.Hugable;
import s367945.lab2.interfaces.Moveable;
import s367945.lab2.structures.Coordinates;

public abstract class Thing extends PropertiesContained implements Moveable, Hugable {
    protected Creature owner;
    protected Set<Property> properties;
    protected Coordinates position;

    public Thing(Creature owner, Coordinates position, Property... properties) {
        this.owner = owner;
        this.position = position;
        this.properties = Set.of(properties);
    }

    public Creature getOwner() {
        return this.owner;
    }

    public void changeOwner(Creature newOwner) {
        this.owner = newOwner;
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
    public Set<Property> getProperties() {
        return this.properties;
    }

    abstract public void interact(Creature actor);
}
