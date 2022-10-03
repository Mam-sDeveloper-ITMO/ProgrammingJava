package s367945.lab1.moves;

import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.Type;
import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.StatusMove;

public class Swagger extends StatusMove {
    public Swagger() {
        super(Type.NORMAL, 0, 85);
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        p.confuse();

        Effect e = new Effect().turns(-1).stat(Stat.ATTACK, 2);
        p.addEffect(e);
    }

    @Override
    protected String describe() {
        return "causes confusion and raises ATTACK";
    }
}
