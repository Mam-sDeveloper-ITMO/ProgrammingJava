package s367945.lab2.location.earth;

import s367945.lab2.interfaces.Positioned;
import s367945.lab2.interfaces.Seeable;
import s367945.lab2.location.Location;
import s367945.lab2.structures.point.Point;
import s367945.lab2.structures.sight.Sight;

public class Earth extends Location implements Seeable {

    public Earth(Point currentPos) {
        super("Earth", currentPos, 6371);
    }

    @Override
    public Sight see(Positioned target) {
        Sight sight = new Sight(target, "A lot of lovely things");
        return sight;
    }

    @Override
    public boolean checkConsistency(Positioned obj) {
        Point objPosition = obj.getPosition();
        return Math.abs(this.center.x - objPosition.x) <= this.radius
                && Math.abs(this.center.y - objPosition.y) <= this.radius
                && Math.abs(this.center.z - objPosition.z) <= this.radius;
    }

}
