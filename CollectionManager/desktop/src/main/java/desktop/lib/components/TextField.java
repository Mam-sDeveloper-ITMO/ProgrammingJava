package desktop.lib.components;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import lombok.Getter;

public class TextField extends JPanel {
    private JLabel label;

    @Getter
    private JTextField textField;

    public TextField(String labelText, int columns) {
        label = new JLabel(labelText);
        textField = new JTextField(columns);

        this.setLayout(new BorderLayout(0, 5));
        this.add(label, BorderLayout.NORTH);
        this.add(textField, BorderLayout.CENTER);
    }

    public void setLabel(String text) {
        label.setText(text);
    }
}
