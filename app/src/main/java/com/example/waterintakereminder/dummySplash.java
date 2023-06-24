package com.example.waterintakereminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.waterintakereminder.onBoardings.mainOnBoardingSplash;

public class dummySplash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy_splash);
        SharedPreferences preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        boolean isOnboardingCompleted = preferences.getBoolean("onboarding_completed", false);

        if (!isOnboardingCompleted) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("onboarding_completed", true);
            editor.apply();

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