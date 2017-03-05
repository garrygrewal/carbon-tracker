package sfu.cmpt276.carbontracker.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Vehicle class contains setters and getters for vehicle objects
 */
public class Vehicle {
    private String carName;
    private String makeName;
    private Model model;

    public Vehicle(String carName, String make, Model model) {
        this.carName = carName;
        this.makeName = make;
        this.model = model;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getMakeName() {
        return makeName;
    }

    public void setMakeName(String makeName) {
        this.makeName = makeName;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }
}