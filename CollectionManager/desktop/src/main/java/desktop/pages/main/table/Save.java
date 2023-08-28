package desktop.pages.main.table;

import java.awt.BorderLayout;
import java.util.ArrayList;
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

public class Save extends JPanel {
    private TextSupplier ts = App.texts.getPackage("texts.main.table.toolbar.save")::getText;

    private JLabel label;

    private JButton saveButton;

    private Set<Human> updatedHumans;

    @Setter
    private Consumer<List<Human>> saveCallback;

    public Save() {
        init();
    }

    private void init() {
        setLayout(new BorderLayout(0, 5));

        // Label
        label = new JLabel(ts.t("savedMessage"));
        add(label, BorderLayout.NORTH);

        // Button
        saveButton = new JButton(ts.t("saveButton"));
        saveButton.setEnabled(false);
        saveButton.addActionListener(e -> new Thread(this::save).start());
        add(saveButton, BorderLayout.CENTER);
    }

    public void notifyUpdate(Set<Human> updatedHumans) {
        if (updatedHumans.isEmpty()) {
            label.setText(ts.t("savedMessage"));
            saveButton.setEnabled(false);
        } else {
            label.setText(ts.t("updatedMessage"));
            saveButton.setEnabled(true);
        }
        this.updatedHumans = updatedHumans;
    }

    private void save() {
        var counter = 0;
        label.setText(ts.t("savingMessage", counter, updatedHumans.size()));
        for (Human human : updatedHumans) {
            try {
                App.context.getManager().update(human);
            } catch (ManipulationError | ElementNotExistsError e) {
                e.printStackTrace();
            }
            counter++;
            label.setText(ts.t("savingMessage", counter, updatedHumans.size()));
        }
        saveCallback.accept(new ArrayList<>(updatedHumans));
        label.setText(ts.t("savedMessage"));
        saveButton.setEnabled(false);
    }
}
