package com.example.waterintakereminder.Database;

public class userDetails {
    private static String name;
    private static String age;
    private static String gender;
    private static String weight;
    private static String weightUnit;
    private static String dailyActivity;
    private static int startHour, startMin, endHour, endMin, interval;
    private static String weather;


    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        userDetails.startHour = startHour;
    }

    public int getStartMin() {
        return startMin;
    }

    public void setStartMin(int startMin) {
        userDetails.startMin = startMin;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        userDetails.endHour = endHour;
    }

    public int getEndMin() {
        return endMin;
    }

    public void setEndMin(int endMin) {
        userDetails.endMin = endMin;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        userDetails.interval = interval;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        userDetails.age = age;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        userDetails.weather = weather;
    }

    public String getDailyActivity() {
        return dailyActivity;
    }

    public void setDailyActivity(String dailyActivity) {
        userDetails.dailyActivity = dailyActivity;
    }

    public String getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(String weightUnit) {
        userDetails.weightUnit = weightUnit;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        userDetails.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        userDetails.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        userDetails.name = name;
    }
}
