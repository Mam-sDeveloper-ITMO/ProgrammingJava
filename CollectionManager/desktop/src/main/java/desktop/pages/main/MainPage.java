package desktop.pages.main;

import java.awt.BorderLayout;

import desktop.App;
import desktop.lib.BasePage;

public class MainPage extends BasePage {

    public MainPage() {
        super("main");
    }

    public void beforeShow() {
        TopBar topBar = new TopBar(App.context.getUsername());

        // Set the BorderLayout for the container
        setLayout(new BorderLayout());

        // Add the TopBar to the NORTH (top) of the container and let it stretch
        // horizontally
        add(topBar, BorderLayout.NORTH);
    }
}
