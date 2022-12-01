package s367945.lab2.animal.human.abs.ukpeer;

import java.util.HashSet;

import s367945.lab2.animal.human.Human;
import s367945.lab2.animal.human.interfaces.Peer;
import s367945.lab2.enums.Gender;
import s367945.lab2.enums.Personality;
import s367945.lab2.location.Location;

public abstract class UKPeer extends Human implements Peer {
    private HashSet<Location> lands;

    public UKPeer(int age, int health, String name, Gender gender, HashSet<Location> lands,
            Personality... personalities) {
        super(age, health, name, gender, personalities);
        this.lands = lands;
    }
    
    public HashSet<Location> getLands() {
        return lands;
    }
}
