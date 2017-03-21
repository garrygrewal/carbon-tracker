package sfu.cmpt276.carbontracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;

public class MultiDayGraphActivity extends AppCompatActivity {
    private TextView textView;
    private SeekBar seekBarX;
    private BarChart barChart;
    private int numberOfDaysToDisplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView = (TextView) findViewById(R.id.textViewSeekBar);
        seekBarX = (SeekBar) findViewById(R.id.seekBar);
        barChart = (BarChart) findViewById(R.id.barChart);
        numberOfDaysToDisplay = 28;
        textView.setText("fuck");

        //setupSeekBar();
       // setupBarChart();

        setContentView(R.layout.activity_multi_day_graph);
    }
    private void setupSeekBar() {
        seekBarX.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                numberOfDaysToDisplay = progress;
                textView.setText(String.format("%d", numberOfDaysToDisplay));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    private void setupBarChart() {
    }


}
