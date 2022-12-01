package s367945.lab2.interfaces;

import s367945.lab2.structures.point.Point;

public interface Positioned {
    public static class IncorrectPosition extends Exception {}

    Point getPosition();
}
