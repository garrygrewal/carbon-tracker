package sfu.cmpt276.carbontracker.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Carbon Model is the Singleton Class-
 */

public class CarbonModel {
    private static CarbonModel instance = new CarbonModel();
    private List<Make> listOfKnownMakes = new ArrayList<>();
    private List<Route> listOfInputRoutes = new ArrayList<>();
    private List<Vehicle> listOfInputVehicles = new ArrayList<>();
    private List<Journey> listOfJourneys = new ArrayList<>();

    public List<Vehicle> cars = new ArrayList<>();
    public List<Route> RouteList = new ArrayList<>();

    private CarbonModel() {
    }

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

    public void addCar(VehicleModel car) {
        cars.add(car);
    }

    public VehicleModel getCar(int i) {
        return cars.get(i);
    }

    public List<String> getMakes(){
        List<String> makes = new ArrayList<>();
        for(int i=0; i<cars.size(); i++){
            VehicleModel car = getCar(i);
            if(!makes.contains(car.getMake())){
                makes.add(car.getMake());
            }
        }
        return makes;
    }

    public List<String> getModels(String make){
        List<String> models = new ArrayList<>();
        for(int i=0; i<cars.size(); i++){
            VehicleModel car = getCar(i);
            if(make.equals(car.getMake())){
                models.add(car.getModel());
            }
        }
        return models;
    }

    public void fillList() {
        for (int i = 0; i < 38122; i++) {
            cars.add(new VehicleModel());
        }
    }

    public List<String> getYears(String model){
        List<String> years = new ArrayList<>();
        for(int i=0; i<cars.size(); i++){
            VehicleModel car = getCar(i);
            if(model.equals(car.getModel())){
                if(!years.contains(car.getYear())){
                    years.add(car.getYear());
                }
            }
        }
        return years;
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

    public List<String> getRemainingCars(String make, String model, String year){
        List<String> remainingCars = new ArrayList<>();
        for(int i=0; i<cars.size(); i++){
            VehicleModel car = getCar(i);
            if(make.equals(car.getMake())){
                if(model.equals(car.getModel())){
                    if(year.equals(car.getYear())){
                        if(!remainingCars.contains(car.getTransmission())){
                            if(!remainingCars.contains(car.getEngineDisplacement())){
                                remainingCars.add(car.getMake() +" " +car.getModel() +" " +car.getYear()
                                        +" " +car.getTransmission() +" " +car.getTransmission());
                            }
                        }
                    }
                }
            }
        }
        return remainingCars;
    }

    public void addVehicle(String vehicleName, String makeName, Model model){
        listOfInputVehicles.add(new Vehicle(vehicleName,makeName,model));
    }


}
