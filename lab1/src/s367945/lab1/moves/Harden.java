package s367945.lab1.moves;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.StatusMove;
import ru.ifmo.se.pokemon.Type;
import ru.ifmo.se.pokemon.Stat;

public class Harden extends StatusMove {
    {
        this.type = Type.NORMAL;
    }

    protected void applySelfEffects(Pokemon p) {
        Effect e = new Effect().turns(-1).stat(Stat.DEFENSE, 1);
        p.addEffect(e);
    }

    protected String describe() {
        return "raises Defense by one stage";
    }
}
