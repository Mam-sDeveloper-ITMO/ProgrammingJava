package s367945.lab1.pokemons;

import ru.ifmo.se.pokemon.Type;
import s367945.lab1.moves.Slash;

public class Vigoroth extends Slakoth {
    {
        this.setType(Type.NORMAL);
        this.setStats(80, 80, 80, 55, 55, 90);
        this.addMove(new Slash());
    }

    public Vigoroth() {
        super();
    }

    public Vigoroth(String name, int level) {
        super(name, level);
    }
}
