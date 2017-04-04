package sfu.cmpt276.carbontracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

import sfu.cmpt276.carbontracker.model.CarbonModel;
import sfu.cmpt276.carbontracker.model.Vehicle;

/*
Allows user to add transportation method
 */

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
                Intent getExtra = getIntent();
                intent.putExtra("journey_index", getExtra.getIntExtra("journey_index", -1));
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
        ListView car_list = (ListView) findViewById(R.id.ListViewVehicles);

        //List Adapter
        ArrayList vehicleList = getVehicleList();
        car_list.setAdapter(new VehicleAdapter(this, vehicleList));

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
                    CarbonModel.getInstance().addVehicle(data.getStringExtra("vehicle_name"), data.getStringExtra("vehicle_make"), data.getStringExtra("vehicle_model"), data.getStringExtra("vehicle_year"), data.getStringExtra("vehicle_city"), data.getStringExtra("vehicle_hwy"), data.getStringExtra("vehicle_fuel"), data.getStringExtra("vehicle_transmission"), data.getStringExtra("vehicle_engineDisplacement"), data.getStringExtra("vehicle_icon"));
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
                    edited_vehicle.setIcon(data.getStringExtra("vehicle_icon"));
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
                CarbonModel.getInstance().addVehicle("Walk", "n/a", "n/a", "n/a", "0", "0", "n/a", "n/a", "n/a", "5");
                int idx = CarbonModel.getInstance().countAllCars()-1;
                Intent intent = new Intent(SelectVehicleActivity.this, SelectRouteActivity.class);
                intent.putExtra("car_index", idx);
                CarbonModel.getInstance().hideVehicle(idx);
                Intent getExtra = getIntent();
                intent.putExtra("journey_index", getExtra.getIntExtra("journey_index", -1));
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
                CarbonModel.getInstance().addVehicle("Bus", "n/a", "n/a", "n/a", "73.63548002427", "73.63548002427", "other", "n/a", "n/a", "6");
                int idx = CarbonModel.getInstance().countAllCars()-1;
                Intent intent = new Intent(SelectVehicleActivity.this, SelectRouteActivity.class);
                intent.putExtra("car_index", idx);
                CarbonModel.getInstance().hideVehicle(idx);
                Intent getExtra = getIntent();
                intent.putExtra("journey_index", getExtra.getIntExtra("journey_index", -1));
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
                CarbonModel.getInstance().addVehicle("SkyTrain", "n/a", "n/a", "n/a", "57.89", "57.89", "other", "n/a", "n/a", "7");
                int idx = CarbonModel.getInstance().countAllCars()-1;
                Intent intent = new Intent(SelectVehicleActivity.this, SelectRouteActivity.class);
                intent.putExtra("car_index", idx);
                CarbonModel.getInstance().hideVehicle(idx);
                Intent getExtra = getIntent();
                intent.putExtra("journey_index", getExtra.getIntExtra("journey_index", -1));
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

    private ArrayList getVehicleList() {
        ArrayList<Vehicle> result = new ArrayList<Vehicle>();
        for (int i=0; i<CarbonModel.getInstance().countCars(); i++) {
            result.add(CarbonModel.getInstance().getVehicle(i));
        }
        return result;
    }

    private class VehicleAdapter extends BaseAdapter {
        private ArrayList<Vehicle> listData;
        private LayoutInflater layoutInflater;

        public VehicleAdapter(Context aContext, ArrayList<Vehicle> listData) {
            this.listData = listData;
            layoutInflater = LayoutInflater.from(aContext);
        }

        @Override
        public int getCount() {
            return listData.size();
        }

        @Override
        public Object getItem(int position) {
            return listData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.list_vehicle, null);
                holder = new ViewHolder();
                holder.name = (TextView) convertView.findViewById(R.id.car_name);
                holder.model = (TextView) convertView.findViewById(R.id.car_model);
                holder.details = (TextView) convertView.findViewById(R.id.car_details);
                //holder.mpg = (TextView) convertView.findViewById(R.id.car_mpg);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Vehicle vehicle = listData.get(position);
            holder.name.setText(vehicle.getName());

            //icon
            TextView icon = (TextView) convertView.findViewById(R.id.car_mpg);
            int i = Integer.parseInt(vehicle.getIcon());
            switch(i) {
                case 0:
                    icon.setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.coupe, 0, 0, 0);
                    break;
                case 1:
                    icon.setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.hatch, 0, 0, 0);
                    break;
                case 2:
                    icon.setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.suv, 0, 0, 0);
                    break;
                case 3:
                    icon.setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.van, 0, 0, 0);
                    break;
                case 4:
                    icon.setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.truck, 0, 0, 0);
                    break;
                case 5:
                    icon.setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.bike, 0, 0, 0);
                    break;
                case 6:
                    icon.setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.bus, 0, 0, 0);
                    break;
                case 7:
                    icon.setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.train, 0, 0, 0);
                    break;
                default:
                    icon.setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.hatch, 0, 0, 0);
                    break;
            }

            holder.model.setText(vehicle.getYear() + " " + vehicle.getMake() + " " + vehicle.getModel());
            holder.details.setText(vehicle.getEngineDisplacement() + " " + vehicle.getTransmission() + ", " + vehicle.getFuelType());
            //holder.mpg.setText(vehicle.getCity() + " City MPG, " + vehicle.getHighway() + " Highway MPG");
            return convertView;
        }

        class ViewHolder {
            TextView name;
            TextView model;
            TextView details;
            TextView mpg;
        }
    }

    //action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.add:
            Intent intent = AddVehicleActivity.makeIntent(SelectVehicleActivity.this);
            startActivityForResult(intent, 30);
            return(true);
        case R.id.reset:
            Intent back = new Intent();
            setResult(Activity.RESULT_CANCELED, back);
            finish();
            return(true);
        case R.id.about:
            //waiting for about page implementation
            //startActivity(new Intent(SelectVehicleActivity.this, AboutActivity.class));
            return(true);
        case R.id.exit:
            System.exit(0);
            return(true);
    }
        return(super.onOptionsItemSelected(item));
    }

    //hide navigation bar
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            View mDecorView = getWindow().getDecorView();
            mDecorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    //navigation back button
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }
}