package s367945.lab2;

import s367945.lab2.classes.Bird;
import s367945.lab2.classes.Body;
import s367945.lab2.classes.Cave;
import s367945.lab2.classes.Fog;
import s367945.lab2.classes.Giraffe;
import s367945.lab2.classes.Moon;
import s367945.lab2.classes.Narrator;
import s367945.lab2.classes.Pattern;
import s367945.lab2.classes.Rain;
import s367945.lab2.classes.SadGirl;
import s367945.lab2.classes.ShipSails;
import s367945.lab2.classes.bodyparts.Hand;
import s367945.lab2.classes.bodyparts.Head;
import s367945.lab2.classes.bodyparts.Leg;
import s367945.lab2.classes.bodyparts.Neck;
import s367945.lab2.classes.bodyparts.Tail;
import s367945.lab2.classes.bodyparts.Torso;
import s367945.lab2.enums.Color;
import s367945.lab2.enums.Gender;
import s367945.lab2.enums.Property;
import s367945.lab2.structures.Coordinates;

public class Main {
    public static void main(String[] args) {
        testDescription();
    }

    private static void testDescription() {
        Body narratorBody = new Body(
            new Coordinates(0, 0, 0),
            new Head(),
            new Neck(),
            new Torso(),
            new Hand(), new Hand(), 
            new Leg(), new Leg() 
        );
        Narrator narrator = new Narrator(narratorBody, 10, 21, "Гумилев", Gender.MAN);
        Leg sadgirlLeg = new Leg(Property.SAD);
        Hand sadgirlHand = new Hand(Property.THIN);
        Body sadgirlBody = new Body(
            new Coordinates(2, 3, 0),
            new Head(),
            new Neck(),
            new Torso(),
            sadgirlHand, sadgirlHand,
            sadgirlLeg, sadgirlLeg
        );
        SadGirl sadgirl = new SadGirl(sadgirlBody, 7, 18, "Ахматова", Gender.WOMEN, Property.SAD, Property.DEPRESSED);
        Body giraffeBody = new Body(
            new Coordinates(0, 0, 0),
            new Head(),
            new Neck(),
            new Torso(Property.SLIM, Property.ELEGANT),
            new Leg(), new Leg(),
            new Leg(), new Leg(),
            new Tail()
        );
        Pattern giraffeSkin = new Pattern(Color.YELLOW, Property.MAGIC);
        Giraffe giraffe = new Giraffe(giraffeBody, 10, 7, giraffeSkin);
        Moon moon = new Moon();
        Bird bird = new Bird(new Body(new Coordinates(0, 0, 0)), 1, 2, new Pattern(Color.BROWN));
        Cave marbleCave = new Cave(new Coordinates(0, 0, 0));                
        Fog fog = new Fog();
        Rain rain = new Rain();
        
        // Print all objects
        System.out.println(sadgirlBody);
        System.out.println(sadgirlHand);
        System.out.println(narrator);
        System.out.println(sadgirl);
        System.out.println(giraffe);
        System.out.println(moon);
        System.out.println(bird);
        System.out.println(marbleCave);
        System.out.println(fog);
        System.out.println(rain);
    }

    private static void testAll() {
        Body narratorBody = new Body(
            new Coordinates(0, 0, 0),
            new Head(),
            new Neck(),
            new Torso(),
            new Hand(), new Hand(), 
            new Leg(), new Leg() 
        );
        Narrator narrator = new Narrator(narratorBody, 10, 21, "Гумилев", Gender.MAN);
        narrator.listen("жираф на озере Чад");

        Leg sadgirlLeg = new Leg(Property.SAD);
        Hand sadgirlHand = new Hand(Property.THIN);
        Body sadgirlBody = new Body(
            new Coordinates(2, 3, 0),
            new Head(),
            new Neck(),
            new Torso(),
            sadgirlHand, sadgirlHand,
            sadgirlLeg, sadgirlLeg
        );
        SadGirl sadgirl = new SadGirl(sadgirlBody, 7, 18, "Ахматова", Gender.WOMEN, Property.SAD, Property.DEPRESSED);
        

        if (sadgirl.see(narrator).hasProperty(Property.SAD)) {
            System.out.println("Сегодня, я вижу, особенно грустен твой взгляд");
        }
        if (sadgirlHand.hasProperty(Property.THIN)) {
            System.out.println("И руки особенно тонки");
        }
        sadgirl.hug(sadgirlLeg); 
        System.out.println("колени обняв");

        sadgirl.listen(narrator.say());
        System.out.println("прослушай");

        Body giraffeBody = new Body(
            new Coordinates(0, 0, 0),
            new Head(),
            new Neck(),
            new Torso(Property.SLIM, Property.ELEGANT),
            new Leg(), new Leg(),
            new Leg(), new Leg(),
            new Tail()
        );
        Pattern giraffeSkin = new Pattern(Color.YELLOW, Property.MAGIC);
        Giraffe giraffe = new Giraffe(giraffeBody, 10, 7, giraffeSkin);

        System.out.println("далеко на озере чад"); 
        if (giraffe.hasProperty(Property.ELEGANT)) {
            System.out.println("изысканный");
        }

        giraffe.walk();
        System.out.println("бродит жираф"); 

        if (giraffe.hasProperty(Property.SLIM)) {
            System.out.println("Ему грациозная стройность и нега дана");
        }
        
        if (giraffe.skin.hasProperty(Property.MAGIC)) {
            System.out.println("И шкуру его украшает волшебный узор");
        }

        Moon moon = new Moon();
        moon.splitAndSwing();
        if (giraffe.skin.compareTo(moon) == 0) {
            System.out.println("С которым равняться осмелится только луна");
        }
        System.out.println("Дробясь и качаясь на влаге широких озер.");

        if (giraffe.equals(new ShipSails(Property.COLORFUL))) {
            System.out.println("Вдали он подобен цветным парусам корабля");
        }

        Bird bird = new Bird(new Body(new Coordinates(0, 0, 0)), 1, 2, new Pattern(Color.BROWN));
        bird.fly();
        System.out.println("И бег его плавен, как радостный птичий полет.");
        
        System.out.println("Я знаю, что много чудесного видит земля");
        Cave marbleCave = new Cave(new Coordinates(0, 0, 0));        
        giraffe.hide(marbleCave);
        System.out.println("когда на закате он прячется в мраморный грот");

        // Я знаю веселые сказки таинственных стран
        // Про чёрную деву, про страсть молодого вождя,
        narrator.listen("веселые сказки таинственных стран");
        narrator.listen("сказка про чёрную деву");
        narrator.listen("сказка про страсть молодого вождя");
        System.out.println(narrator.say());
        System.out.println(narrator.say());
        System.out.println(narrator.say());

        System.out.println("Но ты слишком долго вдыхала тяжелый туман");
        sadgirl.breath(new Fog());

        if (sadgirl.trust(new Rain())) {
            System.out.println("Ты верить не хочешь во что-нибудь кроме дождя");
        }
        
        sadgirl.cry();
        System.out.println("Ты плачешь?");
        sadgirl.listen(narrator.say());
        System.out.println("Послушай");
        giraffe.walk();
        System.out.println("далёко, на озере Чад Изысканный бродит жираф.");
    }
}
