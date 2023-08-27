package desktop.pages.main.viz;

import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import models.Human;

public class VizPanel extends JPanel {
    private SpritesCanvas spritesCanvas;

    private final float SPRITE_SIZE = 10;

    public VizPanel(List<Human> initialHumans) {
        init(initialHumans);
    }

    private void init(List<Human> initialHumans) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        spritesCanvas = new SpritesCanvas();
        add(spritesCanvas);

        setHumans(initialHumans);
    }

    public void setHumans(List<Human> humans) {
        List<HumanSprite> sprites = humans.stream()
            .map(h -> new HumanSprite(
                h.getCoordinates().getX(),
                h.getCoordinates().getY(),
                h.getMinutesOfWaiting() == null ? SPRITE_SIZE : h.getMinutesOfWaiting(),
                h.getImpactSpeed()))
            .toList();

        spritesCanvas.setSprites(sprites);
    }
}

