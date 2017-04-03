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
import java.util.Date;
import java.util.GregorianCalendar;

import sfu.cmpt276.carbontracker.model.CarbonModel;
import sfu.cmpt276.carbontracker.model.TipsArray;


/*
 * Main Menu Screen
 */

public class MainMenuActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        setupButtons();
        pushNotificationSetup();
        Log.d("my app", "size of bills list : " +CarbonModel.getInstance().getSizeOfJourneysList());
    }

    public void pushNotificationSetup(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 21);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND,0);

        Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private void displayTips() {
        String message = CarbonModel.getInstance().getTipsArray().getNextTipInfo();
        if(message != null && !message.isEmpty()) {
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
                displayTips();
                Intent intent = new Intent(MainMenuActivity.this, SelectVehicleActivity.class);
                startActivity(intent);
            }
        });

        //display total footprint button
        Button btn_total = (Button) findViewById(R.id.buttonDisplayTotal);
        btn_total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayTips();
                Intent intent = new Intent(MainMenuActivity.this, TotalFootprintActivity.class);
                startActivity(intent);


            }
        });

        Button btn_bill = (Button) findViewById(R.id.buttonAddNewBill);
        btn_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayTips();
                Intent intent = new Intent(MainMenuActivity.this, AddBillActivity.class);
                startActivity(intent);
            }
        });

        Button btn_singleDayGraph = (Button) findViewById(R.id.buttonDisplayDay);
        btn_singleDayGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayTips();
                Intent intent = new Intent(MainMenuActivity.this, SingleDayGraphActivity.class);
                startActivity(intent);
            }
        });

        Button btn_multiDayGraph = (Button) findViewById(R.id.buttonDisplayMultiDay);
        btn_multiDayGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayTips();
                Intent intent = new Intent(MainMenuActivity.this, MultiDayGraphActivity.class);
                startActivity(intent);
            }
        });
    }

    //navigation back button
    @Override
    public void onBackPressed() {

        //System.exit(0);
        moveTaskToBack(true);// sends the app to background instead
    }
}