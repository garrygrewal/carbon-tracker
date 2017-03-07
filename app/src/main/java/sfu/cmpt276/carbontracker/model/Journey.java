package sfu.cmpt276.carbontracker.model;


/**
 * Holds the Journey data
 */

public class Journey {
    private String journeyName;
    private Vehicle vehicle;
    private Route route;
    private float co2PerCity;
    private float co2PerHighway;
    private float totalCO2Emission = 0;
    private Day date;

    private final double GASOLINE_CO2_EMISSION = 8.89;
    private final double ELECTRIC_CO2_EMISSION = 0;
    private final double DIESEL_CO2_EMISSION = 10.16;

    public Journey(String journeyName, Vehicle vehicle, Route route) {
        this.journeyName = journeyName;
        this.vehicle = vehicle;
        this.route = route;
    }

    public String getJourneyName() {
        return journeyName;
    }

    public void setJourneyName(String journeyName) {
        this.journeyName = journeyName;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public double getCo2PerCity() {
        return co2PerCity;
    }

    public double getCo2PerHighway() {
        return co2PerHighway;
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
        double highwayMilesPerGallon = vehicle.getHighway();
        double cityMilesPerGallon = vehicle.getCity();
        double co2EmittedPerGallonOfFuel;
        if (vehicle.getFuelType().toLowerCase().contains("gasoline")) {
            co2EmittedPerGallonOfFuel = GASOLINE_CO2_EMISSION;
        } else if (vehicle.getFuelType().toLowerCase().contains("electricity")) {
            co2EmittedPerGallonOfFuel = ELECTRIC_CO2_EMISSION;
        } else if (vehicle.getFuelType().toLowerCase().contains("diesel")) {
            co2EmittedPerGallonOfFuel = DIESEL_CO2_EMISSION;
        } else {
            throw new IllegalArgumentException(); //crash
        }
        co2PerCity = (float)  ((route.getCity() / cityMilesPerGallon) * co2EmittedPerGallonOfFuel);
        co2PerHighway = (float) ((route.getHwy() / highwayMilesPerGallon) * co2EmittedPerGallonOfFuel);
        totalCO2Emission = co2PerCity+co2PerHighway;
    }


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
}
