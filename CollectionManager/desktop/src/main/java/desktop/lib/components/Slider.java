package desktop.lib.components;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import lombok.Getter;

public class Slider extends JPanel {
    private JLabel label;

    @Getter
    private JSlider slider;

    private JLabel valueLabel;

    private int from;

    private int to;

    private float ratio;

    @Getter
    private float value;

    public Slider(String text, int from, int to) {
        this.from = from;
        this.to = to;
        this.ratio = 1;
        init(text);
    }

    public Slider(String text, float from, float to, float ratio) {
        this.from = (int) (from * ratio);
        this.to = (int) (to * ratio);
        this.ratio = ratio;
        init(text);
    }

    private void init(String text) {
        setLayout(new BorderLayout(0, 5));

        label = new JLabel(text);
        add(label);

        // Slider and its value label
        var sliderContainer = new JPanel(new BorderLayout());

        // Slider
        slider = new JSlider(from, to);
        sliderContainer.add(slider);
        slider.addChangeListener(e -> {
            value = slider.getValue() / ratio;
            valueLabel.setText(String.valueOf(value));
        });

        // Value label
        valueLabel = new JLabel(String.valueOf(slider.getValue() / ratio));
        sliderContainer.add(valueLabel, BorderLayout.LINE_END);

        add(sliderContainer, BorderLayout.PAGE_END);
    }
}
