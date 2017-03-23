package sfu.cmpt276.carbontracker.model;

import java.io.Serializable;

/**
 * Tip class holds the countdown till next available output.
 */

public class Tip implements Serializable{
    private String info;
    private int countdown;
    boolean exists;

    public Tip(String info, int countdown, boolean exists) {
        this.info = info;
        this.countdown = countdown;
        this.exists = exists;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
    public void startCountDown(){
        countdown = 7;
    }
    public int getCountdown() {
        return countdown;
    }

    public void decrementCountdown() {
        countdown = countdown - 1;
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }
}
