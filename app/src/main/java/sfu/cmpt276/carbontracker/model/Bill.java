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
    private final double electricityToKg = 9000;
    private final double kwhTogwh = 0.000001;
    private final double naturalGasToKg = 56.1;

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

    public boolean hasTheDayOf(Day date){
        if(date.getJulian() <=  endDate.getJulian() && date.getJulian() >= startDate.getJulian()){
            return true;
        } else {
            return false;
        }
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
    public float calculateTotalNaturalGas_kgCO2(){
        return (float) (naturalGasEmissions *naturalGasToKg);
    }
    public float calculateTotalElectricity_kgCO2(){
        return (float) (electricityEmissions *kwhTogwh * electricityToKg);
    }
    public float calculateNaturalGasPerPerson() {
        return (float) ((naturalGasEmissions / numberOfPeople) * naturalGasToKg);
    }

    public float calculateElectricityPerPerson() {
        return (float) (((electricityEmissions / numberOfPeople) * kwhTogwh) * electricityToKg);
    }

    public float calculateElectricityPerPersonPerDay(){
        electricityEmissions = calculateElectricityPerPerson()/period;
        return electricityEmissions;
    }

    public float calculateNaturalGasPerPersonPerDay(){
        naturalGasEmissions = calculateNaturalGasPerPerson()/period;
        return naturalGasEmissions;
    }

    public float calculateElectricityKgC02PerDay(){
        return (float) ((electricity*kwhTogwh*electricityToKg)/period);
    }

    public float calculateNaturalGasPerDay(){
        return (float) ((naturalGas*naturalGasToKg)/period);
    }

}
