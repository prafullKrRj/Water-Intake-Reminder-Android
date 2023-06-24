package com.example.waterintakereminder.Fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.waterintakereminder.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class analyticsFragment extends Fragment {
    ArrayList<BarEntry> weeklyEntries;
    ArrayList<BarEntry> dailyEntries;
    ArrayList<BarEntry> monthlyEntries;
    public analyticsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_analytics, container, false);
        weeklyChart(view);
        dailyChart(view);
        return view;
    }


    private void dailyChart(View view){
        BarChart daily = view.findViewById(R.id.daily);
        int [] yValues = new int[24];
        String [] xValues = new String[24];
        for (int i = 0; i < 24; i++) {
            xValues[i] = (i<=12) ? i+" AM" : i%12+" PM";
        }
        chartFormatter formatter = new chartFormatter(xValues);
        XAxis xAxis = daily.getXAxis();
        xAxis.setValueFormatter(formatter);
        stopZoom(daily);

    }
    private void getDailyData(){

    }
    private void stopZoom(BarChart chart){
        chart.setPinchZoom(false);
        chart.setDragEnabled(false);
        chart.setDragXEnabled(false);
        chart.setDragYEnabled(false);
        chart.setScaleXEnabled(false);
        chart.setScaleYEnabled(false);
    }
    private void getWeeklyData()
    {
        weeklyEntries = new ArrayList<BarEntry>();
        weeklyEntries.add(new BarEntry(1, 2000));
        weeklyEntries.add(new BarEntry(2, 1500));
        weeklyEntries.add(new BarEntry(3, 1500));
        weeklyEntries.add(new BarEntry(4, 1500));
        weeklyEntries.add(new BarEntry(5, 1500));
        weeklyEntries.add(new BarEntry(6, 1500));
        weeklyEntries.add(new BarEntry(7, 1500));
    }
    private void weeklyChart(View view){
        String[] xValues = new String[] {"", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        chartFormatter formatter = new chartFormatter(xValues);
        BarChart weekly = view.findViewById(R.id.weekly);
        stopZoom(weekly);
        XAxis xAxis = weekly.getXAxis();
        xAxis.setValueFormatter(formatter);
        getWeeklyData();
        BarDataSet dataSet = new BarDataSet(weeklyEntries, "Weekly");
        dataSet.setColors(Color.parseColor("#3AA6B9"));
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(12f);

        BarData barData = new BarData(dataSet);
        weekly.setFitBars(true);
        weekly.setData(barData);
        weekly.getDescription().setText("");
        weekly.getDescription().setTextSize(0f);
        weekly.animateY(1700);
    }
}