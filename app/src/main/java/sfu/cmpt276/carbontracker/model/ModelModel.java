package sfu.cmpt276.carbontracker.model;

/**
 * Created by Lester on 3/4/2017.
 */

public class ModelModel {
    private String model;
    private int year;
    private double city;
    private double highway;

    public ModelModel(String model, int year, double city, double highway) {
        this.model = model;
        this.year = year;
        this.city = city;
        this.highway = highway;
    }
}
