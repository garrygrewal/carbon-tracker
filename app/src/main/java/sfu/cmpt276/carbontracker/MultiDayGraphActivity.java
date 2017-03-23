package sfu.cmpt276.carbontracker;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.StackedValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import sfu.cmpt276.carbontracker.model.CarbonModel;

public class MultiDayGraphActivity extends AppCompatActivity {
    public static final int FOUR_WEEKS = 28;
    private TextView textView;
    private SeekBar seekBarX;
    private BarChart barChart;
    private PieChart pieChart;
    private float totalElectricEmissions;
    private float totalNaturalGasEmissions;
    private float totalBusEmissions;
    private float totalSkyTrainEmissions;
    private List<Float> listOfVehicleEmissions;
    private List<String> listOfVehicleNames;
    private float totalJourneyEmissions;
    private float totalUtilitiesEmissions;



    private int numberOfDaysToDisplay;
    private XAxis xAxis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_day_graph);
        textView = (TextView) findViewById(R.id.textViewForSeek);
        seekBarX = (SeekBar) findViewById(R.id.seekBar);
        barChart = (BarChart) findViewById(R.id.barChart);
        pieChart = (PieChart) findViewById(R.id.pieChart2);


        totalBusEmissions = 0;
        totalNaturalGasEmissions = 0;
        totalSkyTrainEmissions = 0;
        totalElectricEmissions = 0;


        numberOfDaysToDisplay = 28;
        textView.setText("" + numberOfDaysToDisplay + " days");
        IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(barChart);

        xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(xAxisFormatter);

        setupSeekBar();
        setupCharts();


    }

    private void setupPieGraph() {
        List<PieEntry> pieEntries = new ArrayList<>();
        totalUtilitiesEmissions = totalElectricEmissions+ totalNaturalGasEmissions;
        totalJourneyEmissions = totalBusEmissions + totalSkyTrainEmissions;

        pieEntries.add(new PieEntry(totalBusEmissions, "Bus"));
        pieEntries.add(new PieEntry(totalSkyTrainEmissions, "Skytrain"));
        pieEntries.add(new PieEntry(totalNaturalGasEmissions, "Natural Gas"));
        pieEntries.add(new PieEntry(totalElectricEmissions, "Electric"));

        for(int i = 0; i < listOfVehicleNames.size(); i++){
            pieEntries.add(new PieEntry(listOfVehicleEmissions.get(i),listOfVehicleNames.get(i)));
            totalJourneyEmissions += listOfVehicleEmissions.get(i);
        }



        PieDataSet dataSet = new PieDataSet(pieEntries, "Total Carbon Emissions");
        int[] allColors = new int[ColorTemplate.COLORFUL_COLORS.length +ColorTemplate.JOYFUL_COLORS.length];
        System.arraycopy(ColorTemplate.COLORFUL_COLORS, 0, allColors, 0,ColorTemplate.COLORFUL_COLORS.length);
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

    private void setupSeekBar() {
        seekBarX.setProgress(numberOfDaysToDisplay);
        seekBarX.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                numberOfDaysToDisplay = progress;
                textView.setText("" + numberOfDaysToDisplay + " days");
                if (progress == 0) {
                    numberOfDaysToDisplay = 1;
                    textView.setText("" + numberOfDaysToDisplay + " days");
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

        barChart.getDescription().setEnabled(false);

        Calendar c = Calendar.getInstance();
        int currentYear = c.get(Calendar.YEAR); // current graphYear
        int currentMonth = c.get(Calendar.MONTH) + 1; // current graphMonth
        int currentDay = c.get(Calendar.DAY_OF_MONTH); // current graphDay
        ArrayList<BarEntry> yVals = new ArrayList<>();
        float start = (c.get(Calendar.DAY_OF_YEAR)) + 365;
        float electricityEmissions = 0;
        float naturalGasEmissions = 0;
        float journeyEmissions = 0;
        int count = 0;
        int weekCount = 0;

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


            if (numberOfDaysToDisplay < 40) {
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
                        journeyEmissions += CarbonModel.getInstance().getJourney(i).getTotalCO2Emission();

                        addToVehicleEmissionsList(CarbonModel.getInstance().getJourney(ii).getVehicleIndex(),CarbonModel.getInstance().getJourney(ii).getTotalCO2Emission());
                    }
                }

                xAxis.setValueFormatter(new DayAxisValueFormatter(barChart));
                yVals.add(new BarEntry(
                        start - i,
                        new float[]{electricityEmissions, naturalGasEmissions, journeyEmissions}
                ));


            } else {

                electricityEmissions +=
                        CarbonModel.getInstance().getElectricityC02Emissions
                                (yearMonthDayOfPreviousDate[0], yearMonthDayOfPreviousDate[1], yearMonthDayOfPreviousDate[2]);
                totalElectricEmissions += electricityEmissions;

                naturalGasEmissions +=
                        CarbonModel.getInstance().getGasC02Emissions
                                (yearMonthDayOfPreviousDate[0], yearMonthDayOfPreviousDate[1], yearMonthDayOfPreviousDate[2]);
                totalNaturalGasEmissions += naturalGasEmissions;

                for (int iii = 0; iii < CarbonModel.getInstance().getSizeOfJourneysList(); iii++) {
                    if (CarbonModel.getInstance().getJourney(iii).getDay().getJulian() ==
                            CarbonModel.getInstance().getJulian
                                    (yearMonthDayOfPreviousDate[0],
                                            yearMonthDayOfPreviousDate[1],
                                            yearMonthDayOfPreviousDate[2])) {
                        journeyEmissions += CarbonModel.getInstance().getJourney(i).getTotalCO2Emission();

                        addToVehicleEmissionsList(CarbonModel.getInstance().getJourney(iii).getVehicleIndex(),CarbonModel.getInstance().getJourney(iii).getTotalCO2Emission());

                    }
                }
                count++;
                if (count == 7) {
                    weekCount++;

                    yVals.add(new BarEntry(
                            start / 7 - (weekCount),
                            new float[]{electricityEmissions, naturalGasEmissions, journeyEmissions}
                    ));
                    count = 0;
                    electricityEmissions = 0;
                    naturalGasEmissions = 0;
                    journeyEmissions = 0;

                }
                xAxis.setValueFormatter(new WeekAxisValueFormatter(barChart));
            }

        }


        BarDataSet set1;

        set1 = new BarDataSet(yVals, "Carbon Emissions");
        set1.setColors(getColors());
        set1.setStackLabels(new String[]{"Electricity", "NaturalGas", "Journeys"});

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(dataSets);

        data.setValueTextSize(10);
        data.setValueFormatter(new StackedValueFormatter(false, "", 2));
        data.setValueTextColor(Color.WHITE);

        barChart.setMaxVisibleValueCount(20);
        barChart.setPinchZoom(false);
        barChart.setData(data);
        barChart.setDrawBarShadow(false);
        barChart.setDrawGridBackground(false);
        barChart.setFitBars(true);
        barChart.invalidate();

        setupPieGraph();
    }

    private void addToVehicleEmissionsList(int vehicleIndex, float kgCO2Emission) {
        String vehicleName = CarbonModel.getInstance().getVehicleFromHidden(vehicleIndex).getName();

        switch (vehicleName){
            case "Bus":
                totalBusEmissions += kgCO2Emission;
                break;
            case "SkyTrain":
                totalSkyTrainEmissions += kgCO2Emission;
                break;
            case "Walk":
                break;
            default:

                if(listOfVehicleNames.contains(vehicleName)){
                    int index = listOfVehicleNames.indexOf(vehicleName);
                    float oldValue = listOfVehicleEmissions.get(index);
                    listOfVehicleEmissions.add(index, oldValue+kgCO2Emission);
                } else {
                    listOfVehicleNames.add(vehicleName);
                    int index = listOfVehicleNames.indexOf(vehicleName);
                    listOfVehicleEmissions.add(index, kgCO2Emission);
                }
        }



    }
    private void setUpTextViews(){
        TextView journeyCO2 = (TextView) findViewById(R.id.textViewJourneyCO2Number2);
        TextView utilitiesCO2 = (TextView) findViewById(R.id.textViewUtilitiesCO2Number2);
        TextView totalCO2 = (TextView) findViewById(R.id.textViewTotalCO2Number2);

        journeyCO2.setText( totalJourneyEmissions + " kgCO2");
        utilitiesCO2.setText(totalUtilitiesEmissions + " kgCO2");
        float journeyAndUtilitiesCO2 = totalJourneyEmissions + totalUtilitiesEmissions;
        totalCO2.setText(journeyAndUtilitiesCO2 + " kgCO2");
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

    @Override
    public void onBackPressed() {
        finish();
    }

}
