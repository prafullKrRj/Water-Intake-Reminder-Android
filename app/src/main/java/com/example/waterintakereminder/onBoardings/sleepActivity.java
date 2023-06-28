package com.example.waterintakereminder.onBoardings;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.waterintakereminder.Database.userDetails;
import com.example.waterintakereminder.MainActivity;
import com.example.waterintakereminder.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

public class sleepActivity extends AppCompatActivity {
    TextView sleepText, wakeText;
    MaterialCardView sleepC, wakeC;
    FloatingActionButton prevButton;
    ExtendedFloatingActionButton nextButton;
    MaterialTimePicker wakeUpPicker, sleepPicker, timeIntervalPicker;
    userDetails details;
    String sleepTime="22:00", wakeTime="08:00";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);

        details = new userDetails();
        ini();
        sleepText = findViewById(R.id.sleepTextOnBoard);
        wakeText = findViewById(R.id.wakeUpTextOnBoard);
        wakeC = findViewById(R.id.cardWake);
        sleepC = findViewById(R.id.cardSleep);
        prevButton = findViewById(R.id.prevButtonSleep);
        nextButton = findViewById(R.id.nextButtonSleep);

        sleepC.setOnClickListener(this::getSleepTime);
        wakeC.setOnClickListener(this::getWakeUpTime);

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Activity.class);
                startActivity(intent);
            }
        });
        nextButton.setOnClickListener(this::nextAct);
    }
    private void ini(){
        details.setEndHour(22);
        details.setEndMin(0);
        details.setStartHour(8);
        details.setStartMin(0);
        details.setInterval(60);
    }
    private void getWakeUpTime(View view){
        wakeUpPicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(22)
                .setMinute(0)
                .setTitleText("WakeUp")
                .build();
        wakeUpPicker.show(getSupportFragmentManager(), "time");
        wakeUpPicker.setCancelable(false);
        wakeUpPicker.addOnPositiveButtonClickListener(this::okClickedWake);
    }

    private void okClickedWake(View view) {
        String hour = (wakeUpPicker.getHour()<10) ? "0"+wakeUpPicker.getHour() : wakeUpPicker.getHour()+"";
        String min = (wakeUpPicker.getMinute()<10) ? "0"+wakeUpPicker.getMinute() : wakeUpPicker.getMinute()+"";
        wakeTime = (String.valueOf(hour+":"+min));
        details.setStartHour(wakeUpPicker.getHour());
        details.setStartMin(wakeUpPicker.getMinute());
        wakeText.setText(wakeTime);
    }

    private void getSleepTime(View view) {
        sleepPicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(22)
                .setMinute(0)
                .setTitleText("Sleep Time")
                .build();
        sleepPicker.show(getSupportFragmentManager(), "time");
        sleepPicker.setCancelable(false);
        sleepPicker.addOnPositiveButtonClickListener(this::okClickedSleep);
    }
    private void okClickedSleep(View view) {
        details.setEndHour(sleepPicker.getHour());
        details.setEndMin(sleepPicker.getMinute());
        String hour = (sleepPicker.getHour()<10) ? "0"+sleepPicker.getHour() : sleepPicker.getHour()+"";
        String min = (sleepPicker.getMinute()<10) ? "0"+sleepPicker.getMinute() : sleepPicker.getMinute()+"";
        sleepText.setText(sleepTime);
    }

    @Override
    public void onBackPressed() {

    }

    private void nextAct(View view) {
        Toast.makeText(this, String.valueOf(details.getStartHour()+" "+details.getEndHour()), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, onBoardingWeather.class);
        startActivity(intent);
    }
}
