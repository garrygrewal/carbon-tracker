package sfu.cmpt276.carbontracker.model;

import android.util.Log;

import java.util.ArrayList;
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



    //for integrating with ArrayAdapter
    public String[] getCarInfo() {
        String[] info = new String[countCars()];
        for (int i = 0; i < countCars(); i++) {
            Vehicle vehicle = getVehicle(i);
            info[i] = vehicle.getName() + ", " + vehicle.getMake() + ", " + vehicle.getModel() + ", " + vehicle.getYear() + ", " + vehicle.getCity() + ", " + vehicle.getHighway() + ", " + vehicle.getFuelType() + ", " + vehicle.getTransmission() + ", " + vehicle.getEngineDisplacement();
                    vehicle.getEngineDisplacement();
        }
        return info;
    }
    public String[] getRemainingCarInfo(List<Vehicle> vehicles) {
        String[] info = new String[vehicles.size()];
        for (int i = 0; i < vehicles.size(); i++) {
            Vehicle vehicle = vehicles.get(i);
            info[i] = vehicle.getMake() + ", " + vehicle.getModel() + ", " + vehicle.getYear() + ", " + vehicle.getCity() + ", " + vehicle.getHighway() + ", " + vehicle.getFuelType() + ", " + vehicle.getTransmission() + ", " + vehicle.getEngineDisplacement();
            vehicle.getEngineDisplacement();
        }
        return info;
    }

    public int countCars(){
        return listOfInputVehicles.size();
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

    //get transmission after select make,model, year
    public String getTransmissionFromRemain(String make,String model,String year){
        String transmission="";
        for(int i=0; i<countCars(); i++){
            Vehicle car = getCar(i);
            if(make.equals(car.getMake())){
                if(model.equals(car.getModel())){
                    if(year.equals(car.getYear())){
                        transmission=car.getTransmission();
                    }
                }
            }
        }
        return transmission;
    }
    //get engineDisplacement after select make,model,year
    public String getEngineDiplacementFromRemain(String make,String model,String year){
        String engineDisplacement="";
        for(int i=0; i<countCars(); i++){
            Vehicle car = getCar(i);
            if(make.equals(car.getMake())){
                if(model.equals(car.getModel())){
                    if(year.equals(car.getYear())){
                        engineDisplacement=car.getTransmission();
                    }
                }
            }
        }
        return engineDisplacement;
    }

    public List<Vehicle> getRemainingCars(String make, String model, String year){
        List<String> remainingCars = new ArrayList<>();
        List<Vehicle> vehiclesLeft = new ArrayList<>();
        for(int i=0; i<cars.size(); i++){
            Vehicle car = getCar(i);
            if(make.equals(car.getMake())){
                if(model.equals(car.getModel())){
                    if(year.equals(car.getYear())){
                        if(!remainingCars.contains(car.getTransmission())){
                            if(!remainingCars.contains(car.getEngineDisplacement())){
                                remainingCars.add(car.getTransmission());
                                remainingCars.add(car.getEngineDisplacement());
                                vehiclesLeft.add(car);
                            }
                        }
                    }
                }
            }
        }
        return vehiclesLeft;
    }

    public void fillList(int rows) {
        for (int i = 0; i < rows; i++) {
            cars.add(new Vehicle());
        }
    }


    public void calculateCarbonEmissions(Journey journey){
        journey.calculateCarbonEmissions();
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
*/
    public void addVehicle(String name, String make, String model, String year, String city, String hwy, String fuelType, String transmission, String displacement){
        Vehicle vehicle = new Vehicle();

        vehicle.setName(name);
        vehicle.setMake(make);
        vehicle.setModel(model);
        vehicle.setYear(year);
        vehicle.setCity(Double.parseDouble(city));
        vehicle.setHighway(Double.parseDouble(hwy));
        vehicle.setFuelType(fuelType);
        if (fuelType.equals("Electricity")) {
            vehicle.setTransmission("none");
            vehicle.setEngineDisplacement("none");
        } else {
            vehicle.setTransmission(transmission);
            vehicle.setEngineDisplacement(displacement);
        }
        listOfInputVehicles.add(vehicle);
    }

    public Vehicle getVehicle(int index) {
        return listOfInputVehicles.get(index);
    }

}
