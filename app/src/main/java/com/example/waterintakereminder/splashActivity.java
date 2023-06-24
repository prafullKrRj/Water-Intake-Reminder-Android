package com.example.waterintakereminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;

public class splashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        LottieAnimationView lottieAnimationView = findViewById(R.id.splashIntro);
        lottieAnimationView.setColorFilter(Color.parseColor("#244AC7EF"));
        new Handler().postDelayed((Runnable) () -> {
            Intent toHome = new Intent(splashActivity.this, MainActivity.class);
            startActivity(toHome);
        }, 1700);
    }
}