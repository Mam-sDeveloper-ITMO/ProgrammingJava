package desktop.lib.components;

import java.awt.BorderLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import lombok.Getter;

public class ComboBox<T> extends JPanel {
    @Getter
    private JComboBox<T> comboBox;

    public ComboBox(String text, T[] items) {
        init(text, items);
    }

    private void init(String text, T[] items) {
        setLayout(new BorderLayout(0, 5));

        var label = new JLabel(text);
        add(label, BorderLayout.NORTH);

        comboBox = new JComboBox<>(items);
        add(comboBox, BorderLayout.CENTER);
    }

    public T getSelectedItem() {
        return (T) comboBox.getSelectedItem();
    }
}
