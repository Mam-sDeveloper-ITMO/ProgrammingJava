package humandeque.manager.local;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import humandeque.HumanDeque;
import humandeque.manager.CollectionStorage;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import models.Car;
import models.Coordinates;
import models.Human;
import models.Mood;

/**
 * Provide storage of HumanDeque in csv file
 */
@RequiredArgsConstructor
public class CsvStorage implements CollectionStorage {
    @NonNull
    private String filePath;

    /**
     * Convert human to strings array
     */
    private String[] humanToStrings(Human human) {
        return new String[] {
                String.valueOf(human.getId()),
                human.getName(),
                String.valueOf(human.getCoordinates().getX()),
                String.valueOf(human.getCoordinates().getY()),
                String.valueOf(human.getCreationDate()),
                String.valueOf(human.getRealHero()),
                String.valueOf(human.getHasToothpick()),
                String.valueOf(human.getImpactSpeed()),
                human.getSoundtrackName(),
                String.valueOf(human.getMinutesOfWaiting()),
                String.valueOf(human.getMood()),
                human.getCar().getName()
        };
    }

    /**
     * Convert strings array to human
     */
    private Human stringsToHuman(String[] strings) {
        return Human.builder()
                .id(Long.parseLong(strings[0]))
                .name(strings[1])
                .coordinates(
                        Coordinates.builder()
                                .x(Float.parseFloat(strings[2]))
                                .y(Float.parseFloat(strings[3]))
                                .build())
                .creationDate(LocalDateTime.parse(strings[4]))
                .realHero(Boolean.parseBoolean(strings[5]))
                .hasToothpick(Boolean.parseBoolean(strings[6]))
                .impactSpeed(Double.parseDouble(strings[7]))
                .soundtrackName(strings[8])
                .minutesOfWaiting(Float.parseFloat(strings[9]))
                .mood(Mood.valueOf(strings[10]))
                .car(new Car(strings[11]))
                .build();
    }

    /**
     * Save collection to csv file
     */
    @Override
    public void save(HumanDeque collection) throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter(filePath));

        String[] header = new String[] {
                "id", "name", "x", "y", "creationDate", "realHero", "hasToothpick",
                "impactSpeed", "impactSpeed", "minutesOfWaiting", "mood", "carName"
        };
        writer.writeNext(header);

        String[] humanRow;
        for (Human human : collection) {
            humanRow = humanToStrings(human);
            writer.writeNext(humanRow);
        }
        writer.close();
    }

    /**
     * Load collection from csv file
     */
    @Override
    public HumanDeque load() throws IOException {
        CSVReader reader = new CSVReader(new FileReader(filePath));
        try {
            String[] nextLine;
            nextLine = reader.readNext(); // skip header

            Human human;
            HumanDeque collection = new HumanDeque();
            while ((nextLine = reader.readNext()) != null) {
                human = stringsToHuman(nextLine);
                collection.add(human);
            }
            return collection;
        } catch (CsvException e) {
            throw new IOException(e);
        }
    }
}
