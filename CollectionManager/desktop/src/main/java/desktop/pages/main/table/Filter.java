package desktop.pages.main.table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.function.BiConsumer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import desktop.App;
import desktop.lib.components.TextField;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;
import textlocale.text.TextSupplier;

public class Filter extends JPanel {
    private TextSupplier ts = App.texts.getPackage("texts.main.table.toolbar.filter")::getText;

    private JComboBox<String> selectComboBox;

    private TextField filterField;

    private String[] columnNames;

    private BiConsumer<String, String> filterCallback;

    public Filter(String[] columnNames, BiConsumer<String, String> filterCallback) {
        this.columnNames = columnNames;
        this.filterCallback = filterCallback;
        init();
    }

    private void init() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        // Column select
        var selectPanel = new JPanel(new BorderLayout(0, 5));
        var selectLabel = new JLabel(ts.t("columnSelect"));
        selectPanel.add(selectLabel, BorderLayout.NORTH);
        selectComboBox = new JComboBox<>(this.columnNames);
        selectPanel.add(selectComboBox, BorderLayout.CENTER);
        add(selectPanel);
        add(Box.createHorizontalStrut(10));

        // Filter field
        filterField = new TextField(ts.t("searchRegex"), 25);
        add(filterField);
        add(Box.createHorizontalStrut(10));

        // Filter button
        var searchBtnPanel = new JPanel(new BorderLayout(0, 5));
        searchBtnPanel.add(new JLabel(" "), BorderLayout.NORTH); // dumb alignment
        var searchIcon = IconFontSwing.buildIcon(FontAwesome.SEARCH, 15, Color.WHITE);
        var searchButton = new JButton(searchIcon);
        searchButton.addActionListener(e -> {
            search();
        });
        searchBtnPanel.add(searchButton, BorderLayout.CENTER);
        add(searchBtnPanel);
        add(Box.createHorizontalStrut(10));

        // Reset button
        var resetBtnPanel = new JPanel(new BorderLayout(0, 5));
        resetBtnPanel.add(new JLabel(" "), BorderLayout.NORTH); // dumb alignment
        var resetIcon = IconFontSwing.buildIcon(FontAwesome.TIMES, 15, Color.WHITE);
        var resetButton = new JButton(resetIcon);
        resetButton.addActionListener(e -> {
            filterField.getTextField().setText("");
            search();
        });
        resetBtnPanel.add(resetButton, BorderLayout.CENTER);
        add(resetBtnPanel);
    }

    private void search() {
        var selectedColumn = (String) selectComboBox.getSelectedItem();
        var filterText = filterField.getTextField().getText();
        filterCallback.accept(selectedColumn, filterText);
    }
}
