package desktop.pages.main.lang;

import java.awt.Component;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import lombok.Setter;

public class LangSelect extends JPanel {
    private List<LangItem> items;

    @Setter
    private Consumer<String> langSelectHandler;

    public LangSelect(List<LangItem> items) {
        this.items = items;
        init();
    }

    private void init() {
        var langsComboBox = new JComboBox<>(items.toArray(new LangItem[0]));
        langsComboBox.addActionListener(e -> {
            var item = (LangItem) langsComboBox.getSelectedItem();
            langSelectHandler.accept(item.getLangCode());
        });
        langsComboBox.setRenderer(new LangItemRenderer());
        add(langsComboBox);
    }

    class LangItemRenderer extends BasicComboBoxRenderer {
        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (value instanceof LangItem) {
                LangItem langItem = (LangItem) value;
                setText(langItem.getLang()); // Display the language name
                setIcon(langItem.getIcon()); // Display the language icon
            }

            return this;
        }
    }
}
