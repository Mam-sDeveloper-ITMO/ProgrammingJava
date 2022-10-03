package s367945.lab1.pokemons;

import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;
import s367945.lab1.moves.DoubleTeam;
import s367945.lab1.moves.Roost;
import s367945.lab1.moves.ScaryFace;
import s367945.lab1.moves.Swagger;

public class Aerodactyl extends Pokemon {
    public Aerodactyl(String name, int level) {
        super(name, level);

        this.setType(Type.ROCK, Type.FLYING);
        this.setStats(80, 105, 65, 60, 75, 130);
        this.setMove(new Roost(), new Swagger(), new ScaryFace(), new DoubleTeam());
    }
}
