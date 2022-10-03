package s367945.lab1.moves;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Type;

public class ShadowClaw extends PhysicalMove {
    public ShadowClaw() {
        super(Type.GHOST, 70, 100);
    }
    
    @Override
    protected String describe() {
        return "slashes with a sharp claw made from shadows";
    }
}
