package s367945.lab1.moves;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Bite extends PhysicalMove {
    {
        this.type = Type.DARK;
        this.power = 60;
        this.accuracy = 100;
    }

    protected void applyOppEffects(Pokemon p) {
        if (Math.random() < 0.3) {
            Effect.flinch(p);
        }

        // or with flinch equivalent
        // Effect e = new Effect().chance(0.3).turns(1).condition(Status.FREEZE)
        // p.addEffect(e);
    }

    protected String describe() {
        return "bites with vicious fangs";
    }
}
