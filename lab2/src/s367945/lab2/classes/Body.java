package s367945.lab2.classes;

import java.util.HashSet;

import s367945.lab2.abc.BodyPart;
import s367945.lab2.abc.Creature;
import s367945.lab2.abc.Thing;
import s367945.lab2.enums.Property;
import s367945.lab2.structures.Coordinates;

public class Body extends Thing {
    protected Creature owner;
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
}
