package sfu.cmpt276.carbontracker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import java.util.Calendar;

import sfu.cmpt276.carbontracker.model.CarbonModel;
import sfu.cmpt276.carbontracker.model.Tip;


/*
 * Main Menu Screen
 */

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        setupButtons();
        regenerateTipsDiffLanguage();
        pushNotificationSetup();
    }

    public void pushNotificationSetup() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 21);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private void displayTips() {
        String message = CarbonModel.getInstance().getTipsArray().getNextTipInfo();
        if (message != null && !message.isEmpty()) {
            Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private void setupButtons() {
        //create journey button
        Button btn_new = (Button) findViewById(R.id.buttonCreateJourney);
        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //displayTips();
                Intent intent = new Intent(MainMenuActivity.this, SelectVehicleActivity.class);
                startActivity(intent);
            }
        });

        //display total footprint button
        Button btn_total = (Button) findViewById(R.id.buttonDisplayTotal);
        btn_total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //displayTips();
                Intent intent = new Intent(MainMenuActivity.this, TotalFootprintActivity.class);
                startActivity(intent);


            }
        });

        Button btn_bill = (Button) findViewById(R.id.buttonAddNewBill);
        btn_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //displayTips();
                Intent intent = new Intent(MainMenuActivity.this, AddBillActivity.class);
                startActivity(intent);
            }
        });

        Button btn_singleDayGraph = (Button) findViewById(R.id.buttonDisplayDay);
        btn_singleDayGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //displayTips();
                Intent intent = new Intent(MainMenuActivity.this, SingleDayGraphActivity.class);
                startActivity(intent);
            }
        });

        Button btn_multiDayGraph = (Button) findViewById(R.id.buttonDisplayMultiDay);
        btn_multiDayGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //displayTips();
                Intent intent = new Intent(MainMenuActivity.this, MultiDayGraphActivity.class);
                startActivity(intent);
            }
        });
    }

    //hide navigation bar
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            View mDecorView = getWindow().getDecorView();
            mDecorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    //navigation back button
    @Override
    public void onBackPressed() {

        System.exit(0);
        //moveTaskToBack(true);// sends the app to background instead
    }


    public void regenerateTipsDiffLanguage() {
        if (CarbonModel.getInstance().getTipsArray().tips[0].isExists()) {
            String text1 = App.getMyContext().getResources().getString(R.string.walkingTip, CarbonModel.getInstance().getHighestJourneyEmission());
            CarbonModel.getInstance().getTipsArray().setTip(0, text1);
        }
        if (CarbonModel.getInstance().getTipsArray().tips[1].isExists()) {
            String text2 = App.getMyContext().getResources().getString(R.string.busTip, CarbonModel.getInstance().getHighestJourneyEmission());
            CarbonModel.getInstance().getTipsArray().setTip(1, text2);
        }
        if (CarbonModel.getInstance().getTipsArray().tips[2].isExists()) {
            String text3 = App.getMyContext().getResources().getString(R.string.bikeTip, CarbonModel.getInstance().getHighestJourneyEmission());
            CarbonModel.getInstance().getTipsArray().setTip(2, text3);
        }
        if (CarbonModel.getInstance().getTipsArray().tips[3].isExists()) {
            String text4 = App.getMyContext().getResources().getString(R.string.stayTip, CarbonModel.getInstance().getHighestJourneyEmission());
            CarbonModel.getInstance().getTipsArray().setTip(3, text4);
        }
        if (CarbonModel.getInstance().getTipsArray().tips[4].isExists()) {
            String text5 = App.getMyContext().getResources().getString(R.string.lightTip, CarbonModel.getInstance().getHighestElectricityEmission());
            CarbonModel.getInstance().getTipsArray().setTip(4, text5);

        }
        if (CarbonModel.getInstance().getTipsArray().tips[5].isExists()) {
            String text6 = App.getMyContext().getResources().getString(R.string.blanketTip, CarbonModel.getInstance().getHighestElectricityEmission());
            CarbonModel.getInstance().getTipsArray().setTip(5, text6);

        }
        if (CarbonModel.getInstance().getTipsArray().tips[6].isExists()) {
            String text7 = App.getMyContext().getResources().getString(R.string.clothesTip, CarbonModel.getInstance().getHighestElectricityEmission());

            CarbonModel.getInstance().getTipsArray().setTip(6, text7);
        }
        if (CarbonModel.getInstance().getTipsArray().tips[7].isExists()) {
            String text8 = App.getMyContext().getResources().getString(R.string.insulateTip, CarbonModel.getInstance().getHighestElectricityEmission());

            CarbonModel.getInstance().getTipsArray().setTip(7, text8);
        }
        if (CarbonModel.getInstance().getTipsArray().tips[8].isExists()) {
            String text9 = App.getMyContext().getResources().getString(R.string.appliancesTip, CarbonModel.getInstance().getHighestElectricityEmission());

            CarbonModel.getInstance().getTipsArray().setTip(8, text9);
        }
        if (CarbonModel.getInstance().getTipsArray().tips[9].isExists()) {
            String text10 = App.getMyContext().getResources().getString(R.string.solarTip, CarbonModel.getInstance().getHighestElectricityEmission());

            CarbonModel.getInstance().getTipsArray().setTip(9, text10);
        }
        if (CarbonModel.getInstance().getTipsArray().tips[10].isExists()) {
            String text11 = App.getMyContext().getResources().getString(R.string.lightTip, CarbonModel.getInstance().getHighestNaturalGasEmission());

            CarbonModel.getInstance().getTipsArray().setTip(10, text11);
        }
        if (CarbonModel.getInstance().getTipsArray().tips[11].isExists()) {
            String text12 = App.getMyContext().getResources().getString(R.string.blanketTip, CarbonModel.getInstance().getHighestNaturalGasEmission());

            CarbonModel.getInstance().getTipsArray().setTip(11, text12);
        }
        if (CarbonModel.getInstance().getTipsArray().tips[12].isExists()) {
            String text13 = App.getMyContext().getResources().getString(R.string.clothesTip, CarbonModel.getInstance().getHighestNaturalGasEmission());

            CarbonModel.getInstance().getTipsArray().setTip(12, text13);
        }
        if (CarbonModel.getInstance().getTipsArray().tips[13].isExists()) {
            String text14 = App.getMyContext().getResources().getString(R.string.insulateTip, CarbonModel.getInstance().getHighestNaturalGasEmission());

            CarbonModel.getInstance().getTipsArray().setTip(13, text14);
        }
        if (CarbonModel.getInstance().getTipsArray().tips[14].isExists()) {
            String text15 = App.getMyContext().getResources().getString(R.string.waterTip, CarbonModel.getInstance().getHighestNaturalGasEmission());

            CarbonModel.getInstance().getTipsArray().setTip(14, text15);
        }
    }
}