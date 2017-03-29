package sfu.cmpt276.carbontracker.model;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Vehicle class, contains the vehicle information
 */

public class Vehicle implements Serializable{
    private String make;
    private String name;
    private String model;
    private String year;
    private double city;
    private double highway;
    private String fuelType;
    private String transmission;
    private String engineDisplacement;
    private int icon;

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public double getCity() {
        return city;
    }

    public void setCity(double city) {
        this.city = city;
    }

    public double getHighway() {
        return highway;
    }

    public void setHighway(double highway) {
        this.highway = highway;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getEngineDisplacement() {
        return engineDisplacement;
    }

    public void setEngineDisplacement(String engineDisplacement) {
        this.engineDisplacement = engineDisplacement;
    }

    public void setName(String in_name) {
        this.name = in_name;
    }

    public String getName() {
        return name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}