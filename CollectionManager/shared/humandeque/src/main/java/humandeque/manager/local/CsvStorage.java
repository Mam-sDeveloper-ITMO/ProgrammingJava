package humandeque.manager.local;

import java.io.BufferedWriter;
import java.io.FileWriter;

import humandeque.HumanDeque;
import humandeque.manager.CollectionStorage;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Provide storage of HumanDeque in csv file
 */
@RequiredArgsConstructor
public class CsvStorage implements CollectionStorage {
    @NonNull
    private String filePath;

    /**
     * Save collection to csv file
     */
    @Override
    public void save(HumanDeque collection) {
        Writer writer = new BufferedWriter(new FileWriter(filePath));
    }

    /**
     * Load collection from csv file
     */
    @Override
    public HumanDeque load() {
        throw new UnsupportedOperationException("Unimplemented method 'load'");
    }
}
