package s367945.lab1.pokemons;

import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;
import s367945.lab1.moves.FireBlast;
import s367945.lab1.moves.ShadowClaw;

public class Slakoth extends Pokemon {
    public Slakoth(String name, int level) {
        super(name, level);
        
        this.setType(Type.NORMAL);
        this.setStats(60, 60, 60, 35, 35, 30);
        this.setMove(new FireBlast(), new ShadowClaw());
    }
}
