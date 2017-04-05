package sfu.cmpt276.carbontracker;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import sfu.cmpt276.carbontracker.model.CarbonModel;

public class PieGraphActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_graph);

        setupPieChart();
    }

    private void setupPieChart() {
        //populating a list of PieEntries;
        List<PieEntry> pieEntries = new ArrayList<>();

        for (int i = 0; i < CarbonModel.getInstance().getSizeOfJourneysList(); i++) {
            pieEntries.add(new PieEntry(CarbonModel.getInstance().getJourneyTotalCO2Emissions(i)
                    , CarbonModel.getInstance().getJourneyName(i)));
        }
        PieDataSet dataSet = new PieDataSet(pieEntries, getString(R.string.totalCO2Emissions));
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        dataSet.setValueTextSize(20f);
        dataSet.setValueFormatter(new PercentFormatter());
        PieData data = new PieData(dataSet);

        // Get the chart

        PieChart chart = (PieChart) findViewById(R.id.piechart);
        chart.setData(data);
        chart.animateY(1000);

        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);


        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.WHITE);

        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);

        chart.setHoleRadius(10f);
        chart.setTransparentCircleRadius(15f);

        chart.setDrawCenterText(true);

        chart.setRotationAngle(0);

        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);

        chart.invalidate();
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

    @Override
    public void onBackPressed() {
        finish();
    }
}
