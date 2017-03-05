package sfu.cmpt276.carbontracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import sfu.cmpt276.carbontracker.model.CarbonModel;

/*
 *Select Transportation Screen-
 * currently only reads in cvs files.
 */


public class SelectVehicleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_vehicle);

        CarbonModel.getInstance().fillList();
        readFile();
        setupButtons();
    }

    private void readFile() {
        InputStream is = getResources().openRawResource(R.raw.vehicles);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

        String line = "";
        try {
            reader.readLine();
            int i = 0;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");

                CarbonModel.getInstance().getCar(i).setMake(tokens[0]);
                CarbonModel.getInstance().getCar(i).setModel(tokens[1]);
                CarbonModel.getInstance().getCar(i).setYear(tokens[2]);
                CarbonModel.getInstance().getCar(i).setCity(Double.parseDouble(tokens[3]));
                CarbonModel.getInstance().getCar(i).setHighway(Double.parseDouble(tokens[4]));
                CarbonModel.getInstance().getCar(i).setFuelType(tokens[5]);
                if (CarbonModel.getInstance().getCar(i).getFuelType().equals("Electricity")) {
                    CarbonModel.getInstance().getCar(i).setTransmission("none");
                    CarbonModel.getInstance().getCar(i).setEngineDisplacement("none");
                } else {
                    CarbonModel.getInstance().getCar(i).setTransmission(tokens[6]);
                    CarbonModel.getInstance().getCar(i).setEngineDisplacement(tokens[7] + "L");
                }
                CarbonModel.getInstance().addCar(CarbonModel.getInstance().getCar(i));

                i++;
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
                Intent intent = new Intent();
                setResult(Activity.RESULT_CANCELED, intent);
                finish();
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
