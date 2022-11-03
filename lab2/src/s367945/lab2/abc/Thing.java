package s367945.lab2.abc;

import java.util.HashSet;
import java.util.Arrays;

import s367945.lab2.enums.Property;
import s367945.lab2.interfaces.Hugable;
import s367945.lab2.interfaces.Moveable;
import s367945.lab2.structures.Coordinates;

public abstract class Thing extends PropertiesContained implements Moveable, Hugable {
    private HashSet<Property> properties;
    protected Creature owner;
    protected Coordinates position;
    
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
    public HashSet<Property> getProperties() {
        return this.properties;
    }

    abstract public void interact(Creature actor);
}
