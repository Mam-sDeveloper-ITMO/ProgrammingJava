package desktop.pages.main;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import desktop.App;
import desktop.lib.BasePage;
import desktop.pages.main.table.TablePanel;
import humandeque.HumanDeque;

public class MainPage extends BasePage {
    private TablePanel table;

    private HumanDeque collection;

    public MainPage() {
        super("main");
    }

    public void beforeShow() {
        setLayout(new BorderLayout());

        // Top bar
        var topBar = new TopBar(App.context.getUsername());
        add(topBar, BorderLayout.NORTH);

        // Collection table
        table = new TablePanel(List.copyOf(App.context.getHumans()),
            this::updateCollection);
        add(table, BorderLayout.CENTER);

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
