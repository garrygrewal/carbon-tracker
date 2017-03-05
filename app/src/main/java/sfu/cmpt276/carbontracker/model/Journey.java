package sfu.cmpt276.carbontracker.model;

/**
 * Holds the Journey data
 */

public class Journey {
    private String journeyName;
    private Vehicle vehicle;
    private Route route;

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
}
