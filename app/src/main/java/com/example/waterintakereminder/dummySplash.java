package com.example.waterintakereminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.waterintakereminder.Database.DBHandler;
import com.example.waterintakereminder.onBoardings.mainOnBoardingSplash;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class dummySplash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy_splash);
        SharedPreferences preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        boolean isOnboardingCompleted = preferences.getBoolean("onboarding_completed", false);
        if (!isOnboardingCompleted) {
            Intent onboardingIntent = new Intent(dummySplash.this, mainOnBoardingSplash.class);
            startActivity(onboardingIntent);
            finish();
        }else{
            Intent intent = new Intent(dummySplash.this, splashActivity.class);
            startActivity(intent);
            finish();
        }

    }
}