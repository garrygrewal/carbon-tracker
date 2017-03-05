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

    public Make(String make, Model model) {
        this.make = make;
        addModel(model);
    }


    public void setMake(String make) {
        this.make = make;
    }

    public void addModel(Model model){
        listOfModels.add(model);
    }

    public String getMake() {
        return make;
    }
    public List<Model> getListOfModels() {
        return Collections.unmodifiableList(listOfModels);
    }
}
