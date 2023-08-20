package desktop.pages.main.table;

import java.awt.Color;
import java.awt.Component;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.regex.PatternSyntaxException;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import com.github.lgooddatepicker.tableeditors.DateTimeTableEditor;

import lombok.Setter;
import models.Car;
import models.Human;
import models.Mood;

public class Table extends JTable {
    private DefaultTableModel model;

    private TableRowSorter<DefaultTableModel> sorter;

    private List<Human> data;

    private Set<Human> updatedData = new HashSet<>();

    @Setter
    private Consumer<Set<Human>> selectCallback;

    @Setter
    private Consumer<Set<Human>> updateCallback;

    public Table(String[] columnNames, List<Human> initialData) {
        super();
        this.data = initialData;
        init(columnNames, initialData);
    }

    public void updateData(List<Human> data) {
        this.data = data;
        this.updatedData = new HashSet<>();

        model.setRowCount(0);
        for (Human human : data) {
            Object[] rowData = {
                human.getId(),
                human.getName(),
                human.getCoordinates().getX(),
                human.getCoordinates().getY(),
                human.getCreationDate(),
                human.getRealHero(),
                human.getHasToothpick(),
                human.getImpactSpeed(),
                human.getSoundtrackName(),
                human.getMinutesOfWaiting(),
                human.getMood(),
                human.getCar().getName()
            };
            model.addRow(rowData);
        }
        fitColumnsToContent();
    }

    public void saveUpdatedHumans(List<Human> humans) {
        humans.forEach(human -> {
            data.removeIf(h -> h.getId() == human.getId());
            data.add(human);
        });
        updateData(data);
    }

    public void deleteSelectedHumans(List<Human> humans) {
        humans.forEach(human -> {
            data.removeIf(h -> h.getId() == human.getId());
        });
        updateData(data);
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

    private void init(String[] columnNames, List<Human> initialData) {
        setSelectionBackground(Color.decode("#666666"));

        model = new TableModel(columnNames, 0);
        setModel(model);

        sorter = new TableRowSorter<DefaultTableModel>(model);
        setRowSorter(sorter);

        // Use custom editor for datetime
        setDefaultRenderer(LocalDateTime.class, new DateTimeTableEditor());
        setDefaultEditor(LocalDateTime.class, new DateTimeTableEditor());

        // Show ComboBox for mood column
        var moodsComboBox = new JComboBox<Mood>(Mood.values());
        getColumnModel().getColumn(10)
            .setCellEditor(new DefaultCellEditor(moodsComboBox));

        // Show only name in car column
        getColumnModel().getColumn(11).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public void setValue(Object value) {
                if (value instanceof String) {
                    setText((String) value);
                } else if (value instanceof Car) {
                    setText(((Car) value).getName());
                } else {
                    setText("");
                }
            }
        });

        // Set up selection handler
        getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                int[] selectedRows = getSelectedRows();
                Set<Human> selectedHumans = new HashSet<>();
                for (int selectedRow : selectedRows) {
                    int modelRow = convertRowIndexToModel(selectedRow);
                    selectedHumans.add(data.get(modelRow));
                }
                selectCallback.accept(selectedHumans);
            }
        });

        updateData(initialData);
    }

    private Human getUpdatedHuman(Human originHuman, int changedColumn, Object value) {
        var builder = originHuman.toBuilder();
        var coordinates = originHuman.getCoordinates();
        var car = originHuman.getCar();
        switch (changedColumn) {
        case 0:
            builder.id((Long) value);
            break;
        case 1:
            builder.name((String) value);
            break;
        case 2:
            coordinates.setX((Float) value);
            builder.coordinates(coordinates);
            break;
        case 3:
            coordinates.setX((Float) value);
            builder.coordinates(coordinates);
            break;
        case 4:
            builder.creationDate((LocalDateTime) value);
            break;
        case 5:
            builder.realHero((Boolean) value);
            break;
        case 6:
            builder.hasToothpick((Boolean) value);
            break;
        case 7:
            builder.impactSpeed((Double) value);
            break;
        case 8:
            builder.soundtrackName((String) value);
            break;
        case 9:
            builder.minutesOfWaiting((Float) value);
            break;
        case 10:
            builder.mood((Mood) value);
            break;
        case 11:
            car.setName((String) value);
            builder.car(car);
            break;
        default:
            break;
        }
        return builder.build();
    }

    private void fitColumnsToContent() {
        for (int columnIndex = 0; columnIndex < this.getColumnCount(); columnIndex++) {
            TableColumn column = this.getColumnModel().getColumn(columnIndex);
            int preferredWidth = 0;

            // Calculate the preferred width based on column content
            for (int rowIndex = 0; rowIndex < this.getRowCount(); rowIndex++) {
                TableCellRenderer cellRenderer = this.getCellRenderer(rowIndex,
                    columnIndex);
                Component cellComponent = this.prepareRenderer(cellRenderer, rowIndex,
                    columnIndex);
                preferredWidth = Math.max(preferredWidth,
                    cellComponent.getPreferredSize().width);
            }

            column.setPreferredWidth(preferredWidth);
        }
    }

    class TableModel extends DefaultTableModel {
        public TableModel(String[] columnNames, int rowCount) {
            super(columnNames, rowCount);
            this.addTableModelListener(new TableModelListener() {
                @Override
                public void tableChanged(TableModelEvent e) {
                    int row = e.getFirstRow();
                    if (row < 0) {
                        return;
                    }

                    var originalHuman = data.get(row);
                    var humanId = originalHuman.getId();

                    int column = e.getColumn();
                    if (column < 0) {
                        return;
                    }

                    Object value = null;
                    try {
                        value = model.getValueAt(row, column);
                    } catch (Exception ex) {
                        return;
                    }

                    // Check if human in row was modified earlier
                    // and use modified version as original
                    var modifiedOriginal = updatedData.stream()
                        .filter(human -> human.getId() == humanId)
                        .findFirst();

                    if (modifiedOriginal.isPresent()) {
                        originalHuman = modifiedOriginal.get();
                    }

                    var updatedHuman = getUpdatedHuman(originalHuman, column, value);
                    updatedData.removeIf(human -> human.getId() == humanId);
                    if (!data.contains(updatedHuman)) {
                        updatedData.add(updatedHuman);
                    }
                    updateCallback.accept(updatedData);
                }
            });
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
            case 0: // id
                return Long.class;
            case 1: // name
                return String.class;
            case 2: // x
                return Float.class;
            case 3: // y
                return Float.class;
            case 4: // create date
                return LocalDateTime.class;
            case 5: // real hero
                return Boolean.class;
            case 6: // has toothpick
                return Boolean.class;
            case 7: // impact speed
                return Double.class;
            case 8: // soundtrack name
                return String.class;
            case 9: // minutes of waiting
                return Float.class;
            case 10: // mood
                return String.class;
            case 11: // car
                return String.class;
            default:
                return Object.class;
            }
        }
    }
}
