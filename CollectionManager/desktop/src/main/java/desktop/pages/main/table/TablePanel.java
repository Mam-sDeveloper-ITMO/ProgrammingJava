package desktop.pages.main.table;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import desktop.App;
import models.Human;
import textlocale.text.TextSupplier;

public class TablePanel extends JPanel {
    private TextSupplier ts = App.texts.getPackage("texts.main.table")::getText;

    private Table table;

    private final String[] columnNames = initColumns();

    private Runnable updateCallback;

    private Runnable refreshCallback;

    public TablePanel(List<Human> initialData, Runnable updateCallback,
        Runnable refreshCallback) {
        this.updateCallback = updateCallback;
        this.refreshCallback = refreshCallback;
        init(initialData);
    }

    private void init(List<Human> data) {
        setLayout(new BorderLayout(0, 10));

        // Table
        table = new Table(columnNames, data);
        var scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Filter field
        var toolBar = new ToolBar(columnNames, table::filter);
        table.setUpdateCallback(toolBar::notifyUpdate);
        toolBar.setSaveCallback(humans -> {
            refreshCallback.run();
        });
        table.setSelectCallback(toolBar::notifySelection);
        toolBar.setDeleteCallback(humans -> {
            refreshCallback.run();
        });
        toolBar.setAddCallback(human -> {
            refreshCallback.run();
        });
        toolBar.setRefreshCallback(updateCallback);
        add(toolBar, BorderLayout.NORTH);

        table.updateData(data);
    }

    public void setHumans(List<Human> humans) {
        table.updateData(humans);
    }

    private String[] initColumns() {
        return new String[] {
            ts.t("columns.id"),
            ts.t("columns.name"),
            ts.t("columns.x"),
            ts.t("columns.y"),
            ts.t("columns.creationDate"),
            ts.t("columns.realHero"),
            ts.t("columns.toothpick"),
            ts.t("columns.impactSpeed"),
            ts.t("columns.soundtrack"),
            ts.t("columns.waiting"),
            ts.t("columns.mood"),
            ts.t("columns.car")
        };
    }
}
