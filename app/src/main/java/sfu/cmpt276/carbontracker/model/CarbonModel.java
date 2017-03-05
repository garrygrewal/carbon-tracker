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
    private List<Journey> listOfJourneys = new ArrayList<>();

    private CarbonModel() {}

    public static CarbonModel getInstance() {
        return instance;
    }

    public void addMake(String makeName, String modelName, Year year){
        for (Make make:listOfKnownMakes) {
            if(make.getMake().equals(makeName)){
                make.addModel(modelName, year);
                return;
            }
        }
        listOfKnownMakes.add(new Make(makeName, modelName, year));
    }


    public void addVehicle(String vehicleName, String makeName, Model model){
        listOfInputVehicles.add(new Vehicle(vehicleName,makeName,model));
    }


}
