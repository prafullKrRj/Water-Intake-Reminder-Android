package com.example.waterintakereminder;

import static com.example.waterintakereminder.Database.params_db.params.ACTIVITY_LEVEL;
import static com.example.waterintakereminder.Database.params_db.params.GENDER;
import static com.example.waterintakereminder.Database.params_db.params.WEATHER;
import static com.example.waterintakereminder.Database.params_db.params.WEIGHT;
import static com.example.waterintakereminder.Database.params_db.params.WEIGHT_UNIT;

import android.content.Context;

import com.example.waterintakereminder.Database.DBHandler;

public class changes {

    private Context context;
    private DBHandler handler;

    public changes(Context context) {
        this.context = context;
        handler = new DBHandler(context);
    }

    public void changeWeight(int weight){
        handler.change(WEIGHT, String.valueOf(weight));
    }

    public void changeWeight(String weight){
        handler.change(WEIGHT, weight);
    }

    public void changeGender(String gender){
        handler.change(GENDER, gender);
    }

    public void changeActivityLevel(String activity){
        handler.change(ACTIVITY_LEVEL, activity);
    }

    public void changeWeather(String weather){
        handler.change(WEATHER, weather);
    }

    public void changeWeightMeasuringUnit(String unit){
        handler.change(WEIGHT_UNIT, unit);
    }
}
