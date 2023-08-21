package desktop.pages.main;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JSeparator;

import desktop.App;
import desktop.lib.BasePage;
import desktop.pages.main.game.GamePanel;
import desktop.pages.main.table.TablePanel;
import humandeque.HumanDeque;

public class MainPage extends BasePage {
    private HumanDeque collection;

    private TablePanel table;

    private GamePanel game;

    private ContentPanel content;

    public MainPage() {
        super("main");
    }

    public void beforeShow() {
        setLayout(new BorderLayout(0, 10));

        // Top bar
        var topBar = new TopBar(App.context.getUsername());
        add(topBar, BorderLayout.NORTH);

        // Collection table
        table = new TablePanel(List.copyOf(App.context.getHumans()),
            this::updateCollection);

        // Waiters game
        game = new GamePanel();

        // Content
        content = new ContentPanel(table, game);
        content.showTable();
        topBar.setOpenTable(content::showTable);
        topBar.setOpenGame(content::showGame);
        add(content);

        updateCollection();
    }

    private void updateCollection() {
        try {
            App.context.getManager().load();
            collection = App.context.getManager().getCollection();
            table.updateTableData(new ArrayList<>(collection));
        } catch (Exception e) {

        }
    }
}
