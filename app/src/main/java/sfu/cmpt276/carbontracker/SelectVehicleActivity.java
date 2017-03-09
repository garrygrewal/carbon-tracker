package sfu.cmpt276.carbontracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import sfu.cmpt276.carbontracker.model.Route;
import sfu.cmpt276.carbontracker.model.Vehicle;


public class SelectVehicleActivity extends AppCompatActivity {

    //public static final int REQUEST_CODE = 1555;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_vehicle);

        CarbonModel.getInstance().fillList(getFileRows());
        readFile();
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
                intent.putExtra("car_index", position);
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
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getTitle().toString()) {
            case "Edit":
                // sends relevant information to AddVehicleActivity
                index = info.position;
                Intent intent = new Intent(SelectVehicleActivity.this, AddVehicleActivity.class);

                intent.putExtra("index",index);

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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.list_vehicle,CarbonModel.getInstance().getCarInfo());
        ListView car_list = (ListView) findViewById(R.id.ListViewVehicles);

        //List Adapter
        car_list.setAdapter(adapter);

        //Context Menu for long press
        registerForContextMenu(car_list);
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
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            //add new vehicle
            case 30:
                if(resultCode==Activity.RESULT_OK){
                    CarbonModel.getInstance().addVehicle(data.getStringExtra("vehicle_name"), data.getStringExtra("vehicle_make"),data.getStringExtra("vehicle_model"),data.getStringExtra("vehicle_year"),data.getStringExtra("vehicle_city"),data.getStringExtra("vehicle_hwy"),data.getStringExtra("vehicle_fuel"),data.getStringExtra("vehicle_transmission"),data.getStringExtra("vehicle_engineDisplacement"));
                    listCars();
                    break;
                }
                else
                    break;
            //edit clicked vehicle

            case 1100:
                if(resultCode==Activity.RESULT_OK){
                    Vehicle edited_vehicle = new Vehicle();
                    edited_vehicle.setName(data.getStringExtra("vehicle_name"));
                    edited_vehicle.setMake(data.getStringExtra("vehicle_make"));
                    edited_vehicle.setYear(data.getStringExtra("vehicle_model"));
                    edited_vehicle.setModel(data.getStringExtra("vehicle_year"));
                    edited_vehicle.setTransmission(data.getStringExtra("vehicle_transmission"));
                    edited_vehicle.setEngineDisplacement(data.getStringExtra("vehicle_engineDisplacement"));
                    double city=Double.parseDouble(data.getStringExtra("vehicle_city"));
                    edited_vehicle.setCity(city);
                    double highWay=Double.parseDouble(data.getStringExtra("vehicle_hwy"));
                    edited_vehicle.setHighway(highWay);
                    edited_vehicle.setFuelType(data.getStringExtra("vehicle_fuel"));
                    CarbonModel.getInstance().editVehicle(edited_vehicle,index);
                    listCars();
                    break;
                }
                else
                    break;

        }
    }

    private void setupButtons() {
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