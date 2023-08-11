package desktop.lib.components;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import lombok.Getter;

public class PasswordField extends JPanel {
    private JLabel label;

    @Getter
    private JPasswordField passwordField;

    public PasswordField(String labelText, int columns) {
        label = new JLabel(labelText);
        passwordField = new JPasswordField(columns);

        this.setLayout(new BorderLayout(0, 5));
        this.add(label, BorderLayout.NORTH);
        this.add(passwordField, BorderLayout.CENTER);
    }

    public void setLabel(String text) {
        label.setText(text);
    }
}
