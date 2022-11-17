package s367945.lab2.shipsail;

import s367945.lab2.interfaces.Positioned;
import s367945.lab2.structures.pattern.Pattern;
import s367945.lab2.structures.point.Point;

public class ShipSail implements Positioned {
    private Pattern pattern;
    private Point position;

    public ShipSail(Pattern pattern, Point position) {
        this.pattern = pattern;
        this.position = position;
    }
    
    public Pattern getPattern() {
        return pattern;
    }

    @Override
    public Point getPosition() {
        return this.position;
    }

    @Override
    public String toString() {
        return String.format("ShipSail with pattern %s", this.pattern);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ShipSail)) {
            return false;
        }
        ShipSail shipSail = (ShipSail) obj;
        return this.pattern.equals(shipSail.pattern);
    }

    @Override
    public int hashCode() {
        return this.pattern.hashCode();
    }
}
