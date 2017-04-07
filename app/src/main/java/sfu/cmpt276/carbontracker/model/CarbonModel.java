package sfu.cmpt276.carbontracker.model;

import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.io.Serializable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import sfu.cmpt276.carbontracker.model.KnownCars;

/**
 * CarbonModel is the Singleton Class-
 */

public class CarbonModel implements Serializable {
    private static final long serialVersionUID = 123456;

    private static CarbonModel instance = new CarbonModel();

    private List<Route> listOfInputRoutes = new ArrayList<>();
    private List<Integer> listOfHiddenRoutes = new ArrayList<>();
    private List<Vehicle> listOfInputVehicles = new ArrayList<>();
    private List<Integer> listOfHiddenVehicles = new ArrayList<>();
    private List<Journey> listOfJourneys = new ArrayList<>();
    private TipsArray tipsArray = new TipsArray();


    private List<Bill> listOfBills = new ArrayList<>();


    private final double GASOLINE_CO2_EMISSION = 8.89;
    private final double ELECTRIC_CO2_EMISSION = 0;
    private final double DIESEL_CO2_EMISSION = 10.16;
    private final double CO2_EMISSION = 1;
    private final double kmToMiles = 0.621371;

    private final double CO2_EMISSIONS_PER_CAPITA_METRIC_TONNES_2013 = 13.532;
    private final double DAYS_IN_YEAR = 365;
    private final double TARGET_PERCENT = 0.70;

    //DBAdapter CarbonTrackerDB;

    private CarbonModel() {
    }

    public TipsArray getTipsArray(){
        return tipsArray;
    }

    public void hideRoute(int index) {
        listOfHiddenRoutes.add(index);
    }

    private float[] getNationalAverageAndParisAccordPerday(){
        float[] results = new float[2];
        results[0] =(float)( CO2_EMISSIONS_PER_CAPITA_METRIC_TONNES_2013 / DAYS_IN_YEAR);
        results[1] =(float)(CO2_EMISSIONS_PER_CAPITA_METRIC_TONNES_2013 / DAYS_IN_YEAR * TARGET_PERCENT);
        return results;
    }
    public int countRoutes() {
        return (listOfInputRoutes.size() - listOfHiddenRoutes.size());
    }

    public Route getRoute(int index) {
        for (int i = 0; i < listOfHiddenRoutes.size(); i++) {
            if (listOfHiddenRoutes.get(i) <= index) {
                index++;
            }
        }
        return listOfInputRoutes.get(index);
    }

    public void editRoute(Route route, int index) {
        listOfInputRoutes.remove(getRealRouteIndex(index));
        listOfInputRoutes.add(getRealRouteIndex(index), route);
    }

    public void addRoute(Route route) {
        listOfInputRoutes.add(route);
    }

    public static CarbonModel getInstance() {
        return instance;
    }

    public Vehicle getVehicleFromHidden(int index){
        return listOfInputVehicles.get(index);
    }

    public Vehicle getVehicle(int index) {
        for (int i = 0; i < listOfHiddenVehicles.size(); i++) {
            if (listOfHiddenVehicles.get(i) <= index) {
                index++;
            }
        }
        return listOfInputVehicles.get(index);
    }

    public void hideVehicle(int index) {
        listOfHiddenVehicles.add(index);
    }

    public void editVehicle(Vehicle vehicle, int index) {
        listOfInputVehicles.remove(getRealVehicleIndex(index));
        listOfInputVehicles.add(getRealVehicleIndex(index), vehicle);
    }

    public int countBills() {
        return listOfBills.size();
    }

    public int countJourneys(){
        return listOfJourneys.size();
    }

    public int countCars() {
        return listOfInputVehicles.size() - listOfHiddenVehicles.size();
    }

    public int countAllCars() {
        return listOfInputVehicles.size();
    }

    public void addCar(Vehicle car) {
        KnownCars.getInstance().listOfKnownCars.add(car);
    }

    public Vehicle getCar(int i) {
        return KnownCars.getInstance().listOfKnownCars.get(i);
    }

    public List<String> getMakes(int index) {
        List<String> makes = new ArrayList<>();
        for (int i = 0; i < KnownCars.getInstance().listOfKnownCars.size(); i++) {
            Vehicle car = getCar(i);
            if (!makes.contains(car.getMake())) {
                makes.add(car.getMake());
            }
        }
        return makes;
    }

