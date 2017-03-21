package sfu.cmpt276.carbontracker.model;


public class Bill {
    private float electricityUse;
    private float naturalGasUse;
    private float electricityEmissions;
    private float naturalGasEmissions;
    private int numberOfPeople;
    private Day startDate;
    private Day endDate;
    private int period;
    private String type;

    public Bill(float electricityUse, float naturalGasUse, float electricityEmissions, float naturalGasEmissions, int numberOfPeople, int period, String type) {
        this.electricityUse = electricityUse;
        this.naturalGasUse = naturalGasUse;
        this.electricityEmissions = electricityEmissions;
        this.naturalGasEmissions = naturalGasEmissions;
        this.numberOfPeople = numberOfPeople;
        this.period = period;
        this.type = type;
    }

    public float getElectricityEmissions() {
        return electricityEmissions;
    }

    public void setElectricityEmissions(float electricityEmissions) {
        this.electricityEmissions = electricityEmissions;
    }

    public float getNaturalGasEmissions() {
        return naturalGasEmissions;
    }

    public void setNaturalGasEmissions(float naturalGasEmissions) {
        this.naturalGasEmissions = naturalGasEmissions;
    }

    public String getType(){
        return type;
    }
    public void setType(String type){
        this.type = type;
    }

    public float getElectricityUse() {
        return electricityUse;
    }

    public void setElectricityUse(float electricityUse) {
        this.electricityUse = electricityUse;
    }

    public float getNaturalGasUse() {
        return naturalGasUse;
    }

    public void setNaturalGasUse(float naturalGasUse) {
        this.naturalGasUse = naturalGasUse;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public void setStartDate(int year, int month, int day) {
        startDate = new Day(year, month, day);
    }

    public void setEndDate(int year, int month, int day) {
        endDate = new Day(year, month, day);
    }

    public Day getStartDate() {
        return startDate;
    }

    public Day getEndDate() {
        return endDate;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod() {
        period = endDate.daysFrom(startDate);
    }

    public float calculateNaturalGasPerPerson() {
        double naturalGasToKg = 56.1;
        naturalGasEmissions = (float) ((naturalGasEmissions / numberOfPeople) * naturalGasToKg);
        return naturalGasEmissions;
    }

    public float calculateElectricityPerPerson() {
        double electricityToKg = 9000;
        double kwhTogwh = 0.000001;
        electricityEmissions = (float) (((electricityEmissions / numberOfPeople) * kwhTogwh) * electricityToKg);
        return electricityEmissions;
    }

    public float calculateElectricityPerPersonPerDay(){
        electricityEmissions = calculateElectricityPerPerson()/period;
        return electricityEmissions;
    }

    public float calculateNaturalGasPerPersonPerDay(){
        naturalGasEmissions = calculateNaturalGasPerPerson()/period;
        return naturalGasEmissions;
    }
}
