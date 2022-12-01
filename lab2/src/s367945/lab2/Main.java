package s367945.lab2;

import java.util.HashSet;
import java.util.Random;

import s367945.lab2.ahmatova.Ahmatova;
import s367945.lab2.animal.Animal;
import s367945.lab2.animal.human.abs.dwarf.Dwarf;
import s367945.lab2.animal.human.abs.fisherman.FisherMan;
import s367945.lab2.animal.human.abs.giant.Giant;
import s367945.lab2.bird.Bird;
import s367945.lab2.blackmaiden.BlackMaiden;
import s367945.lab2.body.Body;
import s367945.lab2.bodypart.bodyparts.Hand;
import s367945.lab2.bodypart.bodyparts.Head;
import s367945.lab2.bodypart.bodyparts.Leg;
import s367945.lab2.bodypart.bodyparts.Neck;
import s367945.lab2.bodypart.bodyparts.Torso;
import s367945.lab2.enums.Appearance;
import s367945.lab2.enums.Gender;
import s367945.lab2.enums.Personality;
import s367945.lab2.fish.Fish;
import s367945.lab2.giraffe.Giraffe;
import s367945.lab2.gumilov.Gumilov;
import s367945.lab2.interfaces.Positioned;
import s367945.lab2.lion.Lion;
import s367945.lab2.location.cave.Cave;
import s367945.lab2.location.earth.Earth;
import s367945.lab2.location.house.House;
import s367945.lab2.location.lake.Lake;
import s367945.lab2.location.magicforest.MagicForest;
import s367945.lab2.location.moon.Moon;
import s367945.lab2.location.mythiccountry.MythicCountry;
import s367945.lab2.location.tropicalgarden.TropicalGarden;
import s367945.lab2.nekowoman.NekoWoman;
import s367945.lab2.plant.grass.Grass;
import s367945.lab2.plant.palm.Palm;
import s367945.lab2.plant.root.Root;
import s367945.lab2.plant.tree.WhiteTree;
import s367945.lab2.rain.Rain;
import s367945.lab2.shipsail.ShipSail;
import s367945.lab2.structures.pattern.Pattern;
import s367945.lab2.structures.point.Point;
import s367945.lab2.structures.sight.Sight;
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
        try {
            giraffe.walk(chadLake);
        } catch (Positioned.IncorrectPosition e) {
            System.out.println(e.getMessage());
        }

        try {
            giraffe.walk(gumilovHouse);
        } catch (Positioned.IncorrectPosition e) {
            System.out.println("Don`t try to get giraffe at home!");
        }

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
        // catch exception for giraffe hide
        try {
            giraffe.hide(cave);
        } catch (Positioned.IncorrectPosition e) {
            System.out.println(e.getMessage());
        }
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
        System.out.println("... and the new beginning");

        Root root = new Root("Root with unimaginable smell");
        WhiteTree whiteTree = new WhiteTree("White tree", root);
        MagicForest magicForest = new MagicForest("Magic forest", new Point(1, 2, 3), 100, whiteTree);
        try {
            whiteTree.walk(magicForest);
        } catch (Positioned.IncorrectPosition e) {
            System.out.println(e.getMessage());
        }

        // anonymous classes of giant and dwarf
        class CommonGiant extends Giant {
            public CommonGiant(int age, String name) {
                super(age, 100, name, Gender.EFFREU, Personality.ANGRY);
            }

            @Override
            public Sight see(Positioned target) {
                return new Sight(target, "Angry sight on " + target.toString());
            }

            @Override
            public String say() {
                return "Urrr";
            }

            @Override
            public void listen(String info) {
                this.say();
            }

            @Override
            public boolean trust(Object obj) {
                return false;
            }

            @Override
            public void hug(Object obj) {
                if (obj instanceof Animal) {
                    ((Animal) obj).die();
                }
            }

            @Override
            public HashSet<Personality> getPersonality() {
                return this.personality;
            }
        }

        class CommonDwarf extends Dwarf {
            public CommonDwarf(int age, String name, Gender gender) {
                super(age, 1, name, gender);
            }

            @Override
            public Sight see(Positioned target) {
                return new Sight(target, "Scary sight on " + target.toString());
            }

            @Override
            public String say() {
                return "I`m here!!!";
            }

            @Override
            public void listen(String info) {
                this.knowledge.add(info);
            }

            @Override
            public boolean trust(Object obj) {
                return true;
            }

            @Override
            public void hug(Object obj) {
            }

            @Override
            public HashSet<Personality> getPersonality() {
                return this.personality;
            }
        }

        CommonGiant commonGiant1 = new CommonGiant(100, "Umputun");
        CommonGiant commonGiant2 = new CommonGiant(100, "Bobuk");

        CommonDwarf commonDwarf1 = new CommonDwarf(10, "Alex", Gender.CETEROFLUID);
        CommonDwarf commonDwarf2 = new CommonDwarf(10, "Ksenks", Gender.AFFECTUGENDER);

        Lion lion = new Lion(10);
        assert magicForest.checkConsistency(commonGiant1);
        assert magicForest.checkConsistency(commonDwarf1);
        assert magicForest.checkConsistency(lion);

        FisherMan anonymousFisherMan = new FisherMan(10, 10, "Boris", Gender.MAN) {
            @Override
            protected Body buildBody() {
                return new Body(new Point(), new Pattern(1, 3),
                        new Head(1, Appearance.WELL_BUILT), new Neck(1, Appearance.WELL_BUILT),
                        new Leg(1, Appearance.WELL_BUILT), new Leg(1, Appearance.WELL_BUILT),
                        new Hand(1, Appearance.WELL_BUILT), new Hand(1, Appearance.WELL_BUILT),
                        new Torso(1, Appearance.WELL_BUILT));
            }

            @Override
            public Sight see(Positioned target) {
                return new Sight(target, "Attentive sight on " + target);
            }

            @Override
            public String say() {
                Random random = new Random();
                if (this.knowledge.size() == 0) {
                    return "Can you tell me something?";
                }

                int index = random.nextInt(this.knowledge.size());
                Object info = this.knowledge.toArray()[index];
                return "I know one story about fishing: " + info;
            }

            @Override
            public void listen(String info) {
                this.knowledge.add(info);
            }

            @Override
            public boolean trust(Object obj) {
                return this.knowledge.contains(obj) || obj instanceof Fish;
            }

            @Override
            public void hug(Object obj) {
                this.personality.add(Personality.HAPPY);
            }

            @Override
            public HashSet<Personality> getPersonality() {
                return this.personality;
            }

            @Override
            public Fish fish() {
                this.moveBody(chadLake.getPosition());
                return new Fish(1);
            }
        };

        anonymousFisherMan.see(commonDwarf1);

        NekoWoman nekoWoman = new NekoWoman(1000, 1, "Mistery woman");
        anonymousFisherMan.listen(nekoWoman.moan());
        anonymousFisherMan.listen(nekoWoman.sigh());

        nekoWoman.die();
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
