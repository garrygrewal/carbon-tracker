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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import sfu.cmpt276.carbontracker.model.CarbonModel;
import sfu.cmpt276.carbontracker.model.Vehicle;


public class SelectVehicleActivity extends AppCompatActivity {

    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_vehicle);

        listCars();
        onListClick();
        setupButtons();
    }

    //clicking on a vehicle in the list
    private void onListClick() {
        final ListView vehicle_list = (ListView) findViewById(R.id.ListViewVehicles);
        vehicle_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(SelectVehicleActivity.this, SelectRouteActivity.class);
                intent.putExtra("car_index", CarbonModel.getInstance().getRealVehicleIndex(position));
                startActivity(intent);
            }
        });
    }

    //CONTEXT MENU
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Vehicle Options");
        menu.add(0, v.getId(), 0, "Edit");
        menu.add(0, v.getId(), 0, "Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getTitle().toString()) {
            case "Edit":
                // sends relevant information to AddVehicleActivity
                index = info.position;
                Intent intent = new Intent(SelectVehicleActivity.this, AddVehicleActivity.class);
                intent.putExtra("index", index);
                startActivityForResult(intent, 1100);
                break;
            case "Delete":
                CarbonModel.getInstance().hideVehicle(info.position);
                //does not actually delete
                //CarbonModel.getInstance().removeRoute(info.position);
                listCars();
                break;
            default:
                return false;
        }
        return true;
    }

    private void listCars() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_vehicle, CarbonModel.getInstance().getCarInfo());
        ListView car_list = (ListView) findViewById(R.id.ListViewVehicles);

        //List Adapter
        car_list.setAdapter(adapter);

        //Context Menu for long press
        registerForContextMenu(car_list);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //add new vehicle
            case 30:
                if (resultCode == Activity.RESULT_OK) {
                    CarbonModel.getInstance().addVehicle(data.getStringExtra("vehicle_name"), data.getStringExtra("vehicle_make"), data.getStringExtra("vehicle_model"), data.getStringExtra("vehicle_year"), data.getStringExtra("vehicle_city"), data.getStringExtra("vehicle_hwy"), data.getStringExtra("vehicle_fuel"), data.getStringExtra("vehicle_transmission"), data.getStringExtra("vehicle_engineDisplacement"));
                    listCars();
                    break;
                } else
                    break;
            //edit clicked vehicle
            case 1100:
                if (resultCode == Activity.RESULT_OK) {
                    Vehicle edited_vehicle = new Vehicle();
                    edited_vehicle.setName(data.getStringExtra("vehicle_name"));
                    edited_vehicle.setMake(data.getStringExtra("vehicle_make"));
                    edited_vehicle.setYear(data.getStringExtra("vehicle_model"));
                    edited_vehicle.setModel(data.getStringExtra("vehicle_year"));
                    edited_vehicle.setTransmission(data.getStringExtra("vehicle_transmission"));
                    edited_vehicle.setEngineDisplacement(data.getStringExtra("vehicle_engineDisplacement"));
                    double city = Double.parseDouble(data.getStringExtra("vehicle_city"));
                    edited_vehicle.setCity(city);
                    double highWay = Double.parseDouble(data.getStringExtra("vehicle_hwy"));
                    edited_vehicle.setHighway(highWay);
                    edited_vehicle.setFuelType(data.getStringExtra("vehicle_fuel"));
                    CarbonModel.getInstance().editVehicle(edited_vehicle, index);
                    listCars();
                    break;
                } else
                    break;

        }
    }

    private void setupButtons() {
        // walk/bike button
        Button walk = (Button) findViewById(R.id.buttonWalk);
        walk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CarbonModel.getInstance().addVehicle("Walk", "n/a", "n/a", "n/a", "0", "0", "n/a", "n/a", "n/a");
                int idx = CarbonModel.getInstance().countAllCars()-1;
                Intent intent = new Intent(SelectVehicleActivity.this, SelectRouteActivity.class);
                intent.putExtra("car_index", idx);
                CarbonModel.getInstance().hideVehicle(idx);
                startActivity(intent);
            }
        });

        // bus button
        Button bus = (Button) findViewById(R.id.buttonBus);
        bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Conversion base : 1 mpg = 6553.55772216 g/km CO2
                // - Assume 89g of CO2 emissions per km of bus travel.
                CarbonModel.getInstance().addVehicle("Bus", "n/a", "n/a", "n/a", "73.63548002427", "73.63548002427", "other", "n/a", "n/a");
                int idx = CarbonModel.getInstance().countAllCars()-1;
                Intent intent = new Intent(SelectVehicleActivity.this, SelectRouteActivity.class);
                intent.putExtra("car_index", idx);
                CarbonModel.getInstance().hideVehicle(idx);
                startActivity(intent);
            }
        });

        // skytrain button
        Button skytrain = (Button) findViewById(R.id.buttonSkytrain);
        skytrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // - Find some reasonable number of CO2 per km of Skytrain travel.
                // NEED MORE RESEARCH
                // PLACEHOLDER CO2----------------------------------------------------------------------------------------------------------------------------------------------------------
                CarbonModel.getInstance().addVehicle("SkyTrain", "n/a", "n/a", "n/a", "57.89", "57.89", "other", "n/a", "n/a");
                int idx = CarbonModel.getInstance().countAllCars()-1;
                Intent intent = new Intent(SelectVehicleActivity.this, SelectRouteActivity.class);
                intent.putExtra("car_index", idx);
                CarbonModel.getInstance().hideVehicle(idx);
                startActivity(intent);
            }
        });


        //add new vehicle button
        Button btn_new = (Button) findViewById(R.id.buttonAddVehicle);
        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AddVehicleActivity.makeIntent(SelectVehicleActivity.this);
                startActivityForResult(intent, 30);
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