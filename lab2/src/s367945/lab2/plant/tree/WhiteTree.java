package s367945.lab2.plant.tree;

import s367945.lab2.enums.BreathSource;
import s367945.lab2.interfaces.Positioned;
import s367945.lab2.interfaces.Walkable;
import s367945.lab2.plant.Plant;
import s367945.lab2.plant.root.Root;

public class WhiteTree extends Plant implements Walkable {
    private Root root;

    public WhiteTree(String name, Root root) {
        super(name);
        this.root = root;
    }

    @Override
    public BreathSource photosynthesize(BreathSource source) {
        return this.root.photosynthesize(source);
    }

    @Override
    public void walk(Positioned target) throws IncorrectPosition {
        this.root.walk(target);
        this.position = target.getPosition();
    }
}
