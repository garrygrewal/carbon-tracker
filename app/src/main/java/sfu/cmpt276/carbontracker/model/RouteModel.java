package sfu.cmpt276.carbontracker.model;

/**
 * Created by Lester on 3/4/2017.
 */

public class RouteModel {
    private String name;
    private float city;
    private float hwy;


    public RouteModel(String in_name, float in_city, float in_hwy) {
        this.name = in_name;
        this.city = in_city;
        this.hwy = in_hwy;
    }

    public String getName() { return name; }

    public void setName(String in_name) {
        this.name = in_name;
    }

    public float getCity() {
        return city;
    }

    public void setCity(float cityStreetsDistance) {
        this.city = cityStreetsDistance;
    }

    public float getHwy() {
        return hwy;
    }

    public void setHwy(float highwayRoadsDistance) {
        this.hwy = highwayRoadsDistance;
    }
}
