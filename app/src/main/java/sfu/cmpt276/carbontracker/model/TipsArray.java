package sfu.cmpt276.carbontracker.model;

import java.util.Collections;

/**
 * Created by btian on 3/22/17.
 */

public class TipsArray {
    Tip[] tips = new Tip[15];

    public TipsArray() {
        for (int i = 0; i < 15; i++) {
            tips[i] = new Tip("", 0, false);
        }
    }

    public void generateCarTip(float journeyEmissions) {
        if (journeyEmissions != 0) {

            String walkInsteadTip = "You generated " + journeyEmissions + " kgCO2 in a single journey, consider walking to reduce your emissions.";
            String busInsteadTip = "You generated " + journeyEmissions + " kgCO2 in a single journey, consider busing to reduce your emissions.";
            String bikeInsteadTip = "You generated " + journeyEmissions + " kgCO2 in a single journey, consider biking to reduce your emissions.";
            String stayAtHome = "You generated " + journeyEmissions + " kgCO2 in a single journey, consider staying at home instead";

            setTip(0, busInsteadTip);
            setTip(1, walkInsteadTip);
            setTip(2, bikeInsteadTip);
            setTip(3, stayAtHome);
        }
    }

    public void generateElectricityTip(float electricityEmissions) {
        String turnOffLightsTip = "You generated " + electricityEmissions + " kgCO2 in a day,consider turning off the lights during the day to help with your emissions.";
        String blanketTip = "You generated " + electricityEmissions + " kgCO2 in a day, consider using a blanket instead of turning down up the heat.";
        String clothesTip = "You generated  " + electricityEmissions + " kgCO2 in a day, consider wearing more clothes instead of turning up the heat.";
        String insulateHome = "You generated  " + electricityEmissions + " kgCO2 in a day, consider insulating your home to reduce drafts and air leaks instead of turning up the heat.";
        String betterAppliances = "You generated  " + electricityEmissions + " kgCO2 in a day, consider purchasing appliances that have superior efficiency";
        String solar = "You generated  " + electricityEmissions + " kgCO2 in a day, consider installing solar panels to your roof.";

        setTip(4, solar);
        setTip(5, turnOffLightsTip);
        setTip(6, blanketTip);
        setTip(7, clothesTip);
        setTip(8, insulateHome);
        setTip(9, betterAppliances);
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
        return null;

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
        String turnOffLightsTip = "You generated " + naturalGasEmissions + " kgCO2 in a day,consider turning off the lights during the day to help with your emissions.";
        String blanketTip = "You generated " + naturalGasEmissions + " kgCO2 in a day, consider using a blanket instead of turning down up the heat.";
        String clothesTip = "You generated  " + naturalGasEmissions + " kgCO2 in a day, consider wearing more clothes instead of turning up the heat.";
        String insulateHome = "You generated  " + naturalGasEmissions + " kgCO2 in a day, consider insulating your home to reduce drafts and air leaks instead of turning up the heat.";
        String waterUsage = "You generated  " + naturalGasEmissions + " kgCO2 in a day, consider taking shorter baths.";

        setTip(10, turnOffLightsTip);
        setTip(11, blanketTip);
        setTip(12, clothesTip);
        setTip(13, insulateHome);
        setTip(14, waterUsage);
    }
}
