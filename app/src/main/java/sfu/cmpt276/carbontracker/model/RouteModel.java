package sfu.cmpt276.carbontracker.model;

/**
 * Created by Lester on 3/4/2017.
 */

public class RouteModel {
    private String name;
    private double city;
    private double hwy;


    public RouteModel(String in_name, double in_city, double in_hwy) {
        this.name = in_name;
        this.city = in_city;
        this.hwy = in_hwy;
    }

    public String getName() { return name; }

    public void setName(String in_name) {
        this.name = in_name;
    }

    public double getCity() {
        return city;
    }

    public void setCity(double cityStreetsDistance) {
        this.city = cityStreetsDistance;
    }

    public double getHwy() {
        return hwy;
    }

    public void setHwy(double highwayRoadsDistance) {
        this.hwy = highwayRoadsDistance;
    }
}
