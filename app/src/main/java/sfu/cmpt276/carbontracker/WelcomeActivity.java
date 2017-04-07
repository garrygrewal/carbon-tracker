package sfu.cmpt276.carbontracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import sfu.cmpt276.carbontracker.model.CarbonModel;
import sfu.cmpt276.carbontracker.model.TipsArray;

/*
splash screen, also reads in cvs
 */

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        final ImageView iv = (ImageView) findViewById(R.id.imageView);
        final Animation an = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate);
        final Animation an2 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.abc_fade_out);


        iv.startAnimation(an);
        an.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                iv.startAnimation(an2);
                finish();
                CarbonModel.getInstance().fillList(getFileRows());
                readFile();
                startActivity(new Intent(WelcomeActivity.this, MainMenuActivity.class));

                CarbonModel.getInstance().LoadData();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus) {
            View mDecorView = getWindow().getDecorView();
            mDecorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    private int getFileRows() {
        InputStream is = getResources().openRawResource(R.raw.vehicles);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        int rows = -1;
        String line = "";

        try {
            while ((line = reader.readLine()) != null) {
                rows++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rows;
    }
}
