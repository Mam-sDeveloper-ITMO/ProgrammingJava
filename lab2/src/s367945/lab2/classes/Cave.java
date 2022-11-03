package s367945.lab2.classes;

import s367945.lab2.interfaces.Positioned;
import s367945.lab2.structures.Coordinates;

public class Cave implements Positioned {
    protected Coordinates position;
    
    public Cave(Coordinates position) {
        this.position = position;
    }
    
    @Override
    public Coordinates getPosition() {
        return this.position;
    }

    @Override
    public String toString() {
        return "Cave at " + this.position;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!super.equals(obj))
            return false;
        if (!(obj instanceof Cave))
            return false;

        Cave other = (Cave) obj;
        return this.position.equals(other.position);
    }

    @Override
    public int hashCode() {
        return super.hashCode() + this.position.hashCode();
    }
}
