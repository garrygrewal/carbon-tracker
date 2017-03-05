package sfu.cmpt276.carbontracker.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Carbon Model is the Singleton Class-
 * Contains list of vehicles from cvs file.
 */

public class CarbonModel {
    private static CarbonModel instance = new CarbonModel();
    public List<VehicleModel> cars = new ArrayList<>();

    private CarbonModel() {
    }

    public static CarbonModel getInstance() {
        return instance;
    }

    public void addCar(VehicleModel car) {
        cars.add(car);
    }

    public VehicleModel getCar(int i) {
        return cars.get(i);
    }

    public void fillList() {
        for (int i = 0; i < 38122; i++) {
            cars.add(new VehicleModel());
        }
    }
}
