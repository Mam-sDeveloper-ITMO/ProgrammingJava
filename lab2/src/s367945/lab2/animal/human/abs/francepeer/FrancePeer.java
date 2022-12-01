package s367945.lab2.animal.human.abs.francepeer;

import s367945.lab2.animal.human.Human;
import s367945.lab2.animal.human.interfaces.Peer;
import s367945.lab2.enums.Gender;
import s367945.lab2.enums.Personality;

public abstract class FrancePeer extends Human implements Peer {
    protected Human knownKing;

    public FrancePeer(int age, int health, String name, Gender gender, Human knownKing, Personality... personalities) {
        super(age, health, name, gender, personalities);
        this.knownKing = knownKing;
    }

    public Human getKnownKing() {
        return knownKing;
    }
}
