package desktop.pages.main.table;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import desktop.App;
import desktop.lib.components.CheckBox;
import desktop.lib.components.ComboBox;
import desktop.lib.components.Slider;
import desktop.lib.components.Spinner;
import desktop.lib.components.TextField;
import humandeque.manager.exceptions.ElementAlreadyExistsError;
import humandeque.manager.exceptions.ManipulationError;
import lombok.Setter;
import models.Car;
import models.Coordinates;
import models.Human;
import models.Mood;
import textlocale.text.TextSupplier;


public class AddDialog extends JDialog {
    private TextSupplier ts = App.texts
        .getPackage("texts.main.table.toolbar.add.dialog")::getText;

    private JLabel errorLabel;

    @Setter
    private Consumer<Human> addCallback;

    public AddDialog() {
        init();
    }

    private void init() {
        setTitle(ts.t("title"));

        setLayout(new GridBagLayout());

        var gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;

        // Name field
        var nameField = new TextField(ts.t("name"), 40);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(nameField, gbc);

        // Real hero and toothpick row
        gbc.gridwidth = 1;
        gbc.gridy++;

        var realHeroContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        var realHeroCheckBox = new CheckBox(ts.t("realHero"));
        realHeroContainer.add(realHeroCheckBox);
        gbc.gridx = 0;
        add(realHeroContainer, gbc);

        var toothpickContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        var toothpickCheckBox = new CheckBox(ts.t("toothpick"));
        toothpickContainer.add(toothpickCheckBox);
        gbc.gridx = 1;
        add(toothpickContainer, gbc);

        gbc.gridx = 0;

        // Coordinates
        var coordinatesLabel = new JLabel(ts.t("coordinates"));
        gbc.gridwidth = 2;
        gbc.gridy++;
        add(coordinatesLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;

        // x
        var xSpinner = new Spinner(ts.t("x"), 0D, null, null, 0.1D);
        gbc.gridx = 0;
        add(xSpinner, gbc);

        // y
        var ySpinner = new Spinner(ts.t("y"), 0D, null, null, 0.1D);
        gbc.gridx = 1;
        add(ySpinner, gbc);

        gbc.gridx = 0;

        // Impact speed slider
        var impactSpeedSlider = new Slider(ts.t("impactSpeed"), 0, 1, 100);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy++;
        add(impactSpeedSlider, gbc);

        // Soundtrack field
        var soundtrackNameField = new TextField(ts.t("soundtrack"), 40);
        gbc.gridwidth = 2;
        gbc.gridy++;
        add(soundtrackNameField, gbc);

        // Waiting slider
        var waitingSlider = new Slider(ts.t("waiting"), 0, 100);
        gbc.gridwidth = 2;
        gbc.gridy++;
        add(waitingSlider, gbc);

        // Mood combo box
        var moodComboBox = new ComboBox<>(ts.t("mood"), Mood.values());
        gbc.gridwidth = 2;
        gbc.gridy++;
        add(moodComboBox, gbc);

        // Car field
        var carNameField = new TextField(ts.t("car"), 40);
        gbc.gridwidth = 2;
        gbc.gridy++;
        add(carNameField, gbc);

        // Error label
        errorLabel = new JLabel(" ");
        errorLabel.setForeground(Color.RED);
        gbc.gridy++;
        add(errorLabel, gbc);

        // Buttons
        gbc.gridwidth = 1;

        var cancelButton = new JButton(ts.t("cancelButton"));
        cancelButton.addActionListener(e -> dispose());
        gbc.gridy++;
        add(cancelButton, gbc);

        var addButton = new JButton(ts.t("addButton"));
        addButton.addActionListener(e -> {
            new Thread(() -> {
                add(nameField.getText(),
                    xSpinner.getValue(),
                    ySpinner.getValue(),
                    realHeroCheckBox.isSelected(),
                    toothpickCheckBox.isSelected(),
                    impactSpeedSlider.getValue(),
                    soundtrackNameField.getText(),
                    waitingSlider.getValue(),
                    (Mood) moodComboBox.getSelectedItem(),
                    carNameField.getText());
            }).start();
        });
        gbc.gridx = 1;
        add(addButton, gbc);

        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void add(
        String name,
        Double x,
        Double y,
        boolean realHero,
        boolean toothpick,
        Float impactSpeed,
        String soundtrackName,
        Float waiting,
        Mood mood,
        String carName) {
        if (name.isEmpty()) {
            errorLabel.setText(ts.t("errors.name"));
            return;
        } else if (soundtrackName.isEmpty()) {
            errorLabel.setText(ts.t("errors.soundtrack"));
            return;
        } else if (carName.isEmpty()) {
            errorLabel.setText(ts.t("errors.car"));
            return;
        }

        // Add human
        var human = Human.builder()
            .name(name)
            .coordinates(new Coordinates(x.floatValue(), y.floatValue()))
            .realHero(realHero)
            .hasToothpick(toothpick)
            .impactSpeed(impactSpeed.doubleValue())
            .soundtrackName(soundtrackName)
            .minutesOfWaiting(waiting)
            .mood(mood)
            .car(new Car(carName))
            .build();

        try {
            App.context.getManager().add(human);
            JOptionPane.showConfirmDialog(
                null,
                ts.t("success"),
                ts.t("successTitle"),
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE);
            addCallback.accept(human);
        } catch (ManipulationError | ElementAlreadyExistsError e) {
            JOptionPane.showConfirmDialog(
                null,
                ts.t("errors.add", e.getMessage()),
                ts.t("errors.addTitle"),
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.ERROR_MESSAGE);
        } finally {
            dispose();
        }
    }
}
