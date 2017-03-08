package sfu.cmpt276.carbontracker.model;


/**
 * Holds the Journey data
 */

public class Journey {
    private String journeyName;
    private int vehicleIndex;
    private int routeIndex;
    //private Vehicle vehicle;
    //private Route route;
    private float co2PerCity;
    private float co2PerHighway;
    private float totalCO2Emission = 0;
    private Day date;

    private final double GASOLINE_CO2_EMISSION = 8.89;
    private final double ELECTRIC_CO2_EMISSION = 0;
    private final double DIESEL_CO2_EMISSION = 10.16;

    public Journey(String journeyName, int vehicle_index, int route_index) {
        this.journeyName = journeyName;
        this.vehicleIndex = vehicle_index;
        this.routeIndex = route_index;
    }

    public String getJourneyName() {
        return journeyName;
    }

    public void setJourneyName(String journeyName) {
        this.journeyName = journeyName;
    }

    public Vehicle getVehicle() {
        return CarbonModel.getInstance().getVehicle(vehicleIndex);
    }

    public void setVehicle(int vehicle) {
        this.vehicleIndex = vehicle;
    }

    public Route getRoute() {
        return CarbonModel.getInstance().getRoute(routeIndex);
    }

    public void setRoute(int route) {
        this.routeIndex = route;
    }

    public double getCo2PerCity() {
        return CarbonModel.getInstance().getVehicle(vehicleIndex).getCity();
    }

    public double getCo2PerHighway() {
        return CarbonModel.getInstance().getVehicle(vehicleIndex).getHighway();
    }
    public void setDate(int year, int month, int day){
        date = new Day(year, month, day);
    }

    public String getDate(){
        return date.getString();
    }

    public float getTotalCO2Emission() {
        return totalCO2Emission;
    }

    public void calculateCarbonEmissions() {
        double highwayMilesPerGallon = CarbonModel.getInstance().getVehicle(vehicleIndex).getHighway();
        double cityMilesPerGallon = CarbonModel.getInstance().getVehicle(vehicleIndex).getCity();
        double co2EmittedPerGallonOfFuel;
        if (CarbonModel.getInstance().getVehicle(vehicleIndex).getFuelType().toLowerCase().contains("gasoline")) {
            co2EmittedPerGallonOfFuel = GASOLINE_CO2_EMISSION;
        } else if (CarbonModel.getInstance().getVehicle(vehicleIndex).getFuelType().toLowerCase().contains("electricity")) {
            co2EmittedPerGallonOfFuel = ELECTRIC_CO2_EMISSION;
        } else if (CarbonModel.getInstance().getVehicle(vehicleIndex).getFuelType().toLowerCase().contains("diesel")) {
            co2EmittedPerGallonOfFuel = DIESEL_CO2_EMISSION;
        } else {
            throw new IllegalArgumentException(); //crash
        }
        co2PerCity = (float)  ((CarbonModel.getInstance().getRoute(routeIndex).getCity() / cityMilesPerGallon) * co2EmittedPerGallonOfFuel);
        co2PerHighway = (float) ((CarbonModel.getInstance().getRoute(routeIndex).getHwy() / highwayMilesPerGallon) * co2EmittedPerGallonOfFuel);
        totalCO2Emission = co2PerCity+co2PerHighway;
    }

/*
    public static void main(String[] args){
        Route route = new Route("testRoute", 100, 200);
        Vehicle car = new Vehicle();
        car.setCity(100);
        car.setHighway(100);
        car.setFuelType("Diesel");

        Journey journey = new Journey("test",car,route);
        journey.calculateCarbonEmissions();
        System.out.println("" + journey.getCo2PerCity());
        System.out.println("" + journey.getCo2PerHighway());

    }
    */
}
