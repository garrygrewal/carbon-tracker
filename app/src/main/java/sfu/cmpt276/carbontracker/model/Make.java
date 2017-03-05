package sfu.cmpt276.carbontracker.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Holds the Make and has a list of Models manufactured by the maker.
 */

public class Make {
    private String make;
    private List<Model> listOfModels = new ArrayList<>();

    public Make(String makeName, String modelName, Year year) {
        this.make = makeName;
        addModel(modelName, year);
    }


    public void setMake(String make) {
        this.make = make;
    }

    public void addModel(String modelName, Year year){
        for (Model carModel: listOfModels) {
            if(carModel.getModel().equals(modelName)){
                carModel.addYear(year);
                return;
            }
        }
            listOfModels.add(new Model(modelName,year));

    }

    public String getMake() {
        return make;
    }
    public List<Model> getListOfModels() {
        return Collections.unmodifiableList(listOfModels);
    }
}
