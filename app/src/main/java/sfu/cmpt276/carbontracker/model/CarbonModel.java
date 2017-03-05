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

    public List<Vehicle> cars = new ArrayList<>();
    public List<Route> RouteList = new ArrayList<>();

    public int countRoutes() {
        return listOfInputRoutes.size();
    }

    public Route getRoute(int index) {
        return listOfInputRoutes.get(index);
    }

    public void removeRoute(int index) { listOfInputRoutes.remove(index); }

    public void editRoute(Route route, int index) {
        listOfInputRoutes.remove(index);
        listOfInputRoutes.add(index, route);
    }

    //for integrating with ArrayAdapter
    public String[] getRouteInfo() {
        String[] info = new String[countRoutes()];
        for (int i = 0; i < countRoutes(); i++) {
            Route route = getRoute(i);
            info[i] = route.getName() + ", " + route.getCity() + " (city), " + route.getHwy() + " (highway).";
        }
        return info;
    }

    public void addRoute(Route route) {
        RouteList.add(route);
    }

    public static CarbonModel getInstance() {
        return instance;
    }

    public void addMake(String makeName, String modelName,int year,double city,double highway,String fuelType,String trany,double displ){

        Year makeYear = new Year(year, city, highway, fuelType, trany, displ);
        for (Make make:listOfKnownMakes) {
            if(make.getMake().equals(makeName)){
                make.addModel(modelName, makeYear);
                return;
            }
        }
        listOfKnownMakes.add(new Make(makeName, modelName, makeYear));
    }


    public void addVehicle(String vehicleName, String makeName, Model model){
        listOfInputVehicles.add(new Vehicle(vehicleName,makeName,model));
    }


}
