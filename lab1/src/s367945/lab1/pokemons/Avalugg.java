package s367945.lab1.pokemons;

import ru.ifmo.se.pokemon.Type;
import s367945.lab1.moves.Crunch;

public class Avalugg extends Bergmite {
    {
        this.setType(Type.ICE);
        this.setStats(95, 117, 184, 44, 46, 28);
        this.addMove(new Crunch());
    }

    public Avalugg() {
        super();
    }

    public Avalugg(String name, int level) {
        super(name, level);
    }
}
