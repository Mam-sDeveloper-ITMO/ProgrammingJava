package s367945.lab1.moves;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Type;

public class FireBlast extends SpecialMove {
    {
        this.type = Type.FIRE;
        this.power = 110;
        this.accuracy = 85;
    }
    
    protected void applyOppEffect(Pokemon p) {
        if (Math.random() < 0.1) {
            Effect.burn(p);
        }
    }

    protected String describe() {
        return "hit with an intense flame. It may burn the target";
    }
}
