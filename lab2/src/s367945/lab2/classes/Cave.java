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
}
