package s367945.lab2.location.tropicalgarden;

import java.util.Arrays;

import s367945.lab2.interfaces.Positioned;
import s367945.lab2.location.Location;
import s367945.lab2.plant.Plant;
import s367945.lab2.structures.point.Point;

public class TropicalGarden extends Location {
    private Plant[] plants;

    public TropicalGarden(String name, Point center, int radius, Plant... plants) {
        super(name, center, radius);
        this.plants = plants;
    }

    public Plant[] getPlants() {
        return plants;
    }

    @Override
    public boolean checkConsistency(Positioned obj) {
        if (obj instanceof Plant) {
            Plant plant = (Plant) obj;
            return Arrays.binarySearch(this.plants, plant) >= 0;
        }
        return false;
    }
}
