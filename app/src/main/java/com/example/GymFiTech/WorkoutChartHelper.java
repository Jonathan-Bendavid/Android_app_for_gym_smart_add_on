package com.example.GymFiTech;

import android.content.Context;
import android.graphics.Color;
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
import android.graphics.Typeface;

public class WorkoutChartHelper {

    private static final String TAG = "WorkoutChartHelper";
    private final BarChart barChart;
    private final Context context;

    public WorkoutChartHelper(BarChart barChart, Context context) {
        this.barChart = barChart;
        this.context = context;
    }

    public void displayWorkoutBarChart(List<Integer> workoutsPerMonth) {
        if (workoutsPerMonth == null || workoutsPerMonth.isEmpty()) {
            Log.w(TAG, "No workout data provided.");
            return;
        }

        // Validate data size
        if (workoutsPerMonth.size() > 12) {
            Log.e(TAG, "Data size exceeds 12 months. Only the first 12 months will be displayed.");
            workoutsPerMonth = workoutsPerMonth.subList(0, 12);
        }

        // Create data for the bar chart
        List<BarEntry> barEntries = createBarEntries(workoutsPerMonth);

        // Set the dataset for the bar chart
        BarDataSet barDataSet = createBarDataSet(barEntries);

        // Create the bar chart data and set it to the chart
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);

        // Customize chart
        customizeChart();

        // Set X-axis properties
        setupXAxis(workoutsPerMonth.size());

        // Set Y-axis properties
        setupYAxis();

        // Refresh the chart
        barChart.invalidate();
    }

    private List<BarEntry> createBarEntries(List<Integer> workoutsPerMonth) {
        List<BarEntry> barEntries = new ArrayList<>();
        for (int i = 0; i < workoutsPerMonth.size(); i++) {
            barEntries.add(new BarEntry(i, workoutsPerMonth.get(i)));
        }

        return barEntries;
    }

    private BarDataSet createBarDataSet(List<BarEntry> barEntries) {
        BarDataSet barDataSet = new BarDataSet(barEntries, "Workouts per Month");
        barDataSet.setColor(context.getResources().getColor(android.R.color.holo_blue_dark));
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(10f);
        barDataSet.setValueTypeface(Typeface.DEFAULT_BOLD);
        barDataSet.setDrawValues(true);

        // Set custom formatter to display integer values
        barDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getBarLabel(BarEntry barEntry) {
                return String.valueOf((int) barEntry.getY());
            }
        });

        return barDataSet;
    }


    private void customizeChart() {
        barChart.getDescription().setEnabled(false);
        barChart.setDragEnabled(true);
        barChart.animateY(2000);
        barChart.setFitBars(true); // Make the x-values fit exactly
    }

    private void setupXAxis(int dataSize) {
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setCenterAxisLabels(false); // Center labels between bars
        xAxis.setLabelCount(dataSize);
        xAxis.setValueFormatter(new MonthValueFormatter());
        xAxis.setTypeface(Typeface.DEFAULT_BOLD);

    }
    private void setupYAxis() {
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularity(1);
        leftAxis.setAxisMinimum(0f); // Start Y-axis at 0
        leftAxis.setTypeface(Typeface.DEFAULT_BOLD);
        barChart.getAxisRight().setEnabled(false); // Disable right Y-axis
    }
    private static class MonthValueFormatter extends ValueFormatter {
        private final String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

        @Override
        public String getFormattedValue(float value) {
            int index = (int) value;
            if (index >= 0 && index < months.length) {
                return months[index];
            }
            return "";
        }
    }
}
