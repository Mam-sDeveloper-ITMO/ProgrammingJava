package s367945.lab2.location.lake;

import s367945.lab2.interfaces.Positioned;
import s367945.lab2.location.Location;
import s367945.lab2.structures.point.Point;

public class Lake extends Location {
    private int depth;

    public Lake(String name, Point center, int radius, int depth) {
        super(name, center, radius);
        this.depth = depth;
    }
    
    public int getDepth() {
        return depth;
    }

    @Override
    public boolean checkConsistency(Positioned obj) {
        Point objPosition = obj.getPosition();
        return Math.abs(this.center.x - objPosition.x) <= this.radius
                && Math.abs(this.center.y - objPosition.y) <= this.radius
                && objPosition.z <= (this.center.z - this.depth);
    }
}
