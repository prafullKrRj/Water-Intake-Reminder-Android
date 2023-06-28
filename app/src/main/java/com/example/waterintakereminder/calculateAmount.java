package com.example.waterintakereminder;

import android.content.Context;

import com.example.waterintakereminder.Database.DBHandler;

public class calculateAmount {
    private String gender;
    private int age;
    private int weight;
    private String weightUnit;
    private String activityLevel;
    private String weather;
    public calculateAmount(String age, String gender, String weight, String weightUnit, String activityLevel, String weather)
    {
        this.age = Integer.parseInt(age);
        this.gender = gender;
        this.weight = parse(weight);
        this.weightUnit = weightUnit;
        this.activityLevel = activityLevel;
        this.weather = weather;
    }
    // activity = sedentary , moderate, high, light
    // weather = low, high, moderate
    public int calculate(){
        int activityFactor = getActivityFactor();
        int genderFactor = getGenderFactor();
        int ageFactor = getAgeFactor();
        int weatherFactor = getWeatherFactor();
        float weightFactor = (weightUnit.equals("kg")) ? weight : weight*0.45359237f;
        return (int) (weight*activityFactor-ageFactor+genderFactor+weightFactor+weatherFactor);
    }
    private int getWeatherFactor(){
        if (weather.equals("high")){
            return 500;
        }else if (weather.equals("moderate")){
            return 250;
        }else return 0;
    }
    private int getAgeFactor(){
        return (age>=55) ? -200 : 0;
    }
    private int getGenderFactor(){
        if (gender.equals("male")){
            return 600;
        }else return 1;
    }
    private int getActivityFactor(){
        int ans = 0;
        switch (activityLevel) {
            case "sedentary":
                ans = 30;
                break;
            case "moderate":
                ans = 40;
                break;
            case "high":
                ans = 47;
                break;
            default:
                ans = 35;
                break;
        }
        return ans;
    }
    private int parse(String string){
        return Integer.parseInt(string);
    }
}
