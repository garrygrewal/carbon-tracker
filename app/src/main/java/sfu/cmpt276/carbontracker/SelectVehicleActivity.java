package sfu.cmpt276.carbontracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import sfu.cmpt276.carbontracker.model.CarbonModel;
import sfu.cmpt276.carbontracker.model.Model;
import sfu.cmpt276.carbontracker.model.Year;

/*
 *Select Transportation Screen-
 * currently only reads in cvs files.
 */


public class SelectVehicleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_vehicle);
        readFile();
        setupButtons();
    }

    private void readFile() {
        InputStream is = getResources().openRawResource(R.raw.vehicles);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        int i = 0;
        String line = "";
        try {
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                String makeName = tokens[0];
                String modelName = tokens[1];
                int year = Integer.parseInt(tokens[2]);
                double city = Double.parseDouble(tokens[3]);
                double highway = Double.parseDouble(tokens[4]);
                double displ = Double.parseDouble(tokens[7]);
                Year modelYear = new Year(year, city, highway, tokens[5], tokens[6], displ);

                CarbonModel.getInstance().addMake(makeName, modelName, modelYear);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, SelectVehicleActivity.class);
    }

    private void setupButtons() {
        //add new vehicle button
        Button btn_new = (Button) findViewById(R.id.buttonAddVehicle);
        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectVehicleActivity.this, AddVehicleActivity.class);
                startActivity(intent);
            }
        });

        //cancel button
        Button btn_cancel = (Button) findViewById(R.id.buttonCancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//////////////////REMOVE THIS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                //temporary, for testing only
                Intent intent = new Intent(SelectVehicleActivity.this, SelectRouteActivity.class);
                startActivity(intent);

                //Intent intent = new Intent();
                //setResult(Activity.RESULT_CANCELED, intent);
                //finish();
            }
        });
    }

    //navigation back button
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }
}