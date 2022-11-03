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
}
