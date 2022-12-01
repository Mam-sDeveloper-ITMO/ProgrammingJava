package s367945.lab2.location.magicforest;

import s367945.lab2.interfaces.Positioned;
import s367945.lab2.lion.Lion;
import s367945.lab2.location.Location;
import s367945.lab2.nekowoman.NekoWoman;
import s367945.lab2.plant.tree.WhiteTree;
import s367945.lab2.structures.point.Point;

import java.util.Arrays;

import s367945.lab2.animal.human.abs.bandit.Bandit;
import s367945.lab2.animal.human.abs.dwarf.Dwarf;
import s367945.lab2.animal.human.abs.francepeer.FrancePeer;
import s367945.lab2.animal.human.abs.giant.Giant;
import s367945.lab2.animal.human.abs.ukpeer.UKPeer;
import s367945.lab2.animal.human.interfaces.Monk;

public class MagicForest extends Location {
    private WhiteTree[] trees;

    public MagicForest(String name, Point center, int radius, WhiteTree... trees) {
        super(name, center, radius);
        this.trees = trees;
    }

    @Override
    public boolean checkConsistency(Positioned obj) {
        if (obj instanceof FrancePeer || obj instanceof UKPeer || obj instanceof Bandit || obj instanceof Monk) {
            return false;
        } else if (obj instanceof NekoWoman || obj instanceof Giant || obj instanceof Dwarf || obj instanceof Lion) {
            return true;
        }

        if (obj instanceof WhiteTree) {
            WhiteTree tree = (WhiteTree) obj;
            return Arrays.binarySearch(this.trees, tree) >= 0;
        }

        Point objPosition = obj.getPosition();
        return Math.abs(this.center.x - objPosition.x) <= this.radius
                && Math.abs(this.center.y - objPosition.y) <= this.radius
                && Math.abs(this.center.z - objPosition.z) <= this.radius;
    }

    public WhiteTree[] getTrees() {
        return trees;
    }
}
