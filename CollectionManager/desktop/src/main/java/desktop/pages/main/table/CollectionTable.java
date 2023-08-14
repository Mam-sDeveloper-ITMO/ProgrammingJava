package desktop.pages.main.table;

import java.awt.Color;
import java.awt.Component;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import models.Human;
import models.Mood;

public class CollectionTable extends JTable {
    private DefaultTableModel model;

    private TableRowSorter<DefaultTableModel> sorter;

    public CollectionTable(String[] columnNames, List<Human> initialData) {
        super();
        init(columnNames, initialData);
    }

    private void init(String[] columnNames, List<Human> initialData) {
        setSelectionBackground(Color.decode("#666666"));

        model = new TableModel(columnNames, 0);
        setModel(model);

        sorter = new TableRowSorter<DefaultTableModel>(model);
        setRowSorter(sorter);

        var moods = Mood.values();
        var moodComboBox = new JComboBox<Mood>(moods);
        getColumnModel().getColumn(10).setCellEditor(new DefaultCellEditor(moodComboBox));

        updateData(initialData);
    }

    public void updateData(List<Human> data) {
        model.setRowCount(0);

        for (Human human : data) {
            Object[] rowData = { human.getId(), human.getName(), human.getCoordinates().getX(),
                    human.getCoordinates().getY(), human.getCreationDate(), human.getRealHero(),
                    human.getHasToothpick(), human.getImpactSpeed(), human.getSoundtrackName(),
                    human.getMinutesOfWaiting(), human.getMood(), human.getCar() };
            model.addRow(rowData);
        }
        fitColumnsToContent();
    }

    public void filter(String column, String regex) {
        RowFilter<DefaultTableModel, Object> rf = null;
        try {
            rf = RowFilter.regexFilter(regex, model.findColumn(column));
        } catch (PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rf);
    }

    private void fitColumnsToContent() {
        for (int columnIndex = 0; columnIndex < this.getColumnCount(); columnIndex++) {
            TableColumn column = this.getColumnModel().getColumn(columnIndex);
            int preferredWidth = 0;

            // Calculate the preferred width based on column content
            for (int rowIndex = 0; rowIndex < this.getRowCount(); rowIndex++) {
                TableCellRenderer cellRenderer = this.getCellRenderer(rowIndex, columnIndex);
                Component cellComponent = this.prepareRenderer(cellRenderer, rowIndex, columnIndex);
                preferredWidth = Math.max(preferredWidth, cellComponent.getPreferredSize().width);
            }

            column.setPreferredWidth(preferredWidth);
        }
    }

    private class TableModel extends DefaultTableModel {
        public TableModel(String[] columnNames, int rowCount) {
            super(columnNames, rowCount);
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
            case 0:
                return Long.class;
            case 1:
                return String.class;
            case 2:
                return Float.class;
            case 3:
                return Float.class;
            case 4:
                return LocalDateTime.class;
            case 5:
                return Boolean.class;
            case 6:
                return Boolean.class;
            case 7:
                return Double.class;
            case 8:
                return String.class;
            case 9:
                return Long.class;
            case 10:
                return String.class;
            case 11:
                return String.class;
            default:
                return Object.class;
            }
        }
    }
}
