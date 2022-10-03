package s367945.lab1.pokemons;

import ru.ifmo.se.pokemon.Type;
import s367945.lab1.moves.Slash;

public class Vigoroth extends Slakoth {
    public Vigoroth(String name, int level) {
        super(name, level);
        
        this.setStats(80, 80, 80, 55, 55, 90);
        this.addMove(new Slash());
    }
}
