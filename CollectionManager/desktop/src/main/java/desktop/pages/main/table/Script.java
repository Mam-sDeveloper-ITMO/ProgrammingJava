package desktop.pages.main.table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

import desktop.App;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;
import lombok.Setter;
import textlocale.text.TextSupplier;

public class Script extends JPanel {
    private TextSupplier ts = App.texts
        .getPackage("texts.main.table.toolbar.script")::getText;

    private JLabel label;

    @Setter
    private Runnable finishHandler;

    public Script() {
        init();
    }

    private void init() {
        setLayout(new BorderLayout(0, 5));

        // Label
        label = new JLabel(ts.t("title"));
        add(label, BorderLayout.NORTH);

        // Button
        var openScriptIcon = IconFontSwing.buildIcon(FontAwesome.FILE_TEXT, 15,
            Color.WHITE);
        var openScriptButton = new JButton(openScriptIcon);
        openScriptButton.addActionListener((e) -> openScript());
        add(openScriptButton, BorderLayout.CENTER);

        setMaximumSize(getPreferredSize());
    }

    private void openScript() {
        var fc = new JFileChooser();
        fc.setFileFilter(new NekoFileFilter());

        int result = fc.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            var file = fc.getSelectedFile();
            var dialog = new ScriptDialog(file, this.finishHandler);
            dialog.setVisible(true);
        }
    }

    // Create a FileFilter to filter specific file extensions
    private class NekoFileFilter extends FileFilter {
        @Override
        public boolean accept(File file) {
            if (file.isDirectory()) {
                return true; // Allow directories to be displayed
            }

            String extension = getExtension(file.getName());
            if (extension != null) {
                // Specify the allowed file extensions here
                return extension.equals("neko");
            }

            return false;
        }

        @Override
        public String getDescription() {
            // Description for the file filter
            return "Script files (*.neko)";
        }

        // Helper method to get the file extension
        private static String getExtension(String fileName) {
            int dotIndex = fileName.lastIndexOf('.');
            if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
                return fileName.substring(dotIndex + 1).toLowerCase();
            }
            return null;
        }
    };
}
