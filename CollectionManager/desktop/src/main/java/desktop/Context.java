package desktop;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import adapter.Adapter;
import adapter.common.AuthAdapter;
import auth.AuthToken;
import desktop.lib.RemoteManager;
import humandeque.manager.CollectionManager;
import lombok.Getter;
import lombok.Setter;
import models.Car;
import models.Coordinates;
import models.Human;
import models.Mood;

public class Context {
    @Getter
    @Setter
    private String rootPage = "main";
    
    @Getter
    @Setter
    private String username = "username";

    @Getter
    @Setter
    private List<Human> humans = generateRandomHumans(200);

    @Getter
    private CollectionManager manager = null;

    private List<Human> generateRandomHumans(int count) {
        Human[] humans = new Human[count];

        Random random = new Random();

        for (int i = 0; i < count; i++) {
            Human human = Human.builder().id(i + 1L).name("Human" + (i + 1L))
                    .coordinates(new Coordinates(random.nextFloat(), random.nextFloat()))
                    .creationDate(LocalDateTime.now()).realHero(random.nextBoolean()).hasToothpick(random.nextBoolean())
                    .impactSpeed(random.nextDouble()).soundtrackName("Soundtrack" + (i + 1L))
                    .minutesOfWaiting(random.nextFloat()).mood(Mood.values()[random.nextInt(Mood.values().length)])
                    .car(new Car("Car" + (i + 1L))).build();

            humans[i] = human;
        }

        return List.of(humans);
    }

    public void initCollectionManager(AuthToken token) {
        var adapter = new AuthAdapter("127.0.0.1", 8000);
        adapter.setToken(Optional.of(token));
        try {
            manager = new RemoteManager(adapter);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
