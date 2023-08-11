package desktop.pages.auth;

import java.awt.CardLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.formdev.flatlaf.ui.FlatBorder;

import adapter.Adapter;

public class CardsPanel extends JPanel {
    private final Adapter authAdapter;

    private CardLayout cardsLayout;

    public CardsPanel(Adapter authAdapter) {
        super();
        this.authAdapter = authAdapter;
        init();
    }

    private void init() {
        this.setBorder(
                BorderFactory.createCompoundBorder(new FlatBorder(), BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        cardsLayout = new CardLayout();
        this.setLayout(cardsLayout);

        this.add(new SignInCard(authAdapter, this::showSignUpCard), "signIn");
        this.add(new SignUpCard(authAdapter, this::showSignInCard), "signUp");
    }

    public void showSignInCard() {
        cardsLayout.show(this, "signIn");
        this.requestFocusInWindow();
    }

    public void showSignUpCard() {
        cardsLayout.show(this, "signUp");
        this.requestFocusInWindow();
    }
}
