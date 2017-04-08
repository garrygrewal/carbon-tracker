package sfu.cmpt276.carbontracker;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.media.Image;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sfu.cmpt276.carbontracker.model.CarbonModel;
import sfu.cmpt276.carbontracker.model.Vehicle;

public class AddVehicleActivity extends AppCompatActivity {
    private String make;
    private String model;
    private String year;
    private int index;
    private int icon;
    private List<Vehicle> outputCars = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        //prevents keyboard from appearing when activity stars
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //program crashes b/c of this line when list of input vehicles is 0
        //get preselected icon
        //icon = Integer.parseInt(CarbonModel.getInstance().getVehicleIcon(index));

        //hides navigation bar after keyboard
        UiChangeListener();
        setupButtons();
        Intent intent = getIntent();
        index = intent.getIntExtra("index", -1);
        extractDataFromIntent();
        populateSpinnerMake(index);
        registerClickCallBackForMake();
        registerClickCallBackForModel();
        registerClickCallBackForYears();
        registerClickCallBackForList();
    }

    private void extractDataFromIntent() {


        if ((index >= 0)) {
            String name = CarbonModel.getInstance().getVehicleName(index);
            EditText input_name = (EditText) findViewById(R.id.bill_type);
            input_name.setText(name);
            make = CarbonModel.getInstance().getVehicleMake(index);
            model = CarbonModel.getInstance().getVehicleModel(index);
            year = CarbonModel.getInstance().getVehicleYear(index);
            populateListView();
        }
    }


    public void populateSpinnerMake(int index) {
        ArrayAdapter<String> adapterMake = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, CarbonModel.getInstance().getMakes(index));
        Spinner spinnerMake = (Spinner) findViewById(R.id.spinnerSelectMake);
        spinnerMake.setAdapter(adapterMake);
        if ((index >= 0)) {
            int spinnerPosition = adapterMake.getPosition(make);
            spinnerMake.setSelection(spinnerPosition);
        }
    }

    public void registerClickCallBackForMake() {
        Spinner spinnerMake = (Spinner) findViewById(R.id.spinnerSelectMake);
        spinnerMake.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                make = (String) parent.getItemAtPosition(position);
                populateSpinnerModel();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void populateSpinnerModel() {
        ArrayAdapter<String> adapterModel = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, CarbonModel.getInstance().getModels(make, index));
        Spinner spinnerModel = (Spinner) findViewById(R.id.spinnerSelectModel);
        spinnerModel.setAdapter(adapterModel);
        if ((index >= 0)) {
            int spinnerPosition = adapterModel.getPosition(model);
            spinnerModel.setSelection(spinnerPosition);
        }
    }

    public void registerClickCallBackForModel() {
        Spinner spinnerModel = (Spinner) findViewById(R.id.spinnerSelectModel);
        spinnerModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                model = (String) parent.getItemAtPosition(position);
                populateSpinnerYear();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void populateSpinnerYear() {
        ArrayAdapter<String> adapterYear = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, CarbonModel.getInstance().getYears(model, index));
        Spinner spinnerYear = (Spinner) findViewById(R.id.spinnerSelectYear);
        spinnerYear.setAdapter(adapterYear);
        if ((index >= 0)) {
            int spinnerPosition = adapterYear.getPosition(year);
            spinnerYear.setSelection(spinnerPosition);
        }
    }

    public void registerClickCallBackForYears() {
        Spinner spinnerYear = (Spinner) findViewById(R.id.spinnerSelectYear);
        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                year = (String) parent.getItemAtPosition(position);
                populateListView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void populateListView() {
        ListView list = (ListView) findViewById(R.id.listviewCars);

        outputCars = CarbonModel.getInstance().getRemainingCars(make, model, year);

        list.setAdapter(new VehicleAdapter(this, (new ArrayList<>(outputCars))));

    }

    public void registerClickCallBackForList() {
        final ListView list = (ListView) findViewById(R.id.listviewCars);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Vehicle vehicle = outputCars.get(position);
                EditText carNameText = (EditText) findViewById(R.id.bill_type);
                vehicle.setName(carNameText.getText().toString());

                if (checkInput(vehicle.getName()) == 0) {
                    setResult(Activity.RESULT_CANCELED);
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("vehicle_name", vehicle.getName());
                    intent.putExtra("vehicle_make", vehicle.getMake());
                    intent.putExtra("vehicle_model", vehicle.getModel());
                    intent.putExtra("vehicle_year", vehicle.getYear());
                    intent.putExtra("vehicle_city", Double.toString(vehicle.getCity()));
                    intent.putExtra("vehicle_hwy", Double.toString(vehicle.getHighway()));
                    intent.putExtra("vehicle_fuel", vehicle.getFuelType());
                    intent.putExtra("vehicle_transmission", vehicle.getTransmission());
                    intent.putExtra("vehicle_engineDisplacement", vehicle.getEngineDisplacement());
                    intent.putExtra("vehicle_icon", Integer.toString(icon)); //vehicle icon global variable
                    setResult(Activity.RESULT_OK, intent);
                    finish();
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
        //set icon button
        final ImageButton iconBtn = (ImageButton) findViewById(R.id.iconButton);
        //set icon
        switch(icon) {
            case 0:
                iconBtn.setBackgroundResource(R.mipmap.coupe);
                break;
            case 1:
                iconBtn.setBackgroundResource(R.mipmap.hatch);
                break;
            case 2:
                iconBtn.setBackgroundResource(R.mipmap.suv);
                break;
            case 3:
                iconBtn.setBackgroundResource(R.mipmap.van);
                break;
            case 4:
                iconBtn.setBackgroundResource(R.mipmap.truck);
                break;
            default:
                iconBtn.setBackgroundResource(R.mipmap.hatch);
                break;
        }
        iconBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String [] items = new String[] {"Coupe", "Hatchback", "Suv", "Van", "Truck"};
                final Integer[] icons = new Integer[] {R.mipmap.coupe, R.mipmap.hatch, R.mipmap.suv, R.mipmap.van, R.mipmap.truck};
                ListAdapter adapter = new ArrayAdapterWithIcon(AddVehicleActivity.this, items, icons);

                new AlertDialog.Builder(AddVehicleActivity.this).setTitle("Select Vehicle Icon")
                        .setAdapter(adapter, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item ) {
                                //Toast.makeText(AddVehicleActivity.this, "Item Selected: " + item, Toast.LENGTH_SHORT).show();
                                icon = item;
                                //change to selected icon
                                switch(icon) {
                                    case 0:
                                        iconBtn.setBackgroundResource(R.mipmap.coupe);
                                        break;
                                    case 1:
                                        iconBtn.setBackgroundResource(R.mipmap.hatch);
                                        break;
                                    case 2:
                                        iconBtn.setBackgroundResource(R.mipmap.suv);
                                        break;
                                    case 3:
                                        iconBtn.setBackgroundResource(R.mipmap.van);
                                        break;
                                    case 4:
                                        iconBtn.setBackgroundResource(R.mipmap.truck);
                                        break;
                                    default:
                                        iconBtn.setBackgroundResource(R.mipmap.hatch);
                                        break;
                                }
                            }
                        }).show();
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

    //icon select dialog adapter
    public class ArrayAdapterWithIcon extends ArrayAdapter<String> {

        private List<Integer> images;

        public ArrayAdapterWithIcon(Context context, String[] items, Integer[] images) {
            super(context, android.R.layout.select_dialog_item, items);
            this.images = Arrays.asList(images);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            TextView textView = (TextView) view.findViewById(android.R.id.text1);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                textView.setCompoundDrawablesRelativeWithIntrinsicBounds(images.get(position), 0, 0, 0);
            } else {
                textView.setCompoundDrawablesWithIntrinsicBounds(images.get(position), 0, 0, 0);
            }
            textView.setCompoundDrawablePadding(
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, getContext().getResources().getDisplayMetrics()));
            return view;
        }

    }



    public static Intent makeIntent(Context context) {
        return new Intent(context, AddVehicleActivity.class);
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
            AddVehicleActivity.VehicleAdapter.ViewHolder holder;
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.list_add_vehicle, null);
                holder = new AddVehicleActivity.VehicleAdapter.ViewHolder();
                holder.model = (TextView) convertView.findViewById(R.id.car_model);
                holder.details = (TextView) convertView.findViewById(R.id.car_details);
                convertView.setTag(holder);
            } else {
                holder = (AddVehicleActivity.VehicleAdapter.ViewHolder) convertView.getTag();
            }

            Vehicle vehicle = listData.get(position);
            holder.model.setText(vehicle.getYear() + " " + vehicle.getMake() + " " + vehicle.getModel());
            holder.details.setText(vehicle.getEngineDisplacement() + " " + vehicle.getTransmission() + ", " + vehicle.getFuelType());
            return convertView;
        }

        class ViewHolder {
            TextView model;
            TextView details;
        }
    }

    //action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.reset:
            Intent back = new Intent();
            setResult(Activity.RESULT_CANCELED, back);
            finish();
            return(true);
        case R.id.about:
            startActivity(new Intent(AddVehicleActivity.this, AboutActivity.class));
            return(true);
        case R.id.exit:
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
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
    public void UiChangeListener()
    {
        final View decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener (new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                    decorView.setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                }
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