    public List<String> getModels(String make, int index) {
        List<String> models = new ArrayList<>();
        for (int i = 0; i < KnownCars.getInstance().listOfKnownCars.size(); i++) {
            Vehicle car = getCar(i);
            if (make.equals(car.getMake())) {
                if (!models.contains(car.getModel())) {
                    models.add(car.getModel());
                }
            }
        }
        return models;
    }

    public List<String> getYears(String model, int index) {
        List<String> years = new ArrayList<>();
        for (int i = 0; i < KnownCars.getInstance().listOfKnownCars.size(); i++) {
            Vehicle car = getCar(i);
            if (model.equals(car.getModel())) {
                if (!years.contains(car.getYear())) {
                    years.add(car.getYear());
                }
            }
        }
        return years;
    }

    public List<Vehicle> getRemainingCars(String make, String model, String year) {
        List<String> remainingCars = new ArrayList<>();
        List<Vehicle> vehiclesLeft = new ArrayList<>();
        for (int i = 0; i < KnownCars.getInstance().listOfKnownCars.size(); i++) {
            Vehicle car = getCar(i);
            if (make.equals(car.getMake())) {
                if (model.equals(car.getModel())) {
                    if (year.equals(car.getYear())) {
                        if (!remainingCars.contains(car.getTransmission())) {
                            if (!remainingCars.contains(car.getEngineDisplacement())) {
                                remainingCars.add(car.getTransmission());
                                remainingCars.add(car.getEngineDisplacement());
                                vehiclesLeft.add(car);
                            }
                        }
                    }
                }
            }
        }
        return vehiclesLeft;
    }

    public void fillList(int rows) {
        for (int i = 0; i < rows; i++) {
            KnownCars.getInstance().listOfKnownCars.add(new Vehicle());
        }
    }

    public void newBill(){
        listOfBills.add(new Bill(0,0,0,0,0,0,""));
    }

    public void newJourney(int in_vehicle, int in_route) {
        String temp_name = "temp";
        listOfJourneys.add(new Journey(temp_name, (in_vehicle), (in_route)));
    }

    private int getRealRouteIndex(int indexWithoutAccountingHidden) {
        for (int i = 0; i < listOfHiddenRoutes.size(); i++) {
            if (listOfHiddenRoutes.get(i) <= indexWithoutAccountingHidden) {
                indexWithoutAccountingHidden++;
            }
        }
        return indexWithoutAccountingHidden;
    }

    public int getRealVehicleIndex(int indexWithoutAccountingHidden) {
        for (int i = 0; i < listOfHiddenVehicles.size(); i++) {
            if (listOfHiddenVehicles.get(i) <= indexWithoutAccountingHidden) {
                indexWithoutAccountingHidden++;
            }
        }
        return indexWithoutAccountingHidden;
    }

    public void calculateCarbonEmissions(Journey journey) {
        double highwayMilesPerGallon = listOfInputVehicles.get(journey.getVehicleIndex()).getHighway();
        double cityMilesPerGallon = listOfInputVehicles.get(journey.getVehicleIndex()).getCity();
        double co2EmittedPerGallonOfFuel;

        if (listOfInputVehicles.get(journey.getVehicleIndex()).getFuelType().toLowerCase().contains("gasoline")) {
            co2EmittedPerGallonOfFuel = GASOLINE_CO2_EMISSION;
        } else if (listOfInputVehicles.get(journey.getVehicleIndex()).getFuelType().toLowerCase().contains("electricity")) {
            co2EmittedPerGallonOfFuel = ELECTRIC_CO2_EMISSION;
        } else if (listOfInputVehicles.get(journey.getVehicleIndex()).getFuelType().toLowerCase().contains("diesel")) {
            co2EmittedPerGallonOfFuel = DIESEL_CO2_EMISSION;
        } else if (listOfInputVehicles.get(journey.getVehicleIndex()).getFuelType().toLowerCase().contains("n/a")) {
            co2EmittedPerGallonOfFuel = ELECTRIC_CO2_EMISSION;
        } else if (listOfInputVehicles.get(journey.getVehicleIndex()).getFuelType().toLowerCase().contains("other")) {
            //input already converted
            co2EmittedPerGallonOfFuel = CO2_EMISSION;
        } else {
            throw new IllegalArgumentException(); //crash
        }
        float co2PerCity = (float) (((listOfInputRoutes.get(journey.getRouteIndex()).getCity() * kmToMiles) / cityMilesPerGallon) * co2EmittedPerGallonOfFuel);
        float co2PerHighway = (float) (((listOfInputRoutes.get(journey.getRouteIndex()).getHwy() * kmToMiles) / highwayMilesPerGallon) * co2EmittedPerGallonOfFuel);
        float totalCO2Emission = co2PerCity + co2PerHighway;

        journey.setCo2PerCity(co2PerCity);
        journey.setCo2PerHighway(co2PerHighway);
        journey.setTotalCO2Emission(totalCO2Emission);
    }

