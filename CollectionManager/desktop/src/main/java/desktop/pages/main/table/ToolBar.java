package desktop.pages.main.table;

import java.awt.FlowLayout;
import java.util.function.BiConsumer;

import javax.swing.JPanel;

public class ToolBar extends JPanel {
    public ToolBar(String[] columnNames, BiConsumer<String, String> filterCallback) {
        init(columnNames, filterCallback);
    }

    private void init(String[] columnNames, BiConsumer<String, String> filterCallback) {
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        // Filter
        var filter = new Filter(columnNames, filterCallback);
        add(filter);
    }
}
