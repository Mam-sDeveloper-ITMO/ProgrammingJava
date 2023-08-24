package desktop.pages.main.viz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

import lombok.Getter;
import lombok.Setter;

public class SpritesCanvas extends JPanel implements ActionListener {
    @Getter @Setter
    private List<HumanSprite> sprites = new ArrayList<>();

    private Timer timer;

    public SpritesCanvas() {
        timer = new Timer(10, this);
        timer.start();
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        for (HumanSprite sprite : sprites) {
            g.fillOval(
                (int) sprite.getX() - (int) sprite.getRadius(),
                (int) sprite.getY() - (int) sprite.getRadius(),
                (int) sprite.getRadius() * 2,
                (int) sprite.getRadius() * 2);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (HumanSprite sprite : sprites) {
            sprite.move();
            sprite.bounceWalls(getWidth(), getHeight());
        }
        for (int i = 0; i < sprites.size(); i++) {
            for (int j = i + 1; j < sprites.size(); j++) {
                sprites.get(i).bounceSprite(sprites.get(j));
            }
        }
        repaint();
    }
}
