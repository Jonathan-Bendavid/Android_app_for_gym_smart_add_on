package com.example.GymFiTech;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class MonthlyPerformanceChartHelper {

    private final BarChart chart;
    private final Context context;

    public MonthlyPerformanceChartHelper(BarChart chart, Context context) {
        this.chart = chart;
        this.context = context;
    }

    public void displayMonthlyPerformanceChart() {
        List<String> dates = Global.monthly_dates;
        List<String> scores = Global.monthly_performance_scores;

        if (dates == null || scores == null || dates.size() != scores.size()) {
            Log.e("MonthlyChart", "Mismatch or null data.");
            return;
        }

        List<BarEntry> entries = new ArrayList<>();
        List<Integer> barColors = new ArrayList<>();

        for (int i = 0; i < scores.size(); i++) {
            try {
                float score = Float.parseFloat(scores.get(i));
                entries.add(new BarEntry(i, score));

                // Color logic
                if (score < 55) {
                    barColors.add(ContextCompat.getColor(context, R.color.bar_green));
                }
                else if (score < 70) {
                    barColors.add(ContextCompat.getColor(context, R.color.bar_green));
                }
                else {
                    barColors.add(ContextCompat.getColor(context, R.color.bar_green));
                }

            } catch (NumberFormatException e) {
                Log.e("MonthlyChart", "Invalid score at index " + i, e);
            }
        }

        BarDataSet dataSet = new BarDataSet(entries, "Monthly Performance");
        dataSet.setColors(barColors);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(12f);
        dataSet.setDrawValues(true);
        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getBarLabel(BarEntry barEntry) {
                return ((int) barEntry.getY()) + "%";
            }
        });

        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.7f);
        chart.setData(barData);

        customizeChart();
        setupXAxis(dates);
        setupYAxis();

        chart.invalidate();
    }

    private void customizeChart() {
        chart.getDescription().setEnabled(false);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(false);
        chart.setFitBars(true);
        chart.animateY(1000);
    }

    private void setupXAxis(List<String> xLabels) {
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(xLabels.size());
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int index = (int) value;
                return (index >= 0 && index < xLabels.size()) ? xLabels.get(index) : "";
            }
        });
    }

    private void setupYAxis() {
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularity(1f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setTextColor(Color.BLACK);
        chart.getAxisRight().setEnabled(false);
    }
}
