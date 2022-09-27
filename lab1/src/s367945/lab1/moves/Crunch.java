package s367945.lab1.moves;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.Type;

public class Crunch extends PhysicalMove {
    {
        this.type = Type.NORMAL;
        this.power = 80;
        this.accuracy = 100;
    }

    protected void applyOppEffects(Pokemon p) {
        Effect e = new Effect().chance(0.3).stat(Stat.DEFENSE, -1);
        p.addEffect(e);
    }

    protected String describe() {
        return "crunches with sharp fangs. May lower SP. DEF";
    }
}
