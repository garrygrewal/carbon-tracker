package sfu.cmpt276.carbontracker.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Carbon Model is the Singleton Class-
 * Contains list of vehicles from cvs file.
 */

public class CarbonModel {
    private static CarbonModel instance = new CarbonModel();
    public List<VehicleModel> cars = new ArrayList<>();
    public List<RouteModel> RouteList = new ArrayList<>();

    public int countRoutes() {
        return RouteList.size();
    }

    public RouteModel getRoute(int index) {
        return RouteList.get(index);
    }

    public void removeRoute(int index) { RouteList.remove(index); }

    public void editRoute(RouteModel route, int index) {
        RouteList.remove(index);
        RouteList.add(index, route);
    }

    //for integrating with ArrayAdapter
    public String[] getRouteInfo() {
        String[] info = new String[countRoutes()];
        for (int i = 0; i < countRoutes(); i++) {
            RouteModel route = getRoute(i);
            info[i] = route.getName() + ", " + route.getCity() + " (city), " + route.getHwy() + " (highway).";
        }
        return info;
    }

    public void addRoute(RouteModel route) {
        RouteList.add(route);
    }

    private CarbonModel() {
    }

    public static CarbonModel getInstance() {
        return instance;
    }

    public void addCar(VehicleModel car) {
        cars.add(car);
    }

    public VehicleModel getCar(int i) {
        return cars.get(i);
    }

    public void fillList() {
        for (int i = 0; i < 38122; i++) {
            cars.add(new VehicleModel());
        }
    }
}
