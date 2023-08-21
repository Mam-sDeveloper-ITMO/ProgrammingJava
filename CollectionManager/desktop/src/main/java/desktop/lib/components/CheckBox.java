package desktop.lib.components;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import lombok.Getter;

public class CheckBox extends JPanel {
    private JLabel label;

    @Getter
    private JCheckBox checkBox;

    public CheckBox(String text) {
        init(text);
    }

    private void init(String text) {
        checkBox = new JCheckBox();
        add(checkBox);
        label = new JLabel(text);
        add(label);
    }

    public Boolean isSelected() {
        return checkBox.isSelected();
    }
}
