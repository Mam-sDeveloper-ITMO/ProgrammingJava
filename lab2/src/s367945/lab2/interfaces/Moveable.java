package s367945.lab2.interfaces;

import s367945.lab2.structures.Coordinates;

public interface Moveable extends Positioned {
    void move(Coordinates coords);
    void offset(Coordinates coords);
}
