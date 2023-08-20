package desktop.pages.main.table;

import java.awt.BorderLayout;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import desktop.App;
import humandeque.manager.exceptions.ElementNotExistsError;
import humandeque.manager.exceptions.ManipulationError;
import lombok.Setter;
import models.Human;
import textlocale.text.TextSupplier;

public class Delete extends JPanel {
    private TextSupplier ts = App.texts
        .getPackage("texts.main.table.toolbar.delete")::getText;

    private JLabel label;

    private JButton deleteButton;

    private Set<Human> selectedHumans;

    @Setter
    private Consumer<List<Human>> deleteCallback;

    public Delete() {
        init();
    }

    private void init() {
        setLayout(new BorderLayout(0, 5));

        // Label
        label = new JLabel(ts.t("noItemsSelected"));
        add(label, BorderLayout.NORTH);

        // Button
        deleteButton = new JButton(ts.t("deleteButton"));
        deleteButton.addActionListener(e -> new Thread(this::delete).start());
        deleteButton.setEnabled(false);
        add(deleteButton, BorderLayout.CENTER);
    }

    public void notifySelection(Set<Human> selected) {
        if (selected.isEmpty()) {
            label.setText(ts.t("noItemsSelected"));
            deleteButton.setEnabled(false);
        } else {
            label.setText(ts.t("itemsSelected", selected.size()));
            deleteButton.setEnabled(true);
        }
        selectedHumans = selected;
    }

    private void delete() {
        var counter = 0;
        label.setText(ts.t("deletingItems", counter, selectedHumans.size()));
        for (Human human : selectedHumans) {
            try {
                App.context.getManager().remove(human.getId());
            } catch (ManipulationError | ElementNotExistsError e) {
                e.printStackTrace();
            }
            counter++;
            label.setText(ts.t("deletingItems", counter, selectedHumans.size()));
        }
        deleteCallback.accept(List.copyOf(selectedHumans));
        label.setText(ts.t("noItemsSelected"));
        deleteButton.setEnabled(false);
    }
}
