package sfu.cmpt276.carbontracker.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains the Car model and various data about the model
 */

public class Model {
    private String model;
    private List<Year> listOfYears = new ArrayList<>();

    public Model(String model, Year year) {
        this.model = model;
        listOfYears.add(year);
    }
    public void addYear(Year year){
        listOfYears.add(year);
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }


}
