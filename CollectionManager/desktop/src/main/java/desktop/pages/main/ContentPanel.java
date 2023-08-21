package desktop.pages.main;

import java.awt.CardLayout;

import javax.swing.JPanel;

import desktop.pages.main.game.GamePanel;
import desktop.pages.main.table.TablePanel;

public class ContentPanel extends JPanel {
    private TablePanel table;

    private GamePanel game;

    private CardLayout layout;

    public ContentPanel(TablePanel table, GamePanel game) {
        this.table = table;
        this.game = game;
        init();
    }

    private void init() {
        layout = new CardLayout();
        setLayout(layout);

        add(table, "table");
        add(game, "game");
    }

    public void showTable() {
        layout.show(this, "table");
    }

    public void showGame() {
        layout.show(this, "game");
    }
}
