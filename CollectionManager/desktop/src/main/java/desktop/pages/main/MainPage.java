package desktop.pages.main;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import desktop.App;
import desktop.lib.BasePage;
import desktop.pages.main.table.TablePanel;
import desktop.pages.main.viz.VizPanel;
import humandeque.HumanDeque;

public class MainPage extends BasePage {
    private HumanDeque collection;

    private TablePanel table;

    private VizPanel viz;

    private ContentPanel content;

    public MainPage() {
        super("main");
    }

    public void beforeShow() {
        super.beforeShow();

        setLayout(new BorderLayout(0, 10));

        // Top bar
        var topBar = new TopBar(App.context.getUsername());
        add(topBar, BorderLayout.NORTH);

        // Collection table
        table = new TablePanel(
            List.copyOf(App.context.getHumans()),
            this::updateCollection,
            this::refreshCollection);

        // Humans visualization
        viz = new VizPanel(List.copyOf(App.context.getHumans()));

        // Content
        content = new ContentPanel(table, viz);
        content.showTable();
        topBar.setOpenTable(content::showTable);
        topBar.setOpenViz(content::showViz);
        add(content);

        // Update collection data
        refreshCollection();
    }

    private void updateCollection() {
        try {
            App.context.getManager().load();
        } catch (Exception e) {

        }
        refreshCollection();
    }

    private void refreshCollection() {
        collection = App.context.getManager().getCollection();
        table.setHumans(new ArrayList<>(collection));
        viz.setHumans(new ArrayList<>(collection));
    }
}
