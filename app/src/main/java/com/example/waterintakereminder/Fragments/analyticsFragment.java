package com.example.waterintakereminder.Fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.waterintakereminder.Database.DBHandler;
import com.example.waterintakereminder.Fragments.HistoryManager.RecyclerView.HistoryModel;
import com.example.waterintakereminder.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class analyticsFragment extends Fragment {

    ArrayList<BarEntry> hourlyEntries;
    DBHandler dbHandler;
    public analyticsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_analytics, container, false);
        dbHandler = new DBHandler(getContext());
        hourlyChart(view);
        avgChart(view);
        return view;
    }
    private void avgChart(View view){
        HorizontalBarChart chart = view.findViewById(R.id.avgChart);
        ArrayList<BarEntry> weeklyAvg = new ArrayList<>();
        String [] xValues = {"weekly", "monthly"};
        weeklyAvg.add(new BarEntry(dbHandler.getWeeklyAvg(), 2));
        chartFormatter formatter = new chartFormatter(xValues);
        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(formatter);
        stopZoom(chart);

        BarDataSet dataSet = new BarDataSet(weeklyAvg, "weekly Average");
        dataSet.setColors(Color.parseColor("#3AA6B9"));
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(12f);
        BarData data = new BarData(dataSet);
        chart.setFitBars(true);
        chart.setData(data);
        chart.getDescription().setText("");
        chart.getDescription().setTextSize(0f);
        chart.animateY(1700);
    }
    private void hourlyChart(View view){
        BarChart hourly = view.findViewById(R.id.hourlyChart);
        String [] xValues = new String[24];
        for (int i = 0; i < 24; i++) {
            xValues[i] = (i<12) ? ((i<10) ? "0"+i+":00 AM" : i+":00AM") : (i!=12 && i%12<10) ? "0"+i+":00PM" : i+":00PM";
        }
        getHourlyData();
        chartFormatter formatter = new chartFormatter(xValues);
        XAxis xAxis = hourly.getXAxis();
        xAxis.setValueFormatter(formatter);
        stopZoom(hourly);
        BarDataSet dataSet = new BarDataSet(hourlyEntries, "hourly");
        dataSet.setColors(Color.parseColor("#3AA6B9"));
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(12f);
        BarData barData = new BarData(dataSet);
        hourly.setFitBars(true);
        hourly.setData(barData);
        hourly.getDescription().setText("");
        hourly.getDescription().setTextSize(0f);
        hourly.animateY(1700);
    }
    private void getHourlyData(){
        List<HistoryModel> history = dbHandler.getHistory();
        hourlyEntries = new ArrayList<>();
        int [] arr = new int[24];
        for (int i = 0; i < history.size(); i++) {
            int hour = Integer.parseInt(history.get(i).getTime().substring(0, 2));
            arr[hour] += Integer.parseInt(history.get(i).getAmount());
        }
        for (int i = 0; i < arr.length; i++) {
            hourlyEntries.add(new BarEntry(i, arr[i]));
        }
    }
    private void stopZoom(BarChart chart){
        chart.setPinchZoom(false);
        chart.setDragEnabled(false);
        chart.setDragXEnabled(false);
        chart.setDragYEnabled(false);
        chart.setScaleXEnabled(false);
        chart.setScaleYEnabled(false);
    }
}