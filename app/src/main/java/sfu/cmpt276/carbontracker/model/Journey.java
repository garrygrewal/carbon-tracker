package sfu.cmpt276.carbontracker.model;
import java.io.Serializable;


import android.util.Log;

/**
 * Journey Class, holds the journey information
 */

public class Journey implements Serializable{
    private String journeyName;
    private int vehicleIndex;
    private int routeIndex;
    private float co2PerCity;
    private float co2PerHighway;
    private float treesPerCity;
    private float treesPerHighway;
    private float totalTreesEmission = 0;
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

    public void setDate(int year, int month, int day) {
        date = new Day(year, month, day);
    }

    public String getStringDate() {
        return this.date.getString();
    }

    public Day getDay() {
        return date;
    }

    public int getVehicleIndex() {
        return vehicleIndex;
    }

    public int getRouteIndex() {
        return routeIndex;
    }

    public float getTotalCO2Emission() {
        return totalCO2Emission;
    }

    public double getTreesPerCity() {
        return treesPerCity;
    }

    public void setTreesPerCity(float treesPerCity){
        this.treesPerCity = treesPerCity;
    }

    public double getTreesPerHighway() {
        return treesPerHighway;
    }

    public void setTreesPerHighway(float treesPerHighway){
        this.treesPerHighway = treesPerHighway;
    }

    public void setTotalTreesEmission(float totalTreesEmission){
        this.totalTreesEmission = totalTreesEmission;
    }

    public double getTotalTreesEmission() {
        return totalTreesEmission;
    }
}
