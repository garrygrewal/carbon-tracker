package sfu.cmpt276.carbontracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;



import sfu.cmpt276.carbontracker.model.CarbonModel;


/*
 * Main Menu Screen
 */

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        setupButtons();
    }

    private void setupButtons() {
        //create journey button
        Button btn_new = (Button) findViewById(R.id.buttonCreateJourney);
        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, SelectVehicleActivity.class);
                startActivity(intent);
            }
        });

        //display total footprint button
        Button btn_total = (Button) findViewById(R.id.buttonDisplayTotal);
        btn_total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, TotalFootprintActivity.class);
                startActivity(intent);

            }
        });

        Button btn_bill = (Button) findViewById(R.id.buttonAddNewBill);
        btn_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, AddBillActivity.class);
                startActivity(intent);
            }
        });

        Button btn_singleDayGraph = (Button) findViewById(R.id.buttonDisplayDay);
        btn_singleDayGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, SingleDayGraphActivity.class);
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