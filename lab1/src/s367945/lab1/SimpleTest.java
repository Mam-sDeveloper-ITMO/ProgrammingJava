package s367945.lab1;

import ru.ifmo.se.pokemon.Battle;
import ru.ifmo.se.pokemon.Pokemon;
import s367945.lab1.pokemons.Aerodactyl;
import s367945.lab1.pokemons.Avalugg;
import s367945.lab1.pokemons.Bergmite;
import s367945.lab1.pokemons.Slaking;
import s367945.lab1.pokemons.Slakoth;
import s367945.lab1.pokemons.Vigoroth;

public class SimpleTest {
    public static void main(String[] args) {
        Battle b = new Battle();

        Pokemon[] allies = {
                new Aerodactyl("Yoko", 15),
                new Avalugg("Etsuko", 33),
                new Bergmite("Kanna", 21)
        };
        for (Pokemon p : allies) {
            b.addAlly(p);
        }

        Pokemon[] foes = {
                new Slakoth("Honoka", 1),
                new Vigoroth("Katsumi", 23),
                new Slaking("Hiroaki", 1)
        };
        for (Pokemon p : foes) {
            b.addFoe(p);
        }

        b.go();
    }
}
