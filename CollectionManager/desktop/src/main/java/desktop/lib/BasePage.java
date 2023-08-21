package desktop.lib;

import javax.swing.JPanel;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class BasePage extends JPanel {
    @Getter
    private final String name;

    public void beforeShow() {
        this.removeAll();
    };

    public void afterShow() {
    };
}
