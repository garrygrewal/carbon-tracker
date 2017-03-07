package sfu.cmpt276.carbontracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import sfu.cmpt276.carbontracker.model.CarbonModel;
import sfu.cmpt276.carbontracker.model.Vehicle;


public class SelectVehicleActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 1555;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_vehicle);

        CarbonModel.getInstance().fillList(getFileRows());
        readFile();
        //listCars();
        setupButtons();
    }

    private void listCars() {
        ArrayAdapter<String> adapter = new ArrayAdapter(this, R.layout.list_route, CarbonModel.getInstance().getCarInfo());
        ListView car_list = (ListView) findViewById(R.id.ListViewVehicles);

        //List Adapter
        car_list.setAdapter(adapter);

        //Context Menu for long press
        //registerForContextMenu(car_list);
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

    private int getFileRows(){
        InputStream is = getResources().openRawResource(R.raw.vehicles);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        int rows=-1;
        String line = "";

        try {
            while((line = reader.readLine()) != null)
            {
                rows++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rows;
    }

    /******Not sure which readFile function would be better to use
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

                CarbonModel.getInstance().addMake(makeName, modelName,year, city, highway, tokens[5], tokens[6], displ);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public static Intent makeIntent(Context context) {
        return new Intent(context, SelectVehicleActivity.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case REQUEST_CODE:
                if(resultCode==Activity.RESULT_OK){
                    Vehicle newVehicle=new Vehicle();
                    newVehicle.setMake(data.getStringExtra("vehicle make"));
                    newVehicle.setModel(data.getStringExtra("vehicle model"));
                    newVehicle.setYear(data.getStringExtra("vehicle year"));
                    newVehicle.setTransmission(data.getStringExtra("vehicle transmission"));
                    newVehicle.setEngineDisplacement(data.getStringExtra("vehicle engineDisplacement"));
                    CarbonModel.getInstance().addCar(newVehicle);
                    listCars();
                    break;
                }
        }
    }

    private void setupButtons() {
        //add new vehicle button
        Button btn_new = (Button) findViewById(R.id.buttonAddVehicle);
        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AddVehicleActivity.makeIntent(SelectVehicleActivity.this);
                startActivityForResult(intent, REQUEST_CODE);
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