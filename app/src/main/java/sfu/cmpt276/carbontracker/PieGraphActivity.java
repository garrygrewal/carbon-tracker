package sfu.cmpt276.carbontracker;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
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
    private void listJourneys() {
        ArrayAdapter<String> adapter = new ArrayAdapter(this, R.layout.list_journey, CarbonModel.getInstance().getJourneyInfo());
        ListView journey_list = (ListView) findViewById(R.id.journeyList);

        //List Adapter
        journey_list.setAdapter(adapter);

        //Context Menu for long press
        registerForContextMenu(journey_list);
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


    @Override
    public void onBackPressed () {
        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }
}
