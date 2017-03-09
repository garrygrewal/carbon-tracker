package sfu.cmpt276.carbontracker.model;

/**
 * Route Class, Contains the route information such as the city distance and the highway distance along with the name of the route.
 */

public class Route {
    private String name;
    private float city;
    private float hwy;


    public Route(String in_name, float in_city, float in_hwy) {
        this.name = in_name;
        this.city = in_city;
        this.hwy = in_hwy;
    }

    public String getName() {
        return name;
    }

    public void setName(String in_name) {
        this.name = in_name;
    }

    public float getCity() {
        return city;
    }

    public float getHwy() {
        return hwy;
    }
}
