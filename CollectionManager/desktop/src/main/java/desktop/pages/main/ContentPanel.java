package desktop.pages.main;

import java.awt.CardLayout;

import javax.swing.JPanel;

import desktop.pages.main.table.TablePanel;
import desktop.pages.main.viz.VizPanel;

public class ContentPanel extends JPanel {
    private TablePanel table;

    private VizPanel viz;

    private CardLayout layout;

    public ContentPanel(TablePanel table, VizPanel viz) {
        this.table = table;
        this.viz = viz;
        init();
    }

    private void init() {
        layout = new CardLayout();
        setLayout(layout);

        add(table, "table");
        add(viz, "viz");
    }

    public void showTable() {
        layout.show(this, "table");
    }

    public void showViz() {
        layout.show(this, "viz");
    }
}
