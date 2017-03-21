package sfu.cmpt276.carbontracker.model;

/**
 * Day Class, holds a day of the year
 */

public class Day {
    private int year;
    private int month;
    private int date;
    private boolean julianValid;
    private int julian;


    public Day(int year, int month, int date) {
        this.year = year;
        this.month = month;
        this.date = date;
        julianValid=false;
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

    public String getActualDate(){
        return getDate()+"/"+getMonth()+"/"+getYear();
    }

    public int daysFrom(Day other) {
        ensureJulian();
        other.ensureJulian();
        return julian - other.julian;
    }

    public void ensureJulian() {
        if (julianValid)
            return;
        julian = toJulian(year, month, date);
        julianValid = true;
    }

    public static int toJulian(int year, int month, int date) {
        int jy = year;
        if (year < 0)
            jy++;
        int jm = month;
        if (month > 2)
            jm++;
        else {
            jy--;
            jm += 13;
        }
        int jul = (int) (java.lang.Math.floor(365.25 * jy)
                + java.lang.Math.floor(30.6001 * jm) + date + 1720995.0);

        int IGREG = 15 + 31 * (10 + 12 * 1582);
        // Gregorian Calendar adopted Oct. 15, 1582

        if (date + 31 * (month + 12 * year) >= IGREG)
        // Change over to Gregorian calendar
        {
            int ja = (int) (0.01 * jy);
            jul += 2 - ja + (int) (0.25 * ja);
        }
        return jul;
    }
}
