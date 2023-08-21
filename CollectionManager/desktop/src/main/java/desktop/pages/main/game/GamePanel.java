package desktop.pages.main.game;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
    public GamePanel() {
        init();
    }

    private void init() {
        add(new JLabel("Game"));
    }
}
