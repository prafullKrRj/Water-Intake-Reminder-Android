package com.example.waterintakereminder;

import android.app.AlarmManager;

public class TimeFormatter {
    public TimeFormatter() {

    }
    public String formattedTime(int hour, int min){
        String h = (hour<10) ? "0"+hour : hour+"";
        String m = (min<10) ? "0"+min : min+"";
        return h+":"+m;

    }

}