    public Journey getJourney(int index) {
        return listOfJourneys.get(index);
    }

    public Bill getBill(int index) {
        return listOfBills.get(index);
    }

    public int newBillIndex() {
        int i;
        for (i = 0; i < getSizeOfBillsList(); i++) {
            if (getBill(i).getNumberOfPeople() == 0) {
                break;
            }
        }
        return i;
    }

    public int newJourneyIndex() {
        int i;
        for (i = 0; i < getSizeOfJourneysList(); i++) {
            if (getJourney(i).getJourneyName() == "temp") {
                break;
            }
        }
        return i;
    }

    public void addJourneyName(int index, String name) {
        getJourney(index).setJourneyName(name);
    }

    //for integrating with ArrayAdapter
    public String[] getJourneyInfo() {
        String[] info = new String[getSizeOfJourneysList()];
        for (int i = 0; i < getSizeOfJourneysList(); i++) {
            Journey journey = getJourney(i);
            calculateCarbonEmissions(journey);
            info[i] = journey.getStringDate() + ", " + journey.getJourneyName() + ", " + listOfInputVehicles.get(journey.getVehicleIndex()).getName()
                    + ", " + listOfInputRoutes.get(journey.getRouteIndex()).getName() + ", " + journey.getTotalCO2Emission() + " kgC02";
        }
        return info;
    }

    public void deleteJourney(int index) {
        listOfJourneys.remove(index);
    }

    public void deleteBill(int index) {
        listOfBills.remove(index);
    }

    public int getSizeOfBillsList() {
        return listOfBills.size();
    }

    public int getSizeOfJourneysList() {
        return listOfJourneys.size();
    }

    public String getJourneyName(int index) {
        return listOfJourneys.get(index).getJourneyName();
    }


    public float getJourneyTotalCO2Emissions(int index) {
        return listOfJourneys.get(index).getTotalCO2Emission();
    }

    public void addVehicle(String name, String make, String model, String year, String city, String hwy, String fuelType, String transmission, String displacement) {
        Vehicle vehicle = new Vehicle();

        vehicle.setName(name);
        vehicle.setMake(make);
        vehicle.setModel(model);
        vehicle.setYear(year);
        vehicle.setCity(Double.parseDouble(city));
        vehicle.setHighway(Double.parseDouble(hwy));
        vehicle.setFuelType(fuelType);
        if (fuelType.equals("Electricity")) {
            vehicle.setTransmission("none");
            vehicle.setEngineDisplacement("none");
        } else {
            vehicle.setTransmission(transmission);
            vehicle.setEngineDisplacement(displacement);
        }
        listOfInputVehicles.add(vehicle);
    }

    public String getVehicleName(int index) {
        return listOfInputVehicles.get(index).getName();
    }

    public String getVehicleMake(int index) {
        return listOfInputVehicles.get(index).getMake();
    }

    public String getVehicleModel(int index) {
        return listOfInputVehicles.get(index).getModel();
    }

    public String getVehicleYear(int index) {
        return listOfInputVehicles.get(index).getYear();
    }


    public String getRouteName(int index) {
        return listOfInputRoutes.get(index).getName();
    }

    public float getRouteCityDistance(int index) {
        return listOfInputRoutes.get(index).getCity();
    }

    public float getRouteHwyDistance(int index) {
        return listOfInputRoutes.get(index).getHwy();
    }

    public String getJourneyRouteName(int journeyIndex) {
        return listOfInputRoutes.get((listOfJourneys.get(journeyIndex).getRouteIndex())).getName();
    }

    public String getJourneyVehicleName(int journeyIndex) {
        return listOfInputVehicles.get((listOfJourneys.get(journeyIndex).getVehicleIndex())).getName();
    }

    public String getJourneyDate(int journeyIndex) {
        return (listOfJourneys.get(journeyIndex)).getStringDate();
    }


    public int[] getYearMonthDayOfPreviousDate(int numberOfDays, int currentYear, int currentMonth, int currentDay){
        Day today = new Day(currentYear,currentMonth,currentDay);
        return today.getDayFromPast(numberOfDays);
    }
    public float getTotalElectricityC02Emissions(){
        float total = 0;
        for(int i = 0; i < listOfBills.size(); i++){
            Bill bill = listOfBills.get(i);
            if(bill.getType().equals("Electricity")){
                total = total + bill.getElectricityEmissions();
            }
        }
        return total;
    }

