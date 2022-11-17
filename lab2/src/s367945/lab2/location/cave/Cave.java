package s367945.lab2.location.cave;

import s367945.lab2.interfaces.Positioned;
import s367945.lab2.location.Location;
import s367945.lab2.structures.point.Point;

public class Cave extends Location {
    public Cave(String name, Point center, int radius) {
        super(name, center, radius);
    }

    @Override
    public boolean checkConsistency(Positioned obj) {
        Point objPosition = obj.getPosition();
        int offset = Math.abs(this.center.x - objPosition.x) +
                Math.abs(this.center.y - objPosition.y) +
                Math.abs(this.center.z - objPosition.z);

        return offset <= this.radius / 3.0;
    }
}
