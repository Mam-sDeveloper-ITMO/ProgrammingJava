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
        setLayout(new BorderLayout(0, 10));

        // Top bar
        var topBar = new TopBar(App.context.getUsername());
        add(topBar, BorderLayout.NORTH);

        // Collection table
        table = new TablePanel(List.copyOf(App.context.getHumans()),
            this::updateCollection);

        // Humans vizualization
        viz = new VizPanel();

        // Content
        content = new ContentPanel(table, viz);
        content.showTable();
        topBar.setOpenTable(content::showTable);
        topBar.setOpenViz(content::showViz);
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
