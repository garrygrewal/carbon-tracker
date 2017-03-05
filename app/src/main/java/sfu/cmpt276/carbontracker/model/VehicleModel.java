package sfu.cmpt276.carbontracker.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * VehicleModel class contains setters and getters for vehicle objects
 */
public class VehicleModel {
    private List<MakeModel> listOfMakes = new ArrayList<>();

    public void addMake(String[] tokens){
        int year = Integer.parseInt(tokens[2]);
        double city = Double.parseDouble(tokens[3]);
        double highway = Double.parseDouble(tokens[4]);


        if(checkIfMakeExists(tokens[0])){

        } else {
            listOfMakes.add(new MakeModel(tokens[0],tokens[1],year, city,highway));
        }
    }

    public boolean checkIfMakeExists(String make){
        Iterator<MakeModel> iterator = listOfMakes.iterator();
        while(iterator.hasNext()){
            if(iterator.next().getMake() == make){
                return true;
            }
        }
        return false;
    }

}