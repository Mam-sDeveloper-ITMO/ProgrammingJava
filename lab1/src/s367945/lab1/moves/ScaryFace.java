package s367945.lab1.moves;

import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.StatusMove;
import ru.ifmo.se.pokemon.Type;
import ru.ifmo.se.pokemon.Effect;

public class ScaryFace extends StatusMove {
    public ScaryFace() {
        super(Type.NORMAL, 0, 100);
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        Effect e = new Effect().turns(-1).stat(Stat.SPEED, -2);
        p.addEffect(e);
    }

    @Override
    protected String describe() {
        return "reduces opp`s SPEED";
    }
}
