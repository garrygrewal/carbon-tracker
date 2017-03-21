package sfu.cmpt276.carbontracker.model;


public class Bill {
    private float electricity;
    private float naturalGas;
    private int numberOfPeople;
    private Day startDate;
    private Day endDate;
    private int period;
    private String type;
    private final double electricityToKg = 9000;
    private final double kwhTogwh = 0.000001;
    private final double naturalGasToKg = 56.1;

    public Bill(float electricity, float naturalGas, int numberOfPeople, int period, String type) {
        this.electricity = electricity;
        this.naturalGas = naturalGas;
        this.numberOfPeople = numberOfPeople;
        this.period = period;
        this.type = type;
    }

    public String getType(){
        return type;
    }
    public void setType(String type){
        this.type = type;
    }

    public float getElectricity() {
        return electricity;
    }

    public void setElectricity(float electricity) {
        this.electricity = electricity;
    }

    public float getNaturalGas() {
        return naturalGas;
    }

    public void setNaturalGas(float naturalGas) {
        this.naturalGas = naturalGas;
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
        return (float) (naturalGas*naturalGasToKg);
    }
    public float calculateTotalElectricity_kgCO2(){
        return (float) (electricity *kwhTogwh * electricityToKg);
    }
    public float calculateNaturalGasPerPerson() {
        return (float) ((naturalGas / numberOfPeople) * naturalGasToKg);
    }

    public float calculateElectricityPerPerson() {
        return (float) (((electricity / numberOfPeople) * kwhTogwh) * electricityToKg);
    }

    public float calculateElectricityPerPersonPerDay(){
        float electricity = calculateElectricityPerPerson()/period;
        return electricity;
    }

    public float calculateNaturalGasPerPersonPerDay(){
        float naturalGas = calculateNaturalGasPerPerson()/period;
        return naturalGas;
    }

    public float calculateElectricityKgC02PerDay(){
        return (float) ((electricity*kwhTogwh*electricityToKg)/period);
    }

    public float calculateNaturalGasPerDay(){
        return (float) ((naturalGas*naturalGasToKg)/period);
    }

}
