package sfu.cmpt276.carbontracker;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.StackedValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import sfu.cmpt276.carbontracker.model.CarbonModel;

/*
displays multi-day graphs
 */

public class MultiDayGraphActivity extends AppCompatActivity {
    public static final int FOUR_WEEKS = 28;
    private TextView textView;
    private SeekBar seekBarX;
    private CombinedChart combinedChart;
    private PieChart pieChart;
    private float totalElectricEmissions;
    private float totalNaturalGasEmissions;
    private float totalBusEmissions;
    private float totalSkyTrainEmissions;
    private List<Float> listOfVehicleEmissions;
    private List<String> listOfVehicleNames;
    private float totalJourneyEmissions;
    private float totalUtilitiesEmissions;
    private Switch routeModeSwitch;
    private int numberOfDaysToDisplay;
    private XAxis xAxis;
    private boolean treemode;
    private float kgCo2ToTrees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_day_graph);
        textView = (TextView) findViewById(R.id.textViewForSeek);
        seekBarX = (SeekBar) findViewById(R.id.seekBar);
        combinedChart = (CombinedChart) findViewById(R.id.barChart);
        pieChart = (PieChart) findViewById(R.id.pieChart2);
        treemode = CarbonModel.getInstance().getHumanRelatableUnitEnabled();
        kgCo2ToTrees = (float) CarbonModel.getInstance().getKgCo2ToTrees();
        setUpSwitch();
        totalBusEmissions = 0;
        totalNaturalGasEmissions = 0;
        totalSkyTrainEmissions = 0;
        totalElectricEmissions = 0;




        numberOfDaysToDisplay = 28;
        textView.setText("" + numberOfDaysToDisplay + getString(R.string.days));

        xAxis = combinedChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);

        setupSeekBar();
        setupCharts();


    }

    private void setUpSwitch() {
        routeModeSwitch = (Switch) findViewById(R.id.switchRouteMode2);
        routeModeSwitch.setChecked(false);
        routeModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setupPieGraph(isChecked);
            }
        });
    }

    private void setupPieGraph(boolean isChecked) {
        Map<String, Float> emissions = new TreeMap<>();
        List<PieEntry> pieEntries = new ArrayList<>();
        totalUtilitiesEmissions = totalElectricEmissions + totalNaturalGasEmissions;
        totalJourneyEmissions = totalBusEmissions + totalSkyTrainEmissions;


        pieEntries.add(new PieEntry(totalNaturalGasEmissions, getString(R.string.naturalgas)));
        pieEntries.add(new PieEntry(totalElectricEmissions, getString(R.string.electricity)));

        if (isChecked) {
            for (int i = 0; i < CarbonModel.getInstance().getSizeOfJourneysList(); i++) {
                String name = CarbonModel.getInstance().getRouteName(CarbonModel.getInstance().getJourney(i).getRouteIndex());
                float newValue;
                if (emissions.containsKey(name)) {
                    newValue = emissions.get(CarbonModel.getInstance().getRouteName(CarbonModel.getInstance().getJourney(i).getRouteIndex()));
                    newValue = newValue + CarbonModel.getInstance().getJourneyTotalCO2Emissions(i);
                    addToMap(emissions, name, newValue);
                } else {
                    newValue = CarbonModel.getInstance().getJourneyTotalCO2Emissions(i);
                    addToMap(emissions, name, newValue);
                }
            }
        } else {
            for (int i = 0; i < CarbonModel.getInstance().getSizeOfJourneysList(); i++) {

                String name = CarbonModel.getInstance().getVehicleName(CarbonModel.getInstance().getJourney(i).getVehicleIndex());
                float newValue;
                if (emissions.containsKey(name)) {
                    newValue = emissions.get(CarbonModel.getInstance().getVehicleName(CarbonModel.getInstance().getJourney(i).getVehicleIndex()));
                    newValue = newValue + CarbonModel.getInstance().getJourneyTotalCO2Emissions(i);
                    addToMap(emissions, name, newValue);
                } else {
                    newValue = CarbonModel.getInstance().getJourneyTotalCO2Emissions(i);
                    addToMap(emissions, name, newValue);
                }

            }
        }
        for (String string : emissions.keySet()) {
            pieEntries.add(new PieEntry(emissions.get(string)
                    , string));
        }


        PieDataSet dataSet = new PieDataSet(pieEntries, getString(R.string.totalcarbonemissions));
        int[] allColors = new int[ColorTemplate.COLORFUL_COLORS.length + ColorTemplate.JOYFUL_COLORS.length];
        System.arraycopy(ColorTemplate.COLORFUL_COLORS, 0, allColors, 0, ColorTemplate.COLORFUL_COLORS.length);
        System.arraycopy(ColorTemplate.JOYFUL_COLORS, 0, allColors, ColorTemplate.COLORFUL_COLORS.length, ColorTemplate.JOYFUL_COLORS.length);
        dataSet.setColors(allColors);
        dataSet.setValueTextSize(20f);
        dataSet.setValueFormatter(new PercentFormatter());
        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.animateY(1000);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);


        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);

        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);

        pieChart.setHoleRadius(10f);
        pieChart.setTransparentCircleRadius(15f);

        pieChart.setDrawCenterText(true);

        pieChart.setRotationAngle(0);

        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);

        pieChart.invalidate();
        setUpTextViews();
    }

    private void addToMap(Map<String, Float> emissions, String name, float newValue) {
        if (name.equals("")) {
            emissions.put("Unknown", newValue);
        } else {
            emissions.put(name, newValue);
        }
    }

    private void setupSeekBar() {
        seekBarX.setProgress(numberOfDaysToDisplay);
        seekBarX.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                numberOfDaysToDisplay = progress;
                textView.setText("" + numberOfDaysToDisplay + " " + getString(R.string.days));
                if (progress == 0) {
                    numberOfDaysToDisplay = 1;
                    textView.setText("" + numberOfDaysToDisplay + " " + getString(R.string.days));
                }
                setupCharts();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setupCharts() {

        combinedChart.getDescription().setEnabled(false);

        Calendar c = Calendar.getInstance();
        int currentYear = c.get(Calendar.YEAR); // current graphYear
        int currentMonth = c.get(Calendar.MONTH) + 1; // current graphMonth
        int currentDay = c.get(Calendar.DAY_OF_MONTH); // current graphDay
        ArrayList<BarEntry> yVals = new ArrayList<>();

        float start = 0;
        float electricityEmissions = 0;
        float naturalGasEmissions = 0;
        float journeyEmissions = 0;

        //reset data;
        totalElectricEmissions = 0;
        totalNaturalGasEmissions = 0;
        totalSkyTrainEmissions = 0;
        totalBusEmissions = 0;
        listOfVehicleEmissions = new ArrayList<>();
        listOfVehicleNames = new ArrayList<>();


        for (int i = 0; i < numberOfDaysToDisplay; i++) {
            int[] yearMonthDayOfPreviousDate = CarbonModel.getInstance().
                    getYearMonthDayOfPreviousDate(i, currentYear, currentMonth, currentDay);

            journeyEmissions = 0;

            electricityEmissions =
                    CarbonModel.getInstance().getElectricityC02Emissions
                            (yearMonthDayOfPreviousDate[0], yearMonthDayOfPreviousDate[1], yearMonthDayOfPreviousDate[2]);
            totalElectricEmissions += electricityEmissions;

            naturalGasEmissions =
                    CarbonModel.getInstance().getGasC02Emissions
                            (yearMonthDayOfPreviousDate[0], yearMonthDayOfPreviousDate[1], yearMonthDayOfPreviousDate[2]);
            totalNaturalGasEmissions += naturalGasEmissions;

            for (int ii = 0; ii < CarbonModel.getInstance().getSizeOfJourneysList(); ii++) {
                if (CarbonModel.getInstance().getJourney(ii).getDay().getJulian() ==
                        CarbonModel.getInstance().getJulian
                                (yearMonthDayOfPreviousDate[0],
                                        yearMonthDayOfPreviousDate[1],
                                        yearMonthDayOfPreviousDate[2])) {
                    journeyEmissions += CarbonModel.getInstance().getJourney(ii).getTotalCO2Emission();

                    addToVehicleEmissionsList(CarbonModel.getInstance().getJourney(ii).getVehicleIndex(), CarbonModel.getInstance().getJourney(ii).getTotalCO2Emission());
                }
            }
            if (treemode) {
                yVals.add(new BarEntry(
                        start + i,
                        new float[]{electricityEmissions * kgCo2ToTrees, naturalGasEmissions * kgCo2ToTrees, journeyEmissions * kgCo2ToTrees}
                ));
            } else {
                yVals.add(new BarEntry(
                        start + i,
                        new float[]{electricityEmissions, naturalGasEmissions, journeyEmissions}
                ));
            }

        }

        BarDataSet set1;

        set1 = new BarDataSet(yVals, getString(R.string.carbonEmissions));
        set1.setColors(getColors());
        set1.setStackLabels(new String[]{getString(R.string.electricity), getString(R.string.naturalgas), getString(R.string.naturalgas)});

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();


        dataSets.add(set1);

        BarData barData = new BarData(dataSets);

        barData.setValueTextSize(10);
        barData.setValueFormatter(new StackedValueFormatter(false, "", 2));
        barData.setValueTextColor(Color.WHITE);


        CombinedData data = new CombinedData();
        data.setData(barData);

        data.setData(generateAverageData(start));
        combinedChart.setMaxVisibleValueCount(20);
        combinedChart.setPinchZoom(false);
        combinedChart.setData(data);
        combinedChart.setDrawBarShadow(false);
        combinedChart.setDrawGridBackground(false);
        combinedChart.invalidate();

        setupPieGraph(false);
    }

    private LineDataSet generateTargetData(float start) {
        ArrayList<Entry> entries = new ArrayList<>();

        for (int index = 0; index < numberOfDaysToDisplay; index++) {
            if (treemode) {
                entries.add(new Entry(start + index,
                        CarbonModel.getInstance().getNationalAverageAndParisAccordPerday()[1] * kgCo2ToTrees));
            } else {
                entries.add(new Entry(start + index,
                        CarbonModel.getInstance().getNationalAverageAndParisAccordPerday()[1]));
            }

        }

        LineDataSet set = new LineDataSet(entries, getString(R.string.target_emissions));
        set.setColor(Color.BLUE);
        set.setLineWidth(1f);
        set.setCircleColor(Color.BLUE);
        set.setCircleRadius(2f);
        set.setFillColor(Color.BLUE);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setValueTextColor(Color.BLUE);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);

        return set;
    }

    private LineData generateAverageData(float start) {
        LineData lineData = new LineData();

        ArrayList<Entry> entries = new ArrayList<>();


        for (int index = 0; index < numberOfDaysToDisplay; index++) {
            if (treemode) {
                entries.add(new Entry(start + index,
                        CarbonModel.getInstance().getNationalAverageAndParisAccordPerday()[0] * kgCo2ToTrees));
            } else {
                entries.add(new Entry(start + index,
                        CarbonModel.getInstance().getNationalAverageAndParisAccordPerday()[0]));
            }
        }

        LineDataSet set = new LineDataSet(entries, getString(R.string.average_emissions));
        set.setColor(Color.BLACK);
        set.setLineWidth(1f);
        set.setCircleColor(Color.BLACK);
        set.setCircleRadius(2f);
        set.setFillColor(Color.BLACK);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setValueTextColor(Color.BLACK);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);

        lineData.addDataSet(set);
        lineData.addDataSet(generateTargetData(start));

        return lineData;
    }

    private void addToVehicleEmissionsList(int vehicleIndex, float kgCO2Emission) {
        String vehicleName = CarbonModel.getInstance().getVehicleFromHidden(vehicleIndex).getName();

        switch (vehicleName) {
            case "Bus":
                totalBusEmissions += kgCO2Emission;
                break;
            case "SkyTrain":
                totalSkyTrainEmissions += kgCO2Emission;
                break;
            case "Walk":
                break;
            default:

                if (listOfVehicleNames.contains(vehicleName)) {
                    int index = listOfVehicleNames.indexOf(vehicleName);
                    float oldValue = listOfVehicleEmissions.get(index);
                    listOfVehicleEmissions.add(index, oldValue + kgCO2Emission);
                } else {
                    listOfVehicleNames.add(vehicleName);
                    int index = listOfVehicleNames.indexOf(vehicleName);
                    listOfVehicleEmissions.add(index, kgCO2Emission);
                }
        }


    }

    private void setUpTextViews() {
        TextView journeyCO2 = (TextView) findViewById(R.id.textViewJourneyCO2Number2);
        TextView utilitiesCO2 = (TextView) findViewById(R.id.textViewUtilitiesCO2Number2);
        TextView totalCO2 = (TextView) findViewById(R.id.textViewTotalCO2Number2);


        float journeyAndUtilitiesCO2 = totalJourneyEmissions + totalUtilitiesEmissions;

        if (treemode) {
            journeyCO2.setText(totalJourneyEmissions * kgCo2ToTrees + " " + getString(R.string.tree));
            utilitiesCO2.setText(totalUtilitiesEmissions * kgCo2ToTrees + " " + getString(R.string.tree));
            totalCO2.setText(journeyAndUtilitiesCO2 * kgCo2ToTrees + " " + getString(R.string.tree));
        } else {
            journeyCO2.setText(totalJourneyEmissions + " kgCO2");
            utilitiesCO2.setText(totalUtilitiesEmissions + " kgCO2");
            totalCO2.setText(journeyAndUtilitiesCO2 + " kgCO2");

        }

    }

    private int[] getColors() {

        int stacksize = 3;

        // have as many colors as stack-values per entry
        int[] colors = new int[stacksize];

        for (int i = 0; i < colors.length; i++) {
            colors[i] = ColorTemplate.MATERIAL_COLORS[i];
        }

        return colors;
    }

    //action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reset:
                Intent back = new Intent();
                setResult(Activity.RESULT_CANCELED, back);
                finish();
                return (true);
            case R.id.about:
                startActivity(new Intent(MultiDayGraphActivity.this, AboutActivity.class));
                return (true);
            case R.id.exit:
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return (true);
        }
        return (super.onOptionsItemSelected(item));
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
