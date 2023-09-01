package desktop.pages.main;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import desktop.App;
import desktop.lib.BasePage;
import desktop.pages.main.table.TablePanel;
import desktop.pages.main.viz.HumanSprite;
import desktop.pages.main.viz.VizPanel;
import humandeque.HumanDeque;

public class MainPage extends BasePage {
    private HumanDeque collection;

    private TablePanel table;

    private VizPanel viz;

    private ContentPanel content;

    public MainPage() {
        super("main");
    }

    public void beforeShow() {
        super.beforeShow();

        setLayout(new BorderLayout(0, 10));

        // Top bar
        var topBar = new TopBar(App.context.getUsername());
        add(topBar, BorderLayout.NORTH);

        // Collection table
        table = new TablePanel(
            List.copyOf(App.context.getHumans()),
            this::updateCollection,
            this::refreshCollection);

        // Humans visualization
        viz = new VizPanel(List.copyOf(App.context.getHumans()));
        viz.setOnSpriteClicked(this::onSpriteClicked);

        // Content
        content = new ContentPanel(table, viz);
        content.showTable();
        topBar.setOpenTable(content::showTable);
        topBar.setOpenViz(content::showViz);
        topBar.setLangSelectHandler((lang) -> {
            App.setLang(lang);
            this.setVisible(false);
            this.beforeShow();
            this.setVisible(true);
            this.requestFocus();
        });
        add(content);

        // Update collection data
        refreshCollection();
    }

    private void updateCollection() {
        try {
            App.context.getManager().load();
        } catch (Exception e) {

        }
        refreshCollection();
    }

    private void refreshCollection() {
        collection = App.context.getManager().getCollection();
        table.setHumans(new ArrayList<>(collection));
        viz.setHumans(new ArrayList<>(collection));
    }

    private void onSpriteClicked(HumanSprite sprite) {
        for (var human : collection) {
            if (human.getId() == sprite.getId()) {
                JOptionPane.showMessageDialog(
                    this,
                    human.toString(),
                    human.getName(),
                    JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
