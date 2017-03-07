package sfu.cmpt276.carbontracker.model;

/**
 * Created by Lester on 3/7/2017.
 */

public class Day {
    private int year;
    private int month;
    private int date;

    public Day(int year, int month, int date) {
        this.year = year;
        this.month = month;
        this.date = date;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getString() {
        return getYear() + "-" + getMonth() + "-" + getDate();
    }
}
