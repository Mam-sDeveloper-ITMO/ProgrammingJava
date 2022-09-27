package s367945.lab1.moves;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.Type;

public class FlashCannon extends SpecialMove {
    {
        this.type = Type.STEEL;
        this.power = 80;
        this.accuracy = 100;
    }

    protected void applyOppEffects(Pokemon p) {
        Effect e = new Effect().chance(0.1).turns(-1).stat(Stat.SPECIAL_DEFENSE, -1);
        p.addEffect(e);
    }

    protected String describe() {
        return "gathers all its light energy and releases it at once";
    }
}
