package s367945.lab2.location.house;

import s367945.lab2.animal.human.Human;
import s367945.lab2.interfaces.Positioned;
import s367945.lab2.location.Location;
import s367945.lab2.structures.point.Point;

public class House extends Location {
    protected Human owner;
    protected int height;

    public House(String name, Point center, int radius, int height, Human owner) {
        super(name, center, radius);
        this.owner = owner;
        this.height = height;
    }

    public Human getOwner() {
        return this.owner;
    }

    @Override
    public boolean checkConsistency(Positioned obj) {
        Point objPosition = obj.getPosition();
        return Math.abs(this.center.x - objPosition.x) <= this.radius
                && Math.abs(this.center.y - objPosition.y) <= this.radius
                && (0 <= objPosition.z)
                && objPosition.z <= (this.center.z + this.height);
    }
}
