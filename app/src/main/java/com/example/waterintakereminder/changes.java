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
        handler.changeUserDetails(WEIGHT, String.valueOf(weight));
    }

    public void changeWeight(String weight){
        handler.changeUserDetails(WEIGHT, weight);
    }

    public void changeGender(String gender){
        handler.changeUserDetails(GENDER, gender);
    }

    public void changeActivityLevel(String activity){
        handler.changeUserDetails(ACTIVITY_LEVEL, activity);
    }

    public void changeWeather(String weather){
        handler.changeUserDetails(WEATHER, weather);
    }

    public void changeWeightMeasuringUnit(String unit){
        handler.changeUserDetails(WEIGHT_UNIT, unit);
    }
}
