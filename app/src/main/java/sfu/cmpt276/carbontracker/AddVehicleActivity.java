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
import android.widget.ListView;
import android.widget.Spinner;

import sfu.cmpt276.carbontracker.model.CarbonModel;
import sfu.cmpt276.carbontracker.model.Vehicle;

public class AddVehicleActivity extends AppCompatActivity {
    private String make;
    private String model;
    private String year;

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
        Log.d("My activity", "Just created: " +CarbonModel.getInstance().getModels(make).size());
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, CarbonModel.getInstance().getRemainingCars(make,model,year));

        ListView list = (ListView) findViewById(R.id.listviewCars);
        list.setAdapter(adapter);
    }

    public void registerClickCallBackForList(){
        ListView list = (ListView) findViewById(R.id.listviewCars);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //extract data from UI
                ListView listVehicles=(ListView)findViewById(R.id.listviewCars);
                String getClickedVehicle=CarbonModel.getInstance().getRemainingCars(make,model,year).get(position);
                String transmission=CarbonModel.getInstance().getTransmissionFromRemain(make,model,year);
                String engineDisplacement=CarbonModel.getInstance().getEngineDiplacementFromRemain(make,model,year);

                Vehicle clicked_vehicle=new Vehicle();
                clicked_vehicle.setMake(make);
                clicked_vehicle.setModel(model);
                clicked_vehicle.setYear(year);
                clicked_vehicle.setTransmission(transmission);
                clicked_vehicle.setEngineDisplacement(engineDisplacement);

                Intent intent=new Intent(AddVehicleActivity.this,SelectVehicleActivity.class);
                intent.putExtra("vehicle make",make);
                intent.putExtra("vehicle model",model);
                intent.putExtra("vehicle year",year);
                intent.putExtra("vehicle transmission",transmission);
                intent.putExtra("vehicle engineDisplacement",engineDisplacement);
                setResult(Activity.RESULT_OK, intent);
                startActivity(intent);


            }
        });
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
