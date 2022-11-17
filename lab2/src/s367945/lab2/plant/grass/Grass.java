package s367945.lab2.plant.grass;

import s367945.lab2.enums.BreathSource;
import s367945.lab2.plant.Plant;

public class Grass extends Plant {
    public Grass(String name) {
        super(name);
    }

    @Override
    public BreathSource photosynthesize(BreathSource source) {
        if (source == BreathSource.CARBON_DIOXIDE) {
            return BreathSource.OXYGEN;
        }
        return BreathSource.FOG;
    }
}
