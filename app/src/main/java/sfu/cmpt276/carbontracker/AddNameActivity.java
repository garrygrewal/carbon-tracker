package sfu.cmpt276.carbontracker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;

import java.util.Calendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import sfu.cmpt276.carbontracker.model.CarbonModel;
import sfu.cmpt276.carbontracker.model.Journey;

/*
Add name activity allows user to add name and date to journey
 */

public class AddNameActivity extends AppCompatActivity {

    private int new_journey_index;
    //boolean dateEntered = false;
    private int journey_index; // for editing

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_name);

        premakeJourney();
        showVehicleAndRoute();
        showEmissions();
        setupButtons();
        UiChangeListener();
    }

    private void showVehicleAndRoute() {
        TextView vehicle_name = (TextView) findViewById(R.id.textVehicleName);
        TextView route_name = (TextView) findViewById(R.id.textRouteName);

        vehicle_name.setText(CarbonModel.getInstance().getJourneyVehicleName(new_journey_index));
        route_name.setText(CarbonModel.getInstance().getJourneyRouteName(new_journey_index));
    }

    private void addName() {
        EditText in_name = (EditText) findViewById(R.id.journeyName);
        String name = in_name.getText().toString();

        CarbonModel.getInstance().getTipsArray().generateCarTip(CarbonModel.getInstance().getJourney(new_journey_index).getTotalCO2Emission());
        CarbonModel.getInstance().addJourneyName(new_journey_index, name);
    }

    private void showEmissions() {

        Journey journey = CarbonModel.getInstance().getJourney(new_journey_index);
        CarbonModel.getInstance().calculateCarbonEmissions(journey);

        TextView city_emis = (TextView) findViewById(R.id.city_emissions);
        TextView hwy_emis = (TextView) findViewById(R.id.hwy_emissions);
        TextView total_emis = (TextView) findViewById(R.id.total_emissions);

        if (!CarbonModel.getInstance().getHumanRelatableUnitEnabled()) {
            city_emis.setText(Double.toString(journey.getCo2PerCity()) + "Kg CO2");
            hwy_emis.setText(Double.toString(journey.getCo2PerHighway() ) + "Kg CO2");
            total_emis.setText(Double.toString(journey.getTotalCO2Emission()) + "Kg CO2");
        }
        else {
            city_emis.setText(Double.toString(journey.getTreesPerCity()) +" "+ getString(R.string.trees));
            hwy_emis.setText(Double.toString(journey.getTreesPerHighway()) +" "+ getString(R.string.trees));
            total_emis.setText(Double.toString(journey.getTotalTreesEmission()) +" "+getString(R.string.trees));
        }
    }


    private void premakeJourney() {
        Intent intent = getIntent();
        journey_index = intent.getIntExtra("journey_index", -1);
        if (journey_index != -1) {
            EditText name = (EditText) findViewById(R.id.journeyName);
            name.setText(CarbonModel.getInstance().getJourneyName(journey_index));
            new_journey_index = journey_index;
        }
        int route_index = intent.getIntExtra("route_index", 0);
        int vehicle_index = intent.getIntExtra("vehicle_index", 0);
        CarbonModel.getInstance().newJourney(vehicle_index, route_index);
        new_journey_index = CarbonModel.getInstance().newJourneyIndex();

        //vehicle icon
        TextView icon = (TextView) findViewById(R.id.iconView);
        switch(Integer.parseInt(CarbonModel.getInstance().getVehicle(vehicle_index).getIcon())) {
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

    }

    private void setupButtons() {
        //date picker
        final EditText date = (EditText) findViewById(R.id.date);
        final Calendar c = Calendar.getInstance();
        final int mYear = c.get(Calendar.YEAR); // current year
        final int mMonth = c.get(Calendar.MONTH); // current month
        final int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

        if (journey_index != -1) {
            date.setText(CarbonModel.getInstance().getJourneyDate(journey_index));
        }
        else {
            //set current date as default
            CarbonModel.getInstance().getJourney(new_journey_index).setDate(mYear, mMonth+1, mDay);
            date.setText(mDay + "/" + (mMonth + 1) + "/" + mYear);
        }

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // date picker dialog
                Dialog datePickerDialog = new DatePickerDialog(AddNameActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        CarbonModel.getInstance().getJourney(new_journey_index).setDate(year, monthOfYear+1, dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
                //dateEntered = true;
            }
        });

        //add journey button
        Button btn_ok = (Button) findViewById(R.id.buttonAddJourney);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInput() == 0) {
                    setResult(Activity.RESULT_CANCELED);
                } else if (journey_index != -1) {
                    addName();

                    CarbonModel.getInstance().deleteJourney(journey_index);
                    Intent intent = new Intent(AddNameActivity.this, TotalFootprintActivity.class);
                    startActivity(intent);

                    displayTips();
                    //save data
                    CarbonModel.getInstance().SaveData();
                }
                else {
                    addName();

                    Intent intent = new Intent(AddNameActivity.this, MainMenuActivity.class);
                    startActivity(intent);

                    displayTips();
                    //save data
                    CarbonModel.getInstance().SaveData();
                }
            }
        });


        //cancel button
        Button btn_cancel = (Button) findViewById(R.id.buttonCancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delete premade route
                CarbonModel.getInstance().deleteJourney(new_journey_index);

                Intent intent = new Intent();
                setResult(Activity.RESULT_CANCELED, intent);
                finish();
            }
        });
    }

    private void displayTips() {
        String message = CarbonModel.getInstance().getTipsArray().getNextTipInfo();
        if(message != null && !message.isEmpty()) {
            Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private int checkInput() {
        EditText in_name = (EditText) findViewById(R.id.journeyName);
        EditText in_date = (EditText) findViewById(R.id.date);
        String name = in_name.getText().toString();
        //check if input is valid
        /*
        if(dateEntered == false){
        Toast toast = Toast.makeText(getApplicationContext(), "Please enter a date." ,Toast.LENGTH_SHORT);
        toast.show();
        return 0;
        }
        */

        if (name.equals(null) || name.replaceAll("\\s+", "").equals("")) {
            Toast toast = Toast.makeText(getApplicationContext(), R.string.journeynameempty, Toast.LENGTH_SHORT);
            toast.show();
            return 0;
        }
        else if (in_date.equals("Select Date")) {
            Toast toast = Toast.makeText(getApplicationContext(), R.string.selectDateplease, Toast.LENGTH_SHORT);
            toast.show();
            return 0;
        }
        else
            return 1;


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
           startActivity(new Intent(AddNameActivity.this, AboutActivity.class));
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
        //delete premade route
        CarbonModel.getInstance().deleteJourney(new_journey_index);

        Intent intent = new Intent(AddNameActivity.this, SelectRouteActivity.class);
        setResult(Activity.RESULT_CANCELED, intent);
        startActivity(intent);
    }
}