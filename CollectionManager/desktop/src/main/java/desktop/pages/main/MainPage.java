package desktop.pages.main;

import java.awt.BorderLayout;
import java.util.List;

import desktop.App;
import desktop.lib.BasePage;
import desktop.pages.main.table.TablePanel;
import humandeque.HumanDeque;

public class MainPage extends BasePage {
    private TablePanel collectionTable;

    private HumanDeque collection;

    public MainPage() {
        super("main");
    }

    public void beforeShow() {
        updateCollection();

        setLayout(new BorderLayout());

        // Top bar
        var topBar = new TopBar(App.context.getUsername());
        add(topBar, BorderLayout.NORTH);

        // Collection table
        collectionTable = new TablePanel(List.copyOf(collection));
        add(collectionTable, BorderLayout.CENTER);
    }

    private void updateCollection() {
        try {
            App.context.getManager().load();
            collection = App.context.getManager().getCollection();
        } catch (Exception e) {

        }
    }
}
