package s367945.lab1.pokemons;

import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;
import s367945.lab1.moves.Bite;
import s367945.lab1.moves.FlashCannon;
import s367945.lab1.moves.Harden;

public class Bergmite extends Pokemon {
    {
        this.setType(Type.ICE);
        this.setStats(55, 69, 85, 32, 35, 28);
        this.setMove(new FlashCannon(), new Harden(), new Bite());
    }

    public Bergmite() {
        super();
    }

    public Bergmite(String name, int level) {
        super(name, level);
    }
}
