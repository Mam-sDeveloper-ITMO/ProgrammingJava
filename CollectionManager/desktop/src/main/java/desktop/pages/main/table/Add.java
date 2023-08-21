package desktop.pages.main.table;

import java.awt.BorderLayout;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import desktop.App;
import models.Human;
import textlocale.text.TextSupplier;

public class Add extends JPanel {
    private TextSupplier ts = App.texts
        .getPackage("texts.main.table.toolbar.add")::getText;

    private JLabel label;

    private AddDialog dialog;

    public Add() {
        init();
    }

    private void init() {
        setLayout(new BorderLayout(0, 5));

        // Dialog
        dialog = new AddDialog();

        // Label
        label = new JLabel(ts.t("title"));
        add(label, BorderLayout.NORTH);

        // Button
        var addButton = new JButton(ts.t("addButton"));
        addButton.addActionListener(e -> dialog.setVisible(true));
        add(addButton, BorderLayout.CENTER);
    }

    public void setAddCallback(Consumer<Human> addCallback) {
        dialog.setAddCallback(addCallback);
    }
}
