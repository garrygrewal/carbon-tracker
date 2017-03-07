package sfu.cmpt276.carbontracker;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
        TextView vehicle = (TextView) findViewById(R.id.textVehicleName);
        TextView route = (TextView) findViewById(R.id.textRouteName);
        Journey thisJourney = CarbonModel.getInstance().getJourney(new_journey_index);
        vehicle.setText(thisJourney.getVehicle().getName());
        route.setText(thisJourney.getRoute().getName());
    }

    private void addName() {
        EditText in_name = (EditText) findViewById(R.id.journeyName);
        String name = in_name.getText().toString();
        CarbonModel.getInstance().addJourneyName(new_journey_index, name);

    }

    private void showEmissions() {
        new_journey_index = CarbonModel.getInstance().newJourneyIndex();
        Journey journey = CarbonModel.getInstance().getJourney(new_journey_index);
        CarbonModel.getInstance().calculateCarbonEmissions(journey);
        TextView city_emis = (TextView) findViewById(R.id.city_emissions);
        city_emis.setText(Double.toString(journey.getCo2PerCity()));
        TextView hwy_emis = (TextView) findViewById(R.id.hwy_emissions);
        city_emis.setText(Double.toString(journey.getCo2PerHighway()));
    }


    private void premakeJourney() {
        Intent intent = getIntent();
        int route_index = intent.getIntExtra("route_index", 0);
        int vehicle_index = intent.getIntExtra("vehicle_index", 0);
        CarbonModel.getInstance().newJourney(CarbonModel.getInstance().getVehicle(vehicle_index), CarbonModel.getInstance().getRoute(route_index));
    }

    private void setupButtons() {
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
                Intent intent = new Intent();
                setResult(Activity.RESULT_CANCELED, intent);
                finish();
            }
        });
    }

    private int checkInput() {
        EditText in_name = (EditText) findViewById(R.id.journeyName);
        String name = in_name.getText().toString();
        //check if input is valid
        if (name.equals(null) || name.replaceAll("\\s+","").equals("")) {
            Toast toast = Toast.makeText(getApplicationContext(), "Journey name cannot be empty.", Toast.LENGTH_SHORT);
            toast.show();
            return 0;
        }
        else
            return 1;
    }

    //navigation back button
    @Override
    public void onBackPressed () {
        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }
}