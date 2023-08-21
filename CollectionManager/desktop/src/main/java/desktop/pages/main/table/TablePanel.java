package desktop.pages.main.table;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import models.Human;

public class TablePanel extends JPanel {
    private Table table;

    private final String[] columnNames = { "ID", "Name", "X", "Y", "Create Time",
        "Real Hero", "Toothpick",
        "Impact Speed", "Soundtrack", "Waiting", "Mood", "Car" };

    private Runnable updateCallback;

    public TablePanel(List<Human> initialData, Runnable updateCallback) {
        this.updateCallback = updateCallback;
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
        toolBar.setSaveCallback(table::saveUpdatedHumans);
        table.setSelectCallback(toolBar::notifySelection);
        toolBar.setDeleteCallback(table::deleteSelectedHumans);
        toolBar.setAddCallback(table::addHuman);
        toolBar.setRefreshCallback(updateCallback);
        add(toolBar, BorderLayout.NORTH);

        updateTableData(data);
    }

    public void updateTableData(List<Human> newData) {
        table.updateData(newData);
    }
}
