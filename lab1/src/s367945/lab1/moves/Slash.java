package s367945.lab1.moves;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Type;

public class Slash extends PhysicalMove {
    {
        this.type = Type.NORMAL;
        this.power = 70;
        this.accuracy = 100;
        this.hits = 2;
    }

    protected String describe() {
        return "slashes with claws";
    }
}
