package com.example.waterintakereminder.Fragments.settingsManager;

import static com.example.waterintakereminder.Database.params_db.params.END_HOUR;
import static com.example.waterintakereminder.Database.params_db.params.END_MIN;
import static com.example.waterintakereminder.Database.params_db.params.INTERVAL;
import static com.example.waterintakereminder.Database.params_db.params.START_HOUR;
import static com.example.waterintakereminder.Database.params_db.params.START_MIN;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.waterintakereminder.Database.DBHandler;
import com.example.waterintakereminder.Dialogs.changeIntervalDialog;
import com.example.waterintakereminder.R;
import com.example.waterintakereminder.TimeFormatter;
import com.example.waterintakereminder.changes;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.shawnlin.numberpicker.NumberPicker;

import java.util.List;
import java.util.Objects;

public class reminderScheduleReminder extends Fragment {
    MaterialTimePicker sleepPicker;
    MaterialTimePicker wakeUpPicker;
    TextView sleepTime, wakeUpTime, timeIntervalPickerText;
    DBHandler handler;
    ImageButton backBtn;
    Button saveBtn;
    private TimeFormatter timeFormatter;
    private int interval, nStartHour, nStartMin, nEndHour, nEndMin;
    public reminderScheduleReminder() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reminder_schedule_reminder, container, false);
        sleepTime = view.findViewById(R.id.sleepTime);
        wakeUpTime = view.findViewById(R.id.wakeUpTime);
        timeIntervalPickerText = view.findViewById(R.id.timeIntervalPicker);
        backBtn = view.findViewById(R.id.backButton);
        handler = new DBHandler(getContext());
        timeFormatter = new TimeFormatter();
        List<Integer> sleepTimings = handler.getSleepDetails();
        timeIntervalPickerText.setText(String.valueOf(sleepTimings.get(4)));
        sleepTime.setText(timeFormatter.formattedTime(sleepTimings.get(2), sleepTimings.get(3)));
        wakeUpTime.setText(timeFormatter.formattedTime(sleepTimings.get(0), sleepTimings.get(1)));
        sleepTime.setOnClickListener(this::getSleepTime);
        wakeUpTime.setOnClickListener(this::getWakeUpTime);
        timeIntervalPickerText.setOnClickListener(this::changeInterval);
        backBtn.setOnClickListener(this::goBack);   // List = {1, 1, 2, 2, , 3}
        return view;
    }

    private void goBack(View view) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.container, new SettingsFragment());
        ft.commit();
    }

    private void changeInterval(View view) {
        changeIntervalDialog dialog = new changeIntervalDialog(getContext());
        dialog.setCancelable(false);
        dialog.show();
        NumberPicker hour = dialog.findViewById(R.id.hourPicker);
        hour.setMaxValue(23);
        hour.setMinValue(0);
        NumberPicker min = dialog.findViewById(R.id.minPicker);
        min.setMaxValue(59);
        min.setMinValue(0);
        TextView cancel = dialog.findViewById(R.id.cancel_button_interval);
        TextView ok = dialog.findViewById(R.id.okBtnInterval);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String interval = String.valueOf(hour.getValue() * 60 + min.getValue());
                timeIntervalPickerText.setText(interval);
                handler.updateSleepDetails(INTERVAL, hour.getValue()*60+min.getValue());
                dialog.dismiss();
            }
        });
    }
    private void getSleepTime(View view) {
        sleepPicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(22)
                .setMinute(0)
                .setTitleText("Sleep Time")
                .build();
        sleepPicker.show(getParentFragmentManager(), "time");
        sleepPicker.setCancelable(false);
        sleepPicker.addOnPositiveButtonClickListener(this::okClickedSleep);
    }

    private void okClickedSleep(View view) {
        String hour = (sleepPicker.getHour()<10) ? "0"+sleepPicker.getHour() : sleepPicker.getHour()+"";
        String min = (sleepPicker.getMinute()<10) ? "0"+sleepPicker.getMinute() : sleepPicker.getMinute()+"";
        sleepTime.setText(hour+":"+min);
        handler.updateSleepDetails(END_HOUR, sleepPicker.getHour());
        handler.updateSleepDetails(END_MIN, sleepPicker.getMinute());
    }

    private void getWakeUpTime(View view){
        wakeUpPicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(22)
                .setMinute(0)
                .setTitleText("WakeUp")
                .build();
        wakeUpPicker.show(getParentFragmentManager(), "time");
        wakeUpPicker.setCancelable(false);
        wakeUpPicker.addOnPositiveButtonClickListener(this::okClickedWake);
    }

    private void okClickedWake(View view) {
        String hour = (wakeUpPicker.getHour()<10) ? "0"+wakeUpPicker.getHour() : wakeUpPicker.getHour()+"";
        String min = (wakeUpPicker.getMinute()<10) ? "0"+wakeUpPicker.getMinute() : wakeUpPicker.getMinute()+"";
        wakeUpTime.setText(hour+":"+min);
        handler.updateSleepDetails(START_HOUR, wakeUpPicker.getHour());
        handler.updateSleepDetails(START_MIN, wakeUpPicker.getMinute());
    }
    private void setAlarmMain() {

    }
}
