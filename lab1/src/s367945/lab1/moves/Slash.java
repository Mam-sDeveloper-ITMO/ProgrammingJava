package s367945.lab1.moves;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Type;

public class Slash extends PhysicalMove {
    public Slash() {
        super(Type.NORMAL, 70, 100);
    }

    @Override
    protected String describe() {
        return "slashes with claws";
    }
}
