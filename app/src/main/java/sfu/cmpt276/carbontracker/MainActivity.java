package sfu.cmpt276.carbontracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/*
 * Main Menu Screen
 */

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1014;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createJourney();
    }

    private void createJourney() {
        Button btnCreateJourney = (Button) findViewById(R.id.buttonCreateJourney);
        btnCreateJourney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SelectTransportation.makeIntent(MainActivity.this);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }
}
