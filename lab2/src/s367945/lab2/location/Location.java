package s367945.lab2.location;

import java.util.Objects;

import s367945.lab2.interfaces.Positioned;
import s367945.lab2.structures.point.Point;

public abstract class Location implements Positioned {
    protected String name;
    protected Point center;
    protected int radius;

    public Location(String name, Point center, int radius) {
        this.name = name;
        this.center = center;
        this.radius = radius;
    }
   
    abstract public boolean checkConsistency(Positioned obj);

    public String getName() {
        return this.name;
    }

    @Override
    public Point getPosition() {
        return this.center;
    }

    @Override
    public String toString() {
        return String.format("Location %s with center %s and radius %d", this.name, this.center, this.radius);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Location)) {
            return false;
        }
        Location location = (Location) obj;
        return this.name.equals(location.name) && this.center.equals(location.center) && this.radius == location.radius;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.center, this.radius);
    }
}
