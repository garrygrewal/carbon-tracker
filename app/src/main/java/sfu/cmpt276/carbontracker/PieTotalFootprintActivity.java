package sfu.cmpt276.carbontracker;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import sfu.cmpt276.carbontracker.model.CarbonModel;

public class PieTotalFootprintActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_total_footprint);
        setupButtons();
        setupPieChart();
    }

    private void setupPieChart() {
        //populating a list of PieEntries;
        List<PieEntry> pieEntries = new ArrayList<>();

        for(int i = 0; i < CarbonModel.getInstance().getSizeOfJourneysList(); i++){
            pieEntries.add(new PieEntry(CarbonModel.getInstance().getJourneyTotalCO2Emissions(i)
                    , CarbonModel.getInstance().getJourneyName(i)));
        }
        PieDataSet dataSet = new PieDataSet(pieEntries, "Total CO2 Emissions");
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);
        PieData data = new PieData(dataSet);

        // Get the chart

        PieChart chart = (PieChart) findViewById(R.id.piechart);
        chart.setData(data);
        chart.animateY(1000);
        chart.invalidate();

    }

    private void setupButtons() {
        //back to main menu button
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

    //navigation back button
    @Override
    public void onBackPressed () {
        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }
}
