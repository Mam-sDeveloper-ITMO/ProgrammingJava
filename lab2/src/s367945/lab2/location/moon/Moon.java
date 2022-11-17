package s367945.lab2.location.moon;

import s367945.lab2.interfaces.Positioned;
import s367945.lab2.location.Location;
import s367945.lab2.structures.pattern.Pattern;
import s367945.lab2.structures.point.Point;

public class Moon extends Location {
    private Pattern surface;

    public Moon(Point currentPos) {
        super("Moon", currentPos, 1737);
        this.surface = new Pattern(1, 4);
    }

    public void split() {
        int col = this.surface.colorfulness;
        int comp = this.surface.complexity;
        this.surface = new Pattern(col, comp * 2);
    }

    public void wobble() {
        int col = this.surface.colorfulness;
        int comp = this.surface.complexity;
        this.surface = new Pattern(col, comp * 4);
    }

    public Pattern getSurface() {
        return surface;
    }
    
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean checkConsistency(Positioned obj) {
        Point objPosition = obj.getPosition();
        return Math.abs(this.center.x - objPosition.x) <= this.radius
                && Math.abs(this.center.y - objPosition.y) <= this.radius
                && Math.abs(this.center.z - objPosition.z) <= this.radius;
    }
}
