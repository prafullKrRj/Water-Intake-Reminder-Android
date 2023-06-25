package com.example.waterintakereminder;

public class calculateAmount {
    private String gender;
    private String weight;
    private String weightUnit;
    private String activityLevel;
    private String weather;

    public calculateAmount(String gender, String weight, String weightUnit, String activityLevel, String weather) {
        this.gender = gender;
        this.weight = weight;
        this.weightUnit = weightUnit;
        this.activityLevel = activityLevel;
        this.weather = weather;
    }
    public void calculate(){
        int amount = 0;
        if (gender.equals("Male")){
            amount+=2;
        }
    }
}
