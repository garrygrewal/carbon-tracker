package sfu.cmpt276.carbontracker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;

import java.util.Calendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import sfu.cmpt276.carbontracker.model.CarbonModel;
import sfu.cmpt276.carbontracker.model.Journey;

public class AddNameActivity extends AppCompatActivity {

    private int new_journey_index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_name);

        premakeJourney();
        showVehicleAndRoute();
        showEmissions();
        setupButtons();
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
        CarbonModel.getInstance().addJourneyName(new_journey_index, name);

    }

    private void showEmissions() {

        Journey journey = CarbonModel.getInstance().getJourney(new_journey_index);
        CarbonModel.getInstance().calculateCarbonEmissions(journey);
        TextView city_emis = (TextView) findViewById(R.id.city_emissions);
        city_emis.setText(Double.toString(journey.getCo2PerCity()));
        TextView hwy_emis = (TextView) findViewById(R.id.hwy_emissions);
        hwy_emis.setText(Double.toString(journey.getCo2PerHighway()));
        TextView total_emis = (TextView) findViewById(R.id.total_emissions);
        total_emis.setText(Double.toString(journey.getTotalCO2Emission()));

    }


    private void premakeJourney() {
        new_journey_index = CarbonModel.getInstance().newJourneyIndex();
        Intent intent = getIntent();
        int route_index = intent.getIntExtra("route_index", 0);
        int vehicle_index = intent.getIntExtra("vehicle_index", 0);
        CarbonModel.getInstance().newJourney(vehicle_index, route_index);
    }

    private void setupButtons() {
        //date picker
        final EditText date = (EditText) findViewById(R.id.date);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                Dialog datePickerDialog = new DatePickerDialog(AddNameActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        CarbonModel.getInstance().getJourney(new_journey_index).setDate(year, monthOfYear, dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        //add journey button
        Button btn_ok = (Button) findViewById(R.id.buttonAddJourney);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInput() == 0) {
                    setResult(Activity.RESULT_CANCELED);
                } else {
                    addName();

                    Intent intent = new Intent(AddNameActivity.this, MainMenuActivity.class);
                    startActivity(intent);
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

    private int checkInput() {
        EditText in_name = (EditText) findViewById(R.id.journeyName);
        EditText in_date = (EditText) findViewById(R.id.date);
        String name = in_name.getText().toString();
        //check if input is valid
        if (name.equals(null) || name.replaceAll("\\s+", "").equals("")) {
            Toast toast = Toast.makeText(getApplicationContext(), "Journey name cannot be empty.", Toast.LENGTH_SHORT);
            toast.show();
            return 0;
        }
        else if (in_date.equals("Select Date")) {
            Toast toast = Toast.makeText(getApplicationContext(), "Please select date.", Toast.LENGTH_SHORT);
            toast.show();
            return 0;
        }
        else
            return 1;
    }

    //navigation back button
    @Override
    public void onBackPressed() {
        //delete premade route
        CarbonModel.getInstance().deleteJourney(new_journey_index);

        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }
}