package s367945.lab1.moves;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Type;

public class Scratch extends PhysicalMove {
    {
        this.type = Type.NORMAL;
        this.power = 40;
        this.accuracy = 100;
    }

    protected String describe() {
        return "scratches with sharp claws";
    }
}