    public float getTotalGasC02Emissions(){
        float total = 0;
        for(int i = 0; i < listOfBills.size(); i++){
            Bill bill = listOfBills.get(i);
            if(bill.getType().equals("Natural Gas")){
                total = total + bill.getNaturalGasEmissions();
            }
        }
        return total;
    }

    public float getElectricityC02Emissions(int year, int month, int day) {
        Day date = new Day(year, month, day);
        float kgOfCO2 = 0;

        for (int i = 0; i < listOfBills.size(); i++) {
            if (listOfBills.get(i).getElectricityEmissions() != 0) { // is electric bill
                if (listOfBills.get(i).hasTheDayOf(date)) {
                    kgOfCO2 = listOfBills.get(i).getElectricityEmissions();
                    return kgOfCO2;
                }
            }
        }


        return getPreviousBillElectricity(date);
    }

    private float getPreviousBillElectricity(Day date) {
        Day closestDate = new Day(0, 0, 0);
        float closestkgOfCO2 = 0;

        for (int i = 0; i < listOfBills.size(); i++) {
            if (listOfBills.get(i).getElectricityEmissions() != 0) {
                if (listOfBills.get(i).getEndDate().getJulian() > closestDate.getJulian()
                        && listOfBills.get(i).getEndDate().getJulian() < date.getJulian()) {
                    closestDate = listOfBills.get(i).getEndDate();
                    closestkgOfCO2 = listOfBills.get(i).getElectricityEmissions();
                }
            }
        }

        return closestkgOfCO2;
    }

    public float getGasC02Emissions(int year, int month, int day) {
        Day date = new Day(year, month, day);

        for (int i = 0; i < listOfBills.size(); i++) {
            if (listOfBills.get(i).getNaturalGasEmissions() != 0) { // is electric bill
                if (listOfBills.get(i).hasTheDayOf(date)) {
                    return listOfBills.get(i).getNaturalGasEmissions();
                }
            }
        }

        return getPreviousBillNaturalGas(date);
    }

    private float getPreviousBillNaturalGas(Day date) {
        Day closestDate = new Day(0, 0, 0);
        float closestkgOfCO2 = 0;

        for (int i = 0; i < listOfBills.size(); i++) {
            if (listOfBills.get(i).getNaturalGasEmissions() != 0) {
                if (listOfBills.get(i).getEndDate().getJulian() > closestDate.getJulian()
                        && listOfBills.get(i).getEndDate().getJulian() < date.getJulian()) {
                    closestDate = listOfBills.get(i).getEndDate();
                    closestkgOfCO2 = listOfBills.get(i).getNaturalGasEmissions();
                }
            }
        }

        return closestkgOfCO2;
    }

    public int getJulian(int year, int month, int date) {
        Day day = new Day(year, month, date);
        return day.getJulian();
    }


    public void SaveData() {
        try {
            String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(fullPath, "CarbonTrackerData.bin")));

            oos.writeObject(this.getInstance());
            oos.flush();
            oos.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void LoadData() {
        String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        CarbonModel saved = (CarbonModel) openFile(new File(fullPath, "CarbonTrackerData.bin"));
        if (saved != null) {
            this.instance = saved;
        }
    }

    @Nullable
    private Object openFile(File f) {
        try
        {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
            Object o = ois.readObject();
            return o;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    //check how many journeys user has entered for current day
    public int numberOfJourneysOnCurrentDay(){
        Calendar calendar = Calendar.getInstance();
        int journeys=0;

        Day currentDay = new Day(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH));

        if(listOfBills.size()>0) {
            for (Journey j : listOfJourneys) {
                if (j.getDay().daysFrom(currentDay) == 0) {
                    journeys++;
                }
            }
        }

        return journeys;
    }

    //check if there is a already a bill for current day
    //if there is no bill for currentday check when was the last time user entered bill
    public boolean enteredBillOnCurrentDay(){
        boolean enteredBillToday = false;
        Calendar calendar = Calendar.getInstance();
        Day currentDay = new Day(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH));

        if(listOfBills.size()>0) {
            for (Bill b : listOfBills) {
                if (currentDay.daysFrom(b.getStartDate()) > 0 && currentDay.daysFrom(b.getEndDate()) < 0) {
                    enteredBillToday = true;
                }
            }
        }
        return enteredBillToday;
    }

}