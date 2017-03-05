package sfu.cmpt276.carbontracker.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Carbon Model is the Singleton Class-
 * Contains list of vehicles from cvs file.
 */

public class CarbonModel {
    private static CarbonModel instance = new CarbonModel();
    private List<Make> listOfKnownMakes = new ArrayList<>();
    private List<Route> listOfInputRoutes = new ArrayList<>();
    private List<Vehicle> listOfInputVehicles = new ArrayList<>();


    private CarbonModel() {}

    public static CarbonModel getInstance() {
        return instance;
    }

    public void addMake(String makeName, Model model){
        boolean makeExists = false;
        for (Make make:listOfKnownMakes) {
            if(make.getMake().equals(makeName)){
                make.addModel(model);
                makeExists = true;
            }
        }
        if(makeExists == false){
            listOfKnownMakes.add(new Make(makeName, model));
        }
    }
    public void addVehicle(String vehicleName, String makeName, Model model){
        listOfInputVehicles.add(new Vehicle(vehicleName,makeName,model));
    }


    public void printListOfKnownMakes(){
        for(Make make: listOfKnownMakes){
            System.out.println(make.getMake());
        }
    }

}
