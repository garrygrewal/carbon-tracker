package sfu.cmpt276.carbontracker.model;

/**
 * Day Class, holds a day of the year
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

    public int getMonth() {
        return month;
    }

    public int getDate() {
        return date;
    }

    public String getString() {
        return getYear() + "-" + getMonth() + "-" + getDate();
    }
}
