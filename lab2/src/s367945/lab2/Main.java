package s367945.lab2;

import java.util.function.Consumer;

import s367945.lab2.ahmatova.Ahmatova;
import s367945.lab2.animal.human.interfaces.SadGirl;
import s367945.lab2.bird.Bird;
import s367945.lab2.blackmaiden.BlackMaiden;
import s367945.lab2.enums.BreathSource;
import s367945.lab2.giraffe.Giraffe;
import s367945.lab2.gumilov.Gumilov;
import s367945.lab2.interfaces.Positioned;
import s367945.lab2.location.cave.Cave;
import s367945.lab2.location.earth.Earth;
import s367945.lab2.location.house.House;
import s367945.lab2.location.lake.Lake;
import s367945.lab2.location.moon.Moon;
import s367945.lab2.location.mythiccountry.MythicCountry;
import s367945.lab2.location.tropicalgarden.TropicalGarden;
import s367945.lab2.plant.grass.Grass;
import s367945.lab2.plant.palm.Palm;
import s367945.lab2.rain.Rain;
import s367945.lab2.shipsail.ShipSail;
import s367945.lab2.structures.pattern.Pattern;
import s367945.lab2.structures.point.Point;
import s367945.lab2.youngking.YoungKing;

public class Main {
    public static void main(String[] args) {
        testLogic();
    }

    public static void testLogic() {
        BlackMaiden blackMaiden = new BlackMaiden();
        YoungKing youngKing = new YoungKing();
        MythicCountry mythicCountry = new MythicCountry(blackMaiden.toString(), youngKing.toString());
        assert mythicCountry.checkConsistency(youngKing);

        Gumilov gumilov = new Gumilov();
        gumilov.walk(mythicCountry);
        for (String story : mythicCountry.getStories()) {
            gumilov.learnStory(story);
        }

        House gumilovHouse = new House("Gumulov flat in Kronstadt", new Point(131, 3144, 12), 30, 4, gumilov);
        gumilov.walk(gumilovHouse);
        assert gumilovHouse.checkConsistency(gumilov);

        Ahmatova ahmatova = new Ahmatova();
        ahmatova.walk(gumilovHouse);

        ahmatova.see(gumilov);
        ahmatova.hug(ahmatova);

        ahmatova.listen(gumilov.say());

        Lake chadLake = new Lake("Chad lake", new Point(0, 0, 0), 300, 20);
        Giraffe giraffe = new Giraffe(13);
        // giraffe.die();
        giraffe.walk(chadLake);

        Moon moon = new Moon(new Point(1000000000, 1333131331, 13131313));
        assert moon.getSurface().compareTo(giraffe.getSkinPattern()) < 0;
        moon.split();
        moon.wobble();
        assert moon.getSurface().compareTo(giraffe.getSkinPattern()) == 0;

        ShipSail shipSail = new ShipSail(new Pattern(1, 7), new Point());
        assert shipSail.getPattern().compareTo(giraffe.getSkinPattern()) < 0;

        Bird bird = new Bird(2);
        bird.fly(moon);

        Earth earth = new Earth(new Point());
        earth.see(giraffe);

        Cave cave = new Cave("Marble cave", new Point(1, 444, -2), 50);
        giraffe.hide(cave);
        assert cave.checkConsistency(giraffe);

        assert gumilov.trust(mythicCountry.getStories().toArray()[0]);
        assert gumilov.trust(mythicCountry.getStories().toArray()[1]);

        Rain rain = new Rain();
        rain.walk(ahmatova);
        assert ahmatova.trust(rain);

        Grass grass = new Grass("Grass with unimaginable smell");
        Palm palm = new Palm("Slim palm");
        TropicalGarden tropicalGarden = new TropicalGarden("Tropical garden", new Point(1, 2, 3), 100, grass, palm);
        assert tropicalGarden.checkConsistency(palm);

        ahmatova.cry();
        System.out.println("The end");
    }

    public static void testToString() {
        BlackMaiden blackMaiden = new BlackMaiden();
        
        YoungKing youngKing = new YoungKing();
        MythicCountry mythicCountry = new MythicCountry(blackMaiden.toString(), youngKing.toString());

        Gumilov gumilov = new Gumilov();

        House gumilovHouse = new House("Gumulov flat in Kronstadt", new Point(131, 3144, 12), 30, 4, gumilov);

        Ahmatova ahmatova = new Ahmatova();

        Giraffe giraffe = new Giraffe(13);

        Moon moon = new Moon(new Point(1000000000, 1333131331, 13131313));

        ShipSail shipSail = new ShipSail(new Pattern(1, 7), new Point());

        Bird bird = new Bird(2);

        Earth earth = new Earth(new Point());

        Cave cave = new Cave("Marble cave", new Point(1, 444, -2), 50);

        Rain rain = new Rain();
        Grass grass = new Grass("Grass with unimaginable smell");
        Palm palm = new Palm("Slim palm");
        TropicalGarden tropicalGarden = new TropicalGarden("Tropical garden", new Point(1, 2, 3), 100, grass, palm);

        // print toString for all objects
        System.out.println(blackMaiden);
        System.out.println(youngKing);
        System.out.println(mythicCountry);
        System.out.println(gumilov);
        System.out.println(gumilovHouse);
        System.out.println(ahmatova);
        System.out.println(giraffe);
        System.out.println(moon);
        System.out.println(shipSail);
        System.out.println(bird);
        System.out.println(earth);
        System.out.println(cave);
        System.out.println(rain);
        System.out.println(grass);
        System.out.println(palm);
        System.out.println(tropicalGarden); 
    }
}
