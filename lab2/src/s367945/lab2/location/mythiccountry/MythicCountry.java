package s367945.lab2.location.mythiccountry;

import java.util.HashSet;

import s367945.lab2.blackmaiden.BlackMaiden;
import s367945.lab2.interfaces.Positioned;
import s367945.lab2.location.Location;
import s367945.lab2.structures.point.Point;
import s367945.lab2.youngking.YoungKing;

public class MythicCountry extends Location {
    private HashSet<String> stories;

    public MythicCountry(String... stories) {
        super("Unknown", new Point(), 0);
        this.stories = new HashSet<String>();
        for (String story : stories) {
            this.stories.add(story);
        }
    }

    public HashSet<String> getStories() {
        return stories;
    }

    @Override
    public boolean checkConsistency(Positioned obj) {
        if (obj instanceof YoungKing || obj instanceof BlackMaiden) {
            return true;
        }
        return false;
    }
}
