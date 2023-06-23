package com.example.waterintakereminder.onBoardings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.waterintakereminder.MainActivity;
import com.example.waterintakereminder.R;

public class lastOnboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_onboard);
        new Handler().postDelayed((Runnable) () -> {
            Intent toHome = new Intent(lastOnboard.this, dailyGoalSplashOnBoard.class);
            startActivity(toHome);
        }, 4200);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(lastOnboard.this, onBoardingWeather.class);
        startActivity(intent);
    }
}