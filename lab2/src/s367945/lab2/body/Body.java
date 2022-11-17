package s367945.lab2.body;

import java.util.HashSet;
import java.util.Objects;

import s367945.lab2.bodypart.BodyPart;
import s367945.lab2.enums.Appearance;
import s367945.lab2.interfaces.Positioned;
import s367945.lab2.structures.pattern.Pattern;
import s367945.lab2.structures.point.Point;

public class Body implements Positioned {
    Point position;
    Pattern skin;
    BodyPart[] parts;

    public Body(Point position, Pattern skin, BodyPart... parts) {
        this.position = position;
        this.skin = skin;
        this.parts = parts;
    }

    public Point getPosition() {
        return this.position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Pattern getSkin() {
        return this.skin;
    }

    public HashSet<Appearance> getProperties() {
        HashSet<Appearance> properties = new HashSet<Appearance>();
        for (BodyPart part : this.parts) {
            properties.add(part.getAppearance());
        }
        return properties;
    }

    @Override
    public String toString() {
        return String.format("Body with appearance %s", this.getProperties());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Body)) {
            return false;
        }
        Body body = (Body) obj;
        return this.position.equals(body.position) && this.skin.equals(body.skin)
                && this.getProperties().equals(body.getProperties());
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.position, this.skin, this.getProperties());
    }
}
