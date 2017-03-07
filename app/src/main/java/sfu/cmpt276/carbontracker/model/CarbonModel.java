package sfu.cmpt276.carbontracker.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.media.CamcorderProfile.get;

/**
 * Carbon Model is the Singleton Class-
 */

public class CarbonModel {
    private static CarbonModel instance = new CarbonModel();
    private List<Make> listOfKnownMakes = new ArrayList<>();
    private List<Route> listOfInputRoutes = new ArrayList<>();
    private List<Integer> listOfHiddenRoutes = new ArrayList<>();
    private List<Vehicle> listOfInputVehicles = new ArrayList<>();
    private List<Journey> listOfJourneys = new ArrayList<>();


    public List<Vehicle> cars = new ArrayList<>();

    private CarbonModel() {
    }

    public void hideRoute(int index) {
        listOfHiddenRoutes.add(index);
    }

    public int countRoutes() {
        return (listOfInputRoutes.size() - listOfHiddenRoutes.size());
    }

    public Route getRoute(int index) {
        for (int i=0; i<listOfHiddenRoutes.size(); i++) {
            if (listOfHiddenRoutes.get(i) <= index) {
                index++;
            }
        }
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
        listOfInputRoutes.add(route);
    }

    public static CarbonModel getInstance() {
        return instance;
    }

    public void addCar(Vehicle car) {
        cars.add(car);
    }

    public Vehicle getCar(int i) {
        return cars.get(i);
    }

    public List<String> getMakes(){
        List<String> makes = new ArrayList<>();
        for(int i=0; i<cars.size(); i++){
            Vehicle car = getCar(i);
            if(!makes.contains(car.getMake())){
                makes.add(car.getMake());
            }
        }
        return makes;
    }

    public List<String> getModels(String make){
        List<String> models = new ArrayList<>();
        for(int i=0; i<cars.size(); i++){
            Vehicle car = getCar(i);
            if(make.equals(car.getMake())){
                if(!models.contains(car.getModel())) {
                    models.add(car.getModel());
                }
            }
        }
        return models;
    }

    public List<String> getYears(String model){
        List<String> years = new ArrayList<>();
        for(int i=0; i<cars.size(); i++){
            Vehicle car = getCar(i);
            if(model.equals(car.getModel())){
                if(!years.contains(car.getYear())){
                    years.add(car.getYear());
                }
            }
        }
        return years;
    }

    public List<String> getRemainingCars(String make, String model, String year){
        List<String> remainingCars = new ArrayList<>();
        List<String> outputCars = new ArrayList<>();
        for(int i=0; i<cars.size(); i++){
            Vehicle car = getCar(i);
            if(make.equals(car.getMake())){
                if(model.equals(car.getModel())){
                    if(year.equals(car.getYear())){
                        if(!remainingCars.contains(car.getTransmission())){
                            if(!remainingCars.contains(car.getEngineDisplacement())){
                                remainingCars.add(car.getTransmission());
                                remainingCars.add(car.getEngineDisplacement());
                                outputCars.add(car.getMake() +" " +car.getModel() +" " +car.getYear()
                                        +" - " + car.getFuelType() +" " +car.getTransmission() +" "
                                        +car.getEngineDisplacement());
                            }
                        }
                    }
                }
            }
        }
        return outputCars;
    }

    public void fillList(int rows) {
        for (int i = 0; i < rows; i++) {
            cars.add(new Vehicle());
        }
    }


    public void calculateCarbonEmissions(Journey journey){
        journey.calculateCarbonEmissions();
    }

    public int getSizeOfJourneysList(){
        return listOfJourneys.size();
    }
    public String getJourneyName(int i){
        return listOfJourneys.get(i).getJourneyName();
    }
    public float getJourneyTotalCO2Emissions(int i){
        return listOfJourneys.get(i).getTotalCO2Emission();
    }
    /////////////////////////////////////////////
// CODE USED FOR TESTING REMOVE BEFORE SUBMISSION //
    //////////////////////////////////////////////
    public void initiateTest() {
        Route route = new Route("testRoute", 100, 200);
        Vehicle car = new Vehicle();
        String name;
        car.setCity(100);
        car.setHighway(100);
        car.setFuelType("Diesel");
        for(int i = 0; i < 4; i++) {
            name = "test" + String.valueOf(i);
            Journey journey = new Journey(name, car, route);
            journey.calculateCarbonEmissions();
            listOfJourneys.add(journey);
        }

    }

/*
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
*/

}
