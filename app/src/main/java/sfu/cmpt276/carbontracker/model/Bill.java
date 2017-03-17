package sfu.cmpt276.carbontracker.model;



public class Bill {
    private float electricity;
    private float naturalGas;
    private int numberOfPeople;
    public Day startDate;
    public Day endDate;
    private int period;

    public Bill(float electricity, float naturalGas, int numberOfPeople, int period){
        this.electricity=electricity;
        this.naturalGas=naturalGas;
        this.numberOfPeople=numberOfPeople;
        this.period=period;
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

    public Day getEndDate() {
        return endDate;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod() {
        period = endDate.daysFrom(startDate);
    }
}
