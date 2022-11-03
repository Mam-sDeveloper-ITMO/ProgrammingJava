package s367945.lab2.structures;

public class Coordinates {
    public final int x;
    public final int y;
    public final int z;

    public Coordinates(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Coordinates byOffset(Coordinates offset) {
        return new Coordinates(this.x + offset.x, this.y + offset.y, this.z + offset.z);
    }

    @Override
    public String toString() {
        return "Coordinates [x=" + x + ", y=" + y + ", z=" + z + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!super.equals(obj))
            return false;
        if (!(obj instanceof Coordinates))
            return false;

        Coordinates other = (Coordinates) obj;
        return this.x == other.x && this.y == other.y && this.z == other.z;
    }

    @Override
    public int hashCode() {
        return super.hashCode() + this.x + this.y + this.z;
    }
}
