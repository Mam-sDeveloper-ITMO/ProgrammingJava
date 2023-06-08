package collections.service.dbmodels.converters;

import collections.service.dbmodels.HumanModel;
import humandeque.HumanDeque;
import models.Car;
import models.Coordinates;
import models.Human;
import models.Mood;

/**
 * Utility for converting {@code HumanMode} to {@code Human} and vice versa.
 *
 * @see Human
 * @see HumanModel
 */
public class HumanConverter {
    /**
     * Converts {@code HumanModel} to {@code Human}.
     *
     * @param humanModel {@code HumanModel} to convert
     * @return {@code Human} converted from {@code HumanModel}
     */
    public static Human toHuman(HumanModel humanModel) {
        Mood mood = Mood.valueOf(humanModel.getMood());
        Car car = new Car(humanModel.getCarName());
        Coordinates coordinates = new Coordinates(humanModel.getXCoordinate(), humanModel.getYCoordinate());

        Human human = Human.builder()
                .id(humanModel.getId())
                .name(humanModel.getName())
                .coordinates(coordinates)
                .creationDate(humanModel.getCreationDate())
                .realHero(humanModel.getRealHero())
                .hasToothpick(humanModel.getHasToothpick())
                .impactSpeed(humanModel.getImpactSpeed())
                .soundtrackName(humanModel.getSoundtrackName())
                .minutesOfWaiting(humanModel.getMinutesOfWaiting())
                .mood(mood)
                .car(car)
                .build();
        return human;
    }

    /**
     * Converts batch of {@code HumanModel} to {@code HumanDeque}.
     *
     * @param humanModels Batch of {@code HumanModel} to convert
     * @return {@code HumanDeque} converted from batch of {@code HumanModel}
     */
    public static HumanDeque toHumanDeque(Iterable<HumanModel> humanModels) {
        HumanDeque humanDeque = new HumanDeque();
        for (HumanModel humanModel : humanModels) {
            Human human = toHuman(humanModel);
            humanDeque.add(human);
        }
        return humanDeque;
    }

    /**
     * Converts {@code Human} to {@code HumanModel}.
     *
     * @param human        {@code Human} to convert
     * @param collectionId Id of collection to which {@code Human} belongs
     * @return {@code HumanModel} converted from {@code Human}
     */
    public static HumanModel toHumanModel(Human human, Integer collectionId) {
        HumanModel humanModel = new HumanModel(
                Long.valueOf(human.getId()).intValue(),
                collectionId,
                human.getName(),
                human.getCoordinates().getX(),
                human.getCoordinates().getY(),
                human.getCreationDate(),
                human.getRealHero(),
                human.getHasToothpick(),
                human.getImpactSpeed(),
                human.getSoundtrackName(),
                human.getMinutesOfWaiting(),
                human.getMood().toString(),
                human.getCar().getName());
        return humanModel;
    }

}
