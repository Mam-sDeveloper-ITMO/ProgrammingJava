package desktop.pages.main.viz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JPanel;
import javax.swing.Timer;

import lombok.Getter;
import lombok.Setter;

public class SpritesCanvas extends JPanel implements ActionListener {
    @Getter
    @Setter
    private List<HumanSprite> sprites = new ArrayList<>();

    private Timer timer;

    @Setter
    private Consumer<HumanSprite> onSpriteClicked;

    public SpritesCanvas() {
        timer = new Timer(10, this);
        timer.start();
        this.addMouseListener(new SpritesSelector());
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

    private class SpritesSelector implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            var dummy = new HumanSprite(0, e.getX(), e.getY(), 10, 0);
            for (HumanSprite sprite : sprites) {
                if (sprite.getCollisionAngel(sprite).isPresent()) {
                    if (onSpriteClicked != null) {
                        onSpriteClicked.accept(sprite);
                    }
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
}
