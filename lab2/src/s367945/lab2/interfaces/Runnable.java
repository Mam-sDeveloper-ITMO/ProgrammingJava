package s367945.lab2.interfaces;


public interface Runnable {
    void run(Positioned target) throws Positioned.IncorrectPosition;
}
