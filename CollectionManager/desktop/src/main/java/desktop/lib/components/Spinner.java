package desktop.lib.components;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import lombok.Getter;
import lombok.Setter;

public class Spinner extends JPanel {
    @Getter
    private JSpinner spinner;

    @Setter
    @Getter
    private Double min = null;

    @Setter
    @Getter
    private Double max = null;

    @Setter
    @Getter
    private Double step = Double.valueOf(1);

    public Spinner(String text) {
        init(text, Double.valueOf(0));
    }

    public Spinner(String text, Double value, Double min, Double max, Double step) {
        this.min = min;
        this.max = max;
        this.step = step;
        init(text, value);
    }

    private void init(String text, Double value) {
        setLayout(new BorderLayout(5, 0));

        var label = new JLabel(text);
        add(label, BorderLayout.WEST);

        spinner = new JSpinner(new SpinnerNumberModel(value, min, max, step));
        spinner.setValue(0);
        add(spinner, BorderLayout.CENTER);
    }

    public Double getValue() {
        return Double.valueOf(spinner.getValue().toString());
    }
}
