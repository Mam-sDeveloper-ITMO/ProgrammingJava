package s367945.lab2.classes;

import s367945.lab2.enums.Color;
import s367945.lab2.enums.Property;
import s367945.lab2.interfaces.Patterned;

public class Moon implements Patterned {
    private final Pattern surface = new Pattern(Color.GRAY, "Amazing Moon surface with infinity of craters",
            Property.MAGIC);
    
    @Override
    public Pattern getPattern() {
        return this.surface;
    }
}
