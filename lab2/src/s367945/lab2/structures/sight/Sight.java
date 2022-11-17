package s367945.lab2.structures.sight;

import s367945.lab2.interfaces.Positioned;

public class Sight {
    public final Positioned target;
    public final String description;

    public Sight(Positioned target, String description) {
        this.target = target;
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("Sight of %s: %s", this.target, this.description);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Sight)) {
            return false;
        }
        Sight sight = (Sight) obj;
        return this.target.equals(sight.target) && this.description.equals(sight.description);
    }

    @Override
    public int hashCode() {
        return this.target.hashCode() + this.description.hashCode();
    }
}
