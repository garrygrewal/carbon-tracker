package sfu.cmpt276.carbontracker;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import sfu.cmpt276.carbontracker.model.CarbonModel;

/*
 * Main Menu Screen
 */

public class MainMenuActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1014;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        /////////////////////////////////////////////
    // CODE USED FOR TESTING REMOVE BEFORE SUBMISSION //
        //////////////////////////////////////////////
        CarbonModel.getInstance().initiateTest();

        setupButtons();
    }


    private void setupButtons() {
        //create journey button
        Button btn_new = (Button) findViewById(R.id.buttonCreateJourney);
        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MainMenuActivity.this, SelectVehicleActivity.class);
                startActivity(intent);
            }
        });

        //display total footprint button
        Button btn_total = (Button) findViewById(R.id.buttonDisplayTotal);
        btn_total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MainMenuActivity.this, TotalFootprintActivity.class);
                startActivity(intent);

            }
        });

    }

    //navigation back button
    @Override
    public void onBackPressed () {
        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);

        System.exit(0);
        //moveTaskToBack(true); sends the app to background instead
    }
}