package sfu.cmpt276.carbontracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import sfu.cmpt276.carbontracker.model.CarbonModel;

/*
 *Select Transportation Screen-
 * currently only reads in cvs files. Still need to create
 * drop down boxes and populate them with information from
 * cvs file, and using array adapters.
 */


public class SelectVehicleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_vehicle);

        CarbonModel.getInstance().fillList();
        readFile();
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
}
