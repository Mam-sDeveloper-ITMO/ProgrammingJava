package desktop.pages.main.table;

import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import models.Human;

public class ToolBar extends JPanel {
    private Save save;

    private Delete delete;

    private Add add;

    public ToolBar(String[] columnNames, BiConsumer<String, String> filterCallback) {
        init(columnNames, filterCallback);
    }

    private void init(String[] columnNames, BiConsumer<String, String> filterCallback) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        // Filter
        var filter = new Filter(columnNames, filterCallback);
        add(filter);

        addSeparator();

        // Add button
        add = new Add();
        add(add);

        addSeparator();

        // Delete button
        delete = new Delete();
        add(delete);

        addSeparator();

        // Save button
        save = new Save();
        add(save);
    }

    private void addSeparator() {
        add(Box.createHorizontalStrut(10));
        add(new JSeparator(JSeparator.VERTICAL));
        add(Box.createHorizontalStrut(10));
    }

    public void notifyUpdate(Set<Human> updatedHumans) {
        save.notifyUpdate(updatedHumans);
    }

    public void notifySelection(Set<Human> selected) {
        delete.notifySelection(selected);
    }

    public void setSaveCallback(Consumer<List<Human>> saveCallback) {
        save.setSaveCallback(saveCallback);
    }

    public void setDeleteCallback(Consumer<List<Human>> deleteCallback) {
        delete.setDeleteCallback(deleteCallback);
    }

    public void setAddCallback(Consumer<Human> addCallback) {
        add.setAddCallback(addCallback);
    }
}
