package sfu.cmpt276.carbontracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import sfu.cmpt276.carbontracker.model.CarbonModel;
import sfu.cmpt276.carbontracker.model.Vehicle;

public class AddVehicleActivity extends AppCompatActivity {
    private String make;
    private String model;
    private String year;
    private List<Vehicle> outputCars = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        setupButtons();
        populateSpinnerMake();
        registerClickCallBackForMake();
        registerClickCallBackForModel();
        registerClickCallBackForYears();
        registerClickCallBackForList();
    }

    public void populateSpinnerMake(){
        ArrayAdapter<String> adapterMake = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, CarbonModel.getInstance().getMakes());
        Spinner spinnerMake = (Spinner) findViewById(R.id.spinnerSelectMake);
        spinnerMake.setAdapter(adapterMake);
    }

    public void registerClickCallBackForMake(){
        Spinner spinnerMake = (Spinner) findViewById(R.id.spinnerSelectMake);
        spinnerMake.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                make = (String)parent.getItemAtPosition(position);
                populateSpinnerModel();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void populateSpinnerModel(){
        ArrayAdapter<String> adapterModel = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, CarbonModel.getInstance().getModels(make));
        Spinner spinnerModel = (Spinner) findViewById(R.id.spinnerSelectModel);
        spinnerModel.setAdapter(adapterModel);
    }

    public void registerClickCallBackForModel(){
        Spinner spinnerModel = (Spinner) findViewById(R.id.spinnerSelectModel);
        spinnerModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                model = (String)parent.getItemAtPosition(position);
                populateSpinnerYear();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void populateSpinnerYear(){
        ArrayAdapter<String> adapterYear = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, CarbonModel.getInstance().getYears(model));
        Spinner spinnerYear = (Spinner) findViewById(R.id.spinnerSelectYear);
        spinnerYear.setAdapter(adapterYear);
    }

    public void registerClickCallBackForYears(){
        Spinner spinnerYear = (Spinner) findViewById(R.id.spinnerSelectYear);
        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                year = (String)parent.getItemAtPosition(position);
                populateListView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void populateListView(){
        outputCars = CarbonModel.getInstance().getRemainingCars(make, model, year);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, CarbonModel.getInstance().getRemainingCarInfo(outputCars));

        ListView list = (ListView) findViewById(R.id.listviewCars);
        list.setAdapter(adapter);
    }

    public void registerClickCallBackForList(){
        final ListView list = (ListView) findViewById(R.id.listviewCars);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //extract data from UI
                /*
                ListView listVehicles=(ListView)findViewById(R.id.listviewCars);
                String getClickedVehicle=CarbonModel.getInstance().getRemainingCars(make,model,year).get(position);

                String transmission=CarbonModel.getInstance().getTransmissionFromRemain(make,model,year).get(position);
                String engineDisplacement=CarbonModel.getInstance().getEngineDiplacementFromRemain(make,model,year);
                */
                Vehicle vehicle = outputCars.get(position);
                EditText carNameText = (EditText) findViewById(R.id.car_name);
                vehicle.setName(carNameText.getText().toString());

                if (checkInput(vehicle.getName()) == 0) {
                    setResult(Activity.RESULT_CANCELED);
                }else {

                    Intent intent = new Intent(AddVehicleActivity.this, SelectVehicleActivity.class);


                    intent.putExtra("vehicle_name", vehicle.getName());
                    intent.putExtra("vehicle_make", vehicle.getMake());
                    intent.putExtra("vehicle_model", vehicle.getModel());
                    intent.putExtra("vehicle_year", vehicle.getYear());
                    intent.putExtra("vehicle_city", Double.toString(vehicle.getCity()));
                    intent.putExtra("vehicle_hwy", Double.toString(vehicle.getHighway()));
                    intent.putExtra("vehicle_fuel", vehicle.getYear());
                    intent.putExtra("vehicle_transmission", vehicle.getTransmission());
                    intent.putExtra("vehicle_engineDisplacement", vehicle.getEngineDisplacement());
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                    // startActivity(intent);
                }

            }
        });
    }
    private int checkInput(String name) {
        //check if input is valid
        if (name.equals(null) || name.replaceAll("\\s+", "").equals("")) {
            Toast toast = Toast.makeText(getApplicationContext(), "Car name cannot be empty.", Toast.LENGTH_SHORT);
            toast.show();
            return 0;
        }
        return 1;
    }

    private void setupButtons() {
        /*add button now not necessary with short click to go back to select vehicle activity
        //add vehicle button
        Button btn_ok = (Button) findViewById(R.id.buttonAddVehicle);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if input is valid
                //save to shared preferences

                Intent intent = new Intent (AddVehicleActivity.this, SelectVehicleActivity.class);
                startActivity(intent);
            }
        });*/

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
    public static Intent makeIntent(Context context) {
        return new Intent(context, AddVehicleActivity.class);
    }
    //navigation back button
    @Override
    public void onBackPressed () {
        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }
}
