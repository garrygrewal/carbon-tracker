package sfu.cmpt276.carbontracker.model;


public class Bill {
    private float electricity;
    private float naturalGas;
    private int numberOfPeople;
    private Day startDate;
    private Day endDate;
    private int period;
    private String type;

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
    public float getKGofCO2forADay(Day date){
        return electricity;
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
        naturalGas = (float) ((naturalGas / numberOfPeople) * naturalGasToKg);
        return naturalGas;
    }

    public float calculateElectricityPerPerson() {
        double electricityToKg = 9000;
        double kwhTogwh = 0.000001;
        electricity = (float) (((electricity / numberOfPeople) * kwhTogwh) * electricityToKg);
        return electricity;
    }

    public float calculateElectricityPerPersonPerDay(){
        float electricity = calculateElectricityPerPerson()/period;
        return electricity;
    }

    public float calculateNaturalGasPerPersonPerDay(){
        float naturalGas = calculateNaturalGasPerPerson()/period;
        return naturalGas;
    }
}
