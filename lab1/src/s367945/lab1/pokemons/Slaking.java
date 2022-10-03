package s367945.lab1.pokemons;

import ru.ifmo.se.pokemon.Type;
import s367945.lab1.moves.Scratch;

public class Slaking extends Vigoroth {
    public Slaking(String name, int level) {
        super(name, level);
        
        this.setStats(150, 160, 100, 95, 65, 100);
        this.addMove(new Scratch());
    }
}
