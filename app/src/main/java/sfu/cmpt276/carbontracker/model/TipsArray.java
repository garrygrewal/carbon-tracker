package sfu.cmpt276.carbontracker.model;

import java.util.Collections;

/**
 * Created by btian on 3/22/17.
 */

public class TipsArray {
    Tip[] tips = new Tip[15];

    public TipsArray() {
        for(Tip tip: tips){
            tip = new Tip("", 0, false);
        }
    }

    public void generateCarTip(float carEmissions, int numberOfVehicle){
        if(carEmissions != 0){

                String busInsteadTip = "You drove %d cars, if you bus instead.... ";

                String walkInsteadTip = "You drove %d cars, If you walk instead....";

                setTip(0, busInsteadTip);
                setTip(1, walkInsteadTip);
            }
    }
    public void generateElectricityTip(float electricityEmissions){
        String turnOffLightsTip = "You generate ,turning off the lights might help with your emissions ";
        String blanketTip = "You generate , consider using a blanket instead of turning down up the heat.";
        String clothesTip = "You generate ,consider wearing more clothes instead of turning up the heat.";
        //String turnOffLightsTip = "You generate ,turning off the lights might help with your emissions ";


        setTip(5, turnOffLightsTip);
        setTip(6, blanketTip);
        setTip(7, clothesTip);
    }

    public String getNextTipInfo(){

        for(int i = 0; i < tips.length; i++){

            if(tips[i].isExists()){
                if(tips[i].getCountdown() < 0){
                    decrementTipsCountdown();
                    tips[i].startCountDown();
                    return tips[i].getInfo();
                }
            }

        }
        return null;

    }


    private void decrementTipsCountdown(){
        for(Tip tip: tips){
            tip.decrementCountdown();
        }
    }
    private void setTip(int index, String info){
        tips[index].setExists(true);
        tips[index].setInfo(info);
    }

    public void generateNaturalGasTip(float naturalGasEmissions) {
        String turnOffLightsTip = "You generate ,turning off the lights might help with your emissions ";
        String blanketTip = "You generate , consider using a blanket instead of turning down up the heat.";
        String clothesTip = "You generate ,consider wearing more clothes instead of turning up the heat.";
        //String turnOffLightsTip = "You generate ,turning off the lights might help with your emissions ";


        setTip(8, turnOffLightsTip);
        setTip(9, blanketTip);
        setTip(10, clothesTip);
    }
}
