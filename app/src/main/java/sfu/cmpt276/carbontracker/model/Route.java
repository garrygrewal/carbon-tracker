package sfu.cmpt276.carbontracker.model;

/**
 * Created by Lester on 3/4/2017.
 */

public class Route {
    private double cityStreetsDistance;
    private double highwayRoadsDistance;

    public Route(double cityStreetsDistance, double highwayRoadsDistance) {
        this.cityStreetsDistance = cityStreetsDistance;
        this.highwayRoadsDistance = highwayRoadsDistance;
    }

    public double getCityStreetsDistance() {
        return cityStreetsDistance;
    }

    public void setCityStreetsDistance(double cityStreetsDistance) {
        this.cityStreetsDistance = cityStreetsDistance;
    }

    public double getHighwayRoadsDistance() {
        return highwayRoadsDistance;
    }

    public void setHighwayRoadsDistance(double highwayRoadsDistance) {
        this.highwayRoadsDistance = highwayRoadsDistance;
    }
}
