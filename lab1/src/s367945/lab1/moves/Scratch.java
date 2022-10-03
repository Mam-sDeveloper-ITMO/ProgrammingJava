package s367945.lab1.moves;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Type;

public class Scratch extends PhysicalMove {
    public Scratch() {
        super(Type.NORMAL, 40, 100);
    }

    @Override
    protected String describe() {
        return "scratches with sharp claws";
    }
}
