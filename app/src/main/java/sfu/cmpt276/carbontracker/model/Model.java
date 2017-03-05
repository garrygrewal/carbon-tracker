package sfu.cmpt276.carbontracker.model;

/**
 * Contains the Car model and various data about the model
 */

public class Model {
    private String model;
    private int year;
    private double city;
    private double highway;
    private String fuelType;
    private String trany;
    private double displ;

    public Model(String model, int year, double city, double highway, String fuelType, String trany, double displ) {
        this.model = model;
        this.year = year;
        this.city = city;
        this.highway = highway;
        this.fuelType = fuelType;
        this.trany = trany;
        this.displ = displ;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
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

    public String getTrany() {
        return trany;
    }

    public void setTrany(String trany) {
        this.trany = trany;
    }

    public double getDispl() {
        return displ;
    }

    public void setDispl(double displ) {
        this.displ = displ;
    }
}
