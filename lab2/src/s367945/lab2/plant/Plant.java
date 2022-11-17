package s367945.lab2.plant;

import s367945.lab2.enums.BreathSource;
import s367945.lab2.interfaces.Positioned;
import s367945.lab2.structures.point.Point;

public abstract class Plant implements Positioned {
    protected String name;
    protected Point position;

    public Plant(String name) {
        this.name = name;
    }

    abstract public BreathSource photosynthesize(BreathSource source);

    public String getName() {
        return name;
    }

    @Override
    public Point getPosition() {
        return this.position;
    }

    @Override
    public String toString() {
        return String.format("Plant %s", this.name);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Plant)) {
            return false;
        }
        Plant plant = (Plant) obj;
        return this.name.equals(plant.name);
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
}
