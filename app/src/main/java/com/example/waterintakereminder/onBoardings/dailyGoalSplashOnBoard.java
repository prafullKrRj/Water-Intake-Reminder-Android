package com.example.waterintakereminder.onBoardings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;
import com.example.waterintakereminder.MainActivity;
import com.example.waterintakereminder.R;

public class dailyGoalSplashOnBoard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_goal_splash_on_board);
        new Handler().postDelayed((Runnable) () -> {
            Intent toHome = new Intent(dailyGoalSplashOnBoard.this, MainActivity.class);
            startActivity(toHome);
        }, 3500);
        LottieAnimationView anim1 = findViewById(R.id.anim1);
        anim1.setSpeed(.7f);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(dailyGoalSplashOnBoard.this, onBoardingWeather.class);
        startActivity(intent);
    }
}