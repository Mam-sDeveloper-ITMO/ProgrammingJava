package s367945.lab2.animal.human.interfaces;

public interface SadGirl {
    public static class NoReasonsToDie extends RuntimeException {
        public NoReasonsToDie() {
            super();
        }
    };

    void cry();
}
