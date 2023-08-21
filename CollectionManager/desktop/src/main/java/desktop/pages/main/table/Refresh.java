package desktop.pages.main.table;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import desktop.App;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;
import lombok.Setter;
import textlocale.text.TextSupplier;

public class Refresh extends JPanel {
    private TextSupplier ts = App.texts
        .getPackage("texts.main.table.toolbar.refresh")::getText;

    private JLabel label;

    @Setter
    private Runnable refreshCallback;

    public Refresh() {
        init();
    }

    private void init() {
        setLayout(new BorderLayout(0, 5));

        // Label
        label = new JLabel(ts.t("title"));
        add(label, BorderLayout.NORTH);

        // Button
        var refreshIcon = IconFontSwing.buildIcon(FontAwesome.REFRESH, 15, Color.WHITE);
        var refreshButton = new JButton(refreshIcon);
        refreshButton.addActionListener(e -> new Thread(refreshCallback).start());
        add(refreshButton, BorderLayout.CENTER);

        setMaximumSize(getPreferredSize());
    }
}
