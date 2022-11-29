package s367945.lab2.animal.human.interfaces;

public interface SadGirl {
    static class NoReasonsToDie extends RuntimeException {
        public NoReasonsToDie() {
            super();
        }
    };

    void cry();
}
