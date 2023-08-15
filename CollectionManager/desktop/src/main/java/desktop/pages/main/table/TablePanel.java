package desktop.pages.main.table;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import models.Human;

public class TablePanel extends JPanel {
    private Table table;

    private List<Human> data;

    private final String[] columnNames = { "ID", "Name", "X", "Y", "Create Time", "Real Hero", "Toothpick",
            "Impact Speed", "Soundtrack", "Waiting", "Mood", "Car" };

    public TablePanel(List<Human> initialData) {
        this.data = initialData;
        init();
    }

    private void init() {
        setLayout(new BorderLayout());

        // Table
        table = new Table(columnNames, data);
        var scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Filter field
        var toolBar = new ToolBar(columnNames, table::filter);
        add(toolBar, BorderLayout.NORTH);

        updateTableData(data);
    }

    public void updateTableData(List<Human> newData) {
        data = newData;
        table.updateData(data);
    }
}
