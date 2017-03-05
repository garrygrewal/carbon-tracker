package sfu.cmpt276.carbontracker.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lester on 3/4/2017.
 */

public class MakeModel {
    private String make;
    private List<ModelModel> listOfModels = new ArrayList<>();

    public MakeModel(String make, String modelName, int year, double city, double highway) {
        this.make = make;
        listOfModels.add(new ModelModel(modelName,year,city,highway));
    }
    public boolean checkIfMakeExists(String make){
        if(this.make == make) {
            return true;
        } else {
            return false;
        }
    }
    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void addModel(String modelName, int year, double city, double highway){
        listOfModels.add(new ModelModel(modelName,year,city,highway));
    }
}
