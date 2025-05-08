package com.example.GymFiTech;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class SetPowerChartHelper {
    private static final String TAG = "SetPowerChartHelper";
    private final BarChart barChart;
    private final Context context;

    public SetPowerChartHelper(BarChart barChart, Context context) {
        this.barChart = barChart;
        this.context = context;
    }

    public void displayRepPowerPerSet(List<List<String>> reps_data) {

        if (reps_data == null || reps_data.isEmpty()) {
            Log.w(TAG, "No rep power data provided.");
            return;
        }

        List<BarEntry> entries = new ArrayList<>();
        List<String> xLabels = new ArrayList<>();

        for (int rep = 0; rep < reps_data.size(); rep++) {
            try {
                float power = Float.parseFloat(reps_data.get(rep).get(3).substring(0,4));
                entries.add(new BarEntry(rep, power));
                xLabels.add("R" + (rep + 1));
            } catch (Exception e) {
                Log.e(TAG, "Failed to parse power value for rep " + rep, e);
            }
        }

        BarDataSet dataSet = new BarDataSet(entries, "Rep Power (mW)");
        dataSet.setColor(context.getResources().getColor(android.R.color.holo_blue_dark));
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTypeface(Typeface.DEFAULT_BOLD);
        dataSet.setValueTextSize(10f);
        dataSet.setDrawValues(true);
        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getBarLabel(BarEntry barEntry) {
                return String.valueOf((barEntry.getY()));
            }
        });

        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.9f);
        barChart.setData(barData);

        customizeChart();
        setupXAxis(xLabels);
        setupYAxis();

        barChart.invalidate(); // Refresh chart
    }
    private void customizeChart() {
        barChart.getDescription().setEnabled(false);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(false);
        barChart.setFitBars(true);
        barChart.animateY(1000);
    }

    private void setupXAxis(List<String> xLabels) {
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(xLabels.size());
        xAxis.setDrawGridLines(false);
        xAxis.setTypeface(Typeface.DEFAULT_BOLD);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int index = (int) value;
                return index >= 0 && index < xLabels.size() ? xLabels.get(index) : "";
            }
        });
    }

    private void setupYAxis() {
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularity(1f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setTypeface(Typeface.DEFAULT_BOLD);
        barChart.getAxisRight().setEnabled(false);
    }
}
