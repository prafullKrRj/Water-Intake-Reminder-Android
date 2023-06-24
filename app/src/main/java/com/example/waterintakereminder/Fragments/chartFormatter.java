package com.example.waterintakereminder.Fragments;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

public class chartFormatter extends ValueFormatter {
    private String [] values;
    public chartFormatter(String[] values) {
        this.values = values;
    }
    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        int index = (int) value;
        if (index >= 0 && index < values.length) {
            return values[index];
        } else {
            return "";
        }
    }
}
