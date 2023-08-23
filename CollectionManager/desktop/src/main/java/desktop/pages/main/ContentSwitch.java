package desktop.pages.main;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import desktop.App;
import lombok.Setter;
import textlocale.text.TextSupplier;

public class ContentSwitch extends JPanel {
    private TextSupplier ts = App.texts
        .getPackage("texts.main.topbar.contentSwitch")::getText;

    @Setter
    private Runnable showTable;

    @Setter
    private Runnable showViz;

    private JButton vizButton;

    private JButton tableButton;

    public ContentSwitch() {
        init();
    }

    private void init() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        // Table button
        tableButton = new JButton(ts.t("openTable"));
        tableButton.addActionListener(e -> {
            showTable.run();
            tableButton.setEnabled(false);
            vizButton.setEnabled(true);
        });
        tableButton.setEnabled(false);
        add(tableButton);

        // Viz button
        vizButton = new JButton(ts.t("openViz"));
        vizButton.addActionListener(e -> {
            showViz.run();
            vizButton.setEnabled(false);
            tableButton.setEnabled(true);
        });
        add(vizButton);
    }
}
