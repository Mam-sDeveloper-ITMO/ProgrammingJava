package s367945.lab2.animal.human.interfaces;

public interface SadGirl {
    static class NoReasonsToDie extends RuntimeException {
        public NoReasonsToDie() {
            super("This world will collapse if sad girls doesn't have reasons for die");
        }
    };

    void cry();
}
