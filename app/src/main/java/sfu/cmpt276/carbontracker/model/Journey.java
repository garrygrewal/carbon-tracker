package sfu.cmpt276.carbontracker.model;


import android.util.Log;

/**
 * Holds the Journey data
 */

public class Journey {
    private String journeyName;
    private Vehicle vehicle;

    private int vehicleIndex;

    private Route route;
    private int routeIndex;
    private float co2PerCity;
    private float co2PerHighway;
    private float totalCO2Emission = 0;
    private Day date;



    public Journey(String journeyName, int vehicleIndex, int routeIndex) {
        this.journeyName = journeyName;
        this.vehicleIndex = vehicleIndex;
        this.routeIndex = routeIndex;
    }

    public String getJourneyName() {
        return journeyName;
    }

    public void setJourneyName(String journeyName) {
        this.journeyName = journeyName;
    }


    public void setCo2PerCity(float co2PerCity) {
        this.co2PerCity = co2PerCity;
    }

    public void setCo2PerHighway(float co2PerHighway) {
        this.co2PerHighway = co2PerHighway;
    }

    public void setTotalCO2Emission(float totalCO2Emission) {
        this.totalCO2Emission = totalCO2Emission;
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

    public int getVehicleIndex() {
        return vehicleIndex;
    }

    public void setVehicleIndex(int vehicleIndex) {
        this.vehicleIndex = vehicleIndex;
    }

    public int getRouteIndex() {
        return routeIndex;
    }

    public void setRouteIndex(int routeIndex) {
        this.routeIndex = routeIndex;
    }

    public float getTotalCO2Emission() {
        return totalCO2Emission;
    }

}
