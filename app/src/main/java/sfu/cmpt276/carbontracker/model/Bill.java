package sfu.cmpt276.carbontracker.model;


public class Bill {
    private float electricity;
    private float naturalGas;
    private int numberOfPeople;
    private Day startDate;
    private Day endDate;
    private int period;

    public Bill(float electricity, float naturalGas, int numberOfPeople, int period) {
        this.electricity = electricity;
        this.naturalGas = naturalGas;
        this.numberOfPeople = numberOfPeople;
        this.period = period;
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
}
