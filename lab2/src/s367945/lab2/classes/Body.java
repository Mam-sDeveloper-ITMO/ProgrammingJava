package s367945.lab2.classes;

import java.util.Arrays;
import java.util.HashSet;

import s367945.lab2.abc.BodyPart;
import s367945.lab2.abc.Creature;
import s367945.lab2.abc.Thing;
import s367945.lab2.enums.Property;
import s367945.lab2.structures.Coordinates;

public class Body extends Thing {
    protected BodyPart[] bodyParts;

    public Body(Coordinates position, BodyPart... bodyParts) {
        super(position);
        this.bodyParts = bodyParts;
    }

    public void onHug(Creature actor) {
        this.owner.onHug(actor);
    };

    @Override
    public void interact(Creature actor) {
        if (this.owner.hasProperty(Property.ANGRY)) {
            actor.hurt(3);
        } else if (this.owner.hasProperty(Property.BENEVOLENT)) {
            actor.heal(1);
        }
    }

    @Override
    public HashSet<Property> getProperties() {
        HashSet<Property> properties = new HashSet<Property>();
        for (BodyPart bodyPart : this.bodyParts) {
            properties.addAll(bodyPart.getProperties());
        }

        return properties;
    }

    @Override
    public String toString() {
        String description = String.format("Body from parts %s", Arrays.toString(this.bodyParts));
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
        if (!(obj instanceof Body))
            return false;

        Body other = (Body) obj;
        return Arrays.equals(this.bodyParts, other.bodyParts);
    }

    @Override
    public int hashCode() {
        return super.hashCode() + Arrays.hashCode(this.bodyParts);
    }
}
