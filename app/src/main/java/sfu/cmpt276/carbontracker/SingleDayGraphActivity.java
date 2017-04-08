package sfu.cmpt276.carbontracker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import sfu.cmpt276.carbontracker.model.CarbonModel;

public class SingleDayGraphActivity extends AppCompatActivity {
    private String date;
    private int graphDay;
    private int graphMonth;
    private int graphYear;
    private Switch routeModeSwitch;
    private boolean treemode;
    private float kgCo2ToTrees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_day_graph);
        Calendar c = Calendar.getInstance();
        graphYear = c.get(Calendar.YEAR); // current graphYear
        graphMonth = c.get(Calendar.MONTH) + 1; // current graphMonth
        graphDay = c.get(Calendar.DAY_OF_MONTH); // current graphDay
        treemode = CarbonModel.getInstance().getHumanRelatableUnitEnabled();
        kgCo2ToTrees = (float) CarbonModel.getInstance().getKgCo2ToTrees();
        setupPieChart(false);
        setupButtons();
        setupSwitch();
        updateTextViews();
    }

    private void setupSwitch() {
        routeModeSwitch = (Switch) findViewById(R.id.switchRouteMode);
        routeModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setupPieChart(isChecked);
            }
        });

    }

    private void updateTextViews() {
        float totalJourneyCO2 = 0;
        float totalUtilitiesCO2 = 0;
        TextView journeyCO2 = (TextView) findViewById(R.id.textViewJourneyCO2Number);
        TextView utilitiesCO2 = (TextView) findViewById(R.id.textViewUtilitiesCO2Number);
        TextView totalCO2 = (TextView) findViewById(R.id.textViewTotalCO2Number);

        for (int i = 0; i < CarbonModel.getInstance().getSizeOfJourneysList(); i++) {
            if (CarbonModel.getInstance().getJourney(i).getDay().getJulian() == CarbonModel.getInstance().getJulian(graphYear, graphMonth, graphDay)) {
                totalJourneyCO2 += CarbonModel.getInstance().getJourneyTotalCO2Emissions(i);
            }
        }


        totalUtilitiesCO2 = CarbonModel.getInstance().getElectricityC02Emissions(graphYear, graphMonth, graphDay) +
                CarbonModel.getInstance().getGasC02Emissions(graphYear, graphMonth, graphDay);

        float journeyAndUtilitiesCO2 = totalUtilitiesCO2 + totalJourneyCO2;
        if(treemode) {
            journeyCO2.setText(totalJourneyCO2* kgCo2ToTrees + " " + getString(R.string.tree));
            utilitiesCO2.setText(totalUtilitiesCO2 * kgCo2ToTrees+ " " + getString(R.string.tree));
            totalCO2.setText(journeyAndUtilitiesCO2 * kgCo2ToTrees+ " " + getString(R.string.tree));
        } else {
            journeyCO2.setText(totalJourneyCO2 + " kgCO2");
            utilitiesCO2.setText(totalUtilitiesCO2 + " kgCO2");
            totalCO2.setText(journeyAndUtilitiesCO2 + " kgCO2");
        }
    }

    private void setupPieChart(boolean isChecked) {
        //populating a list of PieEntries;
        List<PieEntry> pieEntries = new ArrayList<>();
        Map<String, Float> emissions = new TreeMap<>();
        if (isChecked) {
            for (int i = 0; i < CarbonModel.getInstance().getSizeOfJourneysList(); i++) {
                if (CarbonModel.getInstance().getJourney(i).getDay().getJulian() == CarbonModel.getInstance().getJulian(graphYear, graphMonth, graphDay)) {
                    String name = CarbonModel.getInstance().getRouteName(CarbonModel.getInstance().getJourney(i).getRouteIndex());
                    float newValue;
                    if (emissions.containsKey(name)) {
                        newValue= emissions.get(CarbonModel.getInstance().getRouteName(CarbonModel.getInstance().getJourney(i).getRouteIndex()));
                        newValue = newValue + CarbonModel.getInstance().getJourneyTotalCO2Emissions(i);
                        emissions.put(name, newValue);
                    } else {
                        newValue = CarbonModel.getInstance().getJourneyTotalCO2Emissions(i);
                        emissions.put(name, newValue);
                    }
                }
            }
        } else {
            for (int i = 0; i < CarbonModel.getInstance().getSizeOfJourneysList(); i++) {
                if (CarbonModel.getInstance().getJourney(i).getDay().getJulian() == CarbonModel.getInstance().getJulian(graphYear, graphMonth, graphDay)) {
                    String name = CarbonModel.getInstance().getVehicleName(CarbonModel.getInstance().getJourney(i).getVehicleIndex());
                    float newValue;
                    if (emissions.containsKey(name)) {
                        newValue= emissions.get(CarbonModel.getInstance().getVehicleName(CarbonModel.getInstance().getJourney(i).getVehicleIndex()));
                        newValue = newValue + CarbonModel.getInstance().getJourneyTotalCO2Emissions(i);
                        emissions.put(name, newValue);
                    } else {
                        newValue = CarbonModel.getInstance().getJourneyTotalCO2Emissions(i);
                        emissions.put(name, newValue);
                    }
                }
            }
        }
        for (String string : emissions.keySet()) {
            pieEntries.add(new PieEntry(emissions.get(string)
                    , string));
        }

        Log.i("ATT", " " + graphDay + ", " + graphMonth + ", " + graphYear);

        pieEntries.add(new PieEntry(CarbonModel.getInstance().getGasC02Emissions(graphYear, graphMonth, graphDay),  getString(R.string.naturalgasemissions)));
        pieEntries.add(new PieEntry(CarbonModel.getInstance().getElectricityC02Emissions(graphYear, graphMonth, graphDay), getString(R.string.electricityemissions)));

        PieDataSet dataSet = new PieDataSet(pieEntries, getString(R.string.co2emissionsforday));
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
            startActivity(new Intent(SingleDayGraphActivity.this, AboutActivity.class));
            return(true);
        case R.id.exit:
            Intent exit = new Intent(Intent.ACTION_MAIN);
            exit.addCategory(Intent.CATEGORY_HOME);
            exit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(exit);
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

    private void setupButtons() {
        final TextView editPieGraphDate = (TextView) findViewById(R.id.selectDateForGraph);
        date = graphDay + "/" + graphMonth + "/" + graphYear;
        editPieGraphDate.setText(date);
        editPieGraphDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog datePickerDialog = new DatePickerDialog(SingleDayGraphActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // set graphDay of graphMonth , graphMonth and graphYear value in the edit text
                        editPieGraphDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        graphDay = dayOfMonth;
                        graphMonth = monthOfYear + 1;
                        graphYear = year;
                        setupPieChart(false);
                        updateTextViews();
                    }
                }, graphYear, graphMonth - 1, graphDay);
                datePickerDialog.show();
            }
        });

    }
}
