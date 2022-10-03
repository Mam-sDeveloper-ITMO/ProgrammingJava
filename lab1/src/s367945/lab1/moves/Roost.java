package s367945.lab1.moves;

import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.StatusMove;
import ru.ifmo.se.pokemon.Type;
import ru.ifmo.se.pokemon.Effect;

public class Roost extends StatusMove {
    public Roost() {
        super(Type.FLYING, 0, 100);
    }

    @Override
    protected void applySelfEffects(Pokemon p) {
        double halfHP = p.getStat(Stat.HP) / 2;
        int hpDiff = (int) Math.round(halfHP - p.getHP());

        if (hpDiff > 0) {
            Effect e = new Effect().turns(-1).stat(Stat.HP, hpDiff);
            p.addEffect(e);
        }
        
        // implement lose of FLYING type on one turn is impossible
        // Pokemon type field protected  
    }

    @Override
    protected String describe() {
        return "restores HP by up to half of its max HP";
    }
}
