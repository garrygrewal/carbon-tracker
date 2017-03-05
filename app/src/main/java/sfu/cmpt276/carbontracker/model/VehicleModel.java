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

    }

    public boolean checkIfMakeExists(String make){
        Iterator<MakeModel> iterator = listOfMakes.iterator();
        while(iterator.hasNext()){
            if(iterator.next().checkIfMakeExists(make)){
                return true;
            }
        }
        return false;
    }

}
