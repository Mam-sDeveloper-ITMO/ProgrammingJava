package desktop.pages.main.table;

import java.util.List;

import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import models.Human;

public class CollectionTable extends JTable {
    private DefaultTableModel model;

    private TableRowSorter<TableModel> sorter;

    public CollectionTable(String[] columnNames, List<Human> initialData) {
        super();
        init(columnNames, initialData);
    }

    private void init(String[] columnNames, List<Human> initialData) {
        model = new DefaultTableModel(columnNames, 0);
        setModel(model);

        sorter = new TableRowSorter<TableModel>((TableModel) getModel());
        setRowSorter(sorter);

        // setAutoCreateRowSorter(true);
        setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        updateData(initialData);
    }

    public void updateData(List<Human> data) {
        model.setRowCount(0);

        for (Human human : data) {
            Object[] rowData = { human.getId(), human.getName(), human.getCoordinates(), human.getCreationDate(),
                    human.getRealHero(), human.getHasToothpick(), human.getImpactSpeed(), human.getSoundtrackName(),
                    human.getMinutesOfWaiting(), human.getMood(), human.getCar() };
            model.addRow(rowData);
        }
    }

    public void filter(String column, String regex) {
        RowFilter<TableModel, Object> rf = null;
        try {
            rf = RowFilter.regexFilter(regex, model.findColumn(column));
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rf);
    }
}
