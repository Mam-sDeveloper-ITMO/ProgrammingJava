package desktop.pages;

import java.awt.Label;

import desktop.App;
import desktop.lib.BasePage;
import textlocale.text.TextSupplier;

public class TablePage extends BasePage {
    private TextSupplier ts = App.texts.getPackage("texts.table")::getText;

    public TablePage() {
        super("table");
    }

    @Override
    public void beforeShow() {
        this.add(new Label("Table"));
    }
}
