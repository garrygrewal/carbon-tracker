package sfu.cmpt276.carbontracker;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static sfu.cmpt276.carbontracker.R.id.route_city;
import static sfu.cmpt276.carbontracker.R.id.route_hwy;
import static sfu.cmpt276.carbontracker.R.id.route_name;

public class AddRouteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route);

        prefillRoute();
        setupButtons();
    }

    //prefill EditText for when user is editing an existing route
    private void prefillRoute() {
        Intent intent = getIntent();
        if ((intent.getStringExtra("name") != null)) {
            String name = intent.getStringExtra("name");
            EditText in_name = (EditText) findViewById(route_name);
            in_name.setText(name);
            String city = intent.getStringExtra("city");
            EditText in_city = (EditText) findViewById(route_city);
            in_city.setText(city);
            String hwy = intent.getStringExtra("hwy");
            EditText in_hwy = (EditText) findViewById(route_hwy);
            in_hwy.setText(hwy);
        }
    }

    private void setupButtons() {
        //add route button
        Button btn_ok = (Button) findViewById(R.id.buttonAddRoute);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddNewRoute();
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

    private void AddNewRoute() {
        EditText in_name = (EditText) findViewById(route_name);
        String name = in_name.getText().toString();
        EditText in_city = (EditText) findViewById(route_city);
        String city = in_city.getText().toString();
        EditText in_hwy = (EditText) findViewById(route_hwy);
        String hwy = in_hwy.getText().toString();

        //check if input is valid
        if (checkInput(name, city, hwy) == 0) {
            setResult(Activity.RESULT_CANCELED);
        } else {
            Intent intent = new Intent();
            intent.putExtra("name", name);
            intent.putExtra("city", city);
            intent.putExtra("hwy", hwy);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }

    //check if input is valid
    private int checkInput(String name, String city, String hwy) {
        //check if input is valid
        if (name.equals(null) || name.replaceAll("\\s+","").equals("")) {
            Toast toast = Toast.makeText(getApplicationContext(), "Route name cannot be empty.", Toast.LENGTH_SHORT);
            toast.show();
            return 0;
        }
        else if (city.equals(null) || city.equals("") || hwy.equals(null) || hwy.equals("")) {
            Toast toast = Toast.makeText(getApplicationContext(), "Distance cannot be empty.", Toast.LENGTH_SHORT);
            toast.show();
            return 0;
        }
        else if (Math.signum(Float.parseFloat(city)) == 0 || Math.signum(Float.parseFloat(hwy)) == 0) {
            Toast toast = Toast.makeText(getApplicationContext(), "Distance cannot be 0.", Toast.LENGTH_SHORT);
            toast.show();
            return 0;
        }
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
