package sfu.cmpt276.carbontracker;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static sfu.cmpt276.carbontracker.R.id.route_city;
import static sfu.cmpt276.carbontracker.R.id.route_hwy;
import static sfu.cmpt276.carbontracker.R.id.route_name;

/*
Add route activity allow user to add different routes to journey
 */

public class AddRouteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route);

        prefillRoute();
        setupButtons();
        UiChangeListener();
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
        else {
            EditText in_city = (EditText) findViewById(route_city);
            in_city.setText("0");
            EditText in_hwy = (EditText) findViewById(route_hwy);
            in_hwy.setText("0");
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
        //extract data from UI
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
        if (name.equals(null) || name.replaceAll("\\s+", "").equals("")) {
            Toast toast = Toast.makeText(getApplicationContext(), "Route name cannot be empty.", Toast.LENGTH_SHORT);
            toast.show();
            return 0;
        } else if (city.equals(null) || city.equals("") || hwy.equals(null) || hwy.equals("")) {
            Toast toast = Toast.makeText(getApplicationContext(), "Distance cannot be empty.", Toast.LENGTH_SHORT);
            toast.show();
            return 0;
        } else if (Math.signum(Float.parseFloat(city)) == 0 && Math.signum(Float.parseFloat(hwy)) == 0) {
            Toast toast = Toast.makeText(getApplicationContext(), "Distance cannot be 0.", Toast.LENGTH_SHORT);
            toast.show();
            return 0;
        }
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
            startActivity(new Intent(AddRouteActivity.this, AboutActivity.class));
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
