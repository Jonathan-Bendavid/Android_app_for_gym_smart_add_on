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
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
import java.util.List;

public class ExercisePerformanceChartHelper {

    private static final String TAG = "ExercisePerformanceChart";
    private final BarChart barChart;
    private final Context context;

    public ExercisePerformanceChartHelper(BarChart barChart, Context context) {
        this.barChart = barChart;
        this.context = context;
    }

    public void displaySetPerformanceScores(List<String> performanceScores) {
        if (performanceScores == null || performanceScores.isEmpty()) {
            Log.w(TAG, "No performance scores provided.");
            return;
        }

        List<BarEntry> entries = new ArrayList<>();
        List<String> xLabels = new ArrayList<>();

        for (int i = 0; i < performanceScores.size(); i++) {
            try {
                float score = Float.parseFloat(performanceScores.get(i));
                entries.add(new BarEntry(i, score));
                xLabels.add("Set " + (i + 1));
            }
            catch (Exception e) {
                Log.e(TAG, "Error parsing performance score at index " + i, e);
            }
        }

        BarDataSet dataSet = new BarDataSet(entries, "Performance Score");
        dataSet.setColor(ContextCompat.getColor(context, R.color.bar_slate));
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(14f);
        dataSet.setValueTypeface(Typeface.DEFAULT_BOLD);
        dataSet.setDrawValues(true);
        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getBarLabel(BarEntry barEntry) {
                if (barEntry.getY() < 55) {

                }
                return String.valueOf((barEntry.getY()) + "%");
            }
        });

        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.7f);
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
