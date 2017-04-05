package sfu.cmpt276.carbontracker.model;

import android.content.res.Resources;

import java.io.Serializable;
import java.util.Collections;

import sfu.cmpt276.carbontracker.App;
import sfu.cmpt276.carbontracker.R;

/**
 * Creates and holds all tips
 */

public class TipsArray implements Serializable{
    private Tip[] tips = new Tip[15];
    private float highestJourneyEmission;
    private float highestElectricityEmission;
    private float highestNaturalGasEmssion;


    public TipsArray() {
        for (int i = 0; i < 15; i++) {
            tips[i] = new Tip("", 0, false);
        }
        highestElectricityEmission = 0;
        highestJourneyEmission = 0;
        highestNaturalGasEmssion = 0;
    }

    public void generateCarTip(float journeyEmissions) {
        if (journeyEmissions != 0) {
            if(journeyEmissions > highestJourneyEmission) {
                String walkInsteadTip = App.getContext().getResources().getString(R.string.walkingTip, journeyEmissions);
                String busInsteadTip = App.getContext().getResources().getString(R.string.busTip, journeyEmissions);
                String bikeInsteadTip = App.getContext().getResources().getString(R.string.bikeTip, journeyEmissions);
                String stayAtHome = App.getContext().getResources().getString(R.string.stayTip, journeyEmissions);

                setTip(0, busInsteadTip);
                setTip(1, walkInsteadTip);
                setTip(2, bikeInsteadTip);
                setTip(3, stayAtHome);
                highestJourneyEmission = journeyEmissions;
            }
        }
    }

    public void generateElectricityTip(float electricityEmissions) {
        if(highestElectricityEmission < electricityEmissions) {
            String turnOffLightsTip =App.getContext().getResources().getString(R.string.lightTip, electricityEmissions);
            String blanketTip = App.getContext().getResources().getString(R.string.blanketTip, electricityEmissions);
            String clothesTip =App.getContext().getResources().getString(R.string.clothesTip, electricityEmissions);
            String insulateHome =App.getContext().getResources().getString(R.string.insulateTip, electricityEmissions);
            String betterAppliances = App.getContext().getResources().getString(R.string.appliancesTip, electricityEmissions);
            String solar = App.getContext().getResources().getString(R.string.solarTip, electricityEmissions);

            setTip(4, solar);
            setTip(5, turnOffLightsTip);
            setTip(6, blanketTip);
            setTip(7, clothesTip);
            setTip(8, insulateHome);
            setTip(9, betterAppliances);
        }
    }

    public String getNextTipInfo() {

        for (int i = 0; i < tips.length; i++) {

            if (tips[i].isExists()) {
                if (tips[i].getCountdown() <= 0) {
                    decrementTipsCountdown();
                    tips[i].startCountDown();

                    return tips[i].getInfo();
                }
            }

        }
        return "";

    }


    private void decrementTipsCountdown() {
        for (Tip tip : tips) {
            tip.decrementCountdown();
        }
    }

    private void setTip(int index, String info) {
        tips[index].setExists(true);
        tips[index].setInfo(info);
    }

    public void generateNaturalGasTip(float naturalGasEmissions) {
        if(highestNaturalGasEmssion < naturalGasEmissions) {
            String turnOffLightsTip = App.getContext().getResources().getString(R.string.lightTip, naturalGasEmissions);
            String blanketTip = App.getContext().getResources().getString(R.string.blanketTip, naturalGasEmissions);
            String clothesTip = App.getContext().getResources().getString(R.string.clothesTip, naturalGasEmissions);
            String insulateHome = App.getContext().getResources().getString(R.string.insulateTip, naturalGasEmissions);
            String waterUsage = App.getContext().getResources().getString(R.string.waterTip, naturalGasEmissions);

            setTip(10, turnOffLightsTip);
            setTip(11, blanketTip);
            setTip(12, clothesTip);
            setTip(13, insulateHome);
            setTip(14, waterUsage);
        }
    }
}
