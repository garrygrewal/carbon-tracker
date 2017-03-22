package sfu.cmpt276.carbontracker.model;

import java.util.ArrayList;
import java.util.List;

/**
 * moved over from CarbonModel
 */

public class KnownCars {
    public List<Vehicle> listOfKnownCars = new ArrayList<>();

    private static KnownCars instance = new KnownCars();
    private KnownCars() {
    }
    public static KnownCars getInstance() {
        return instance;
    }

};
