package com.example.waterintakereminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.waterintakereminder.Database.DBHandler;

import java.time.LocalDate;

public class splashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        DBHandler handler = new DBHandler(this);
        LocalDate prevDate = LocalDate.parse(handler.getPrevDate());
        LocalDate currDate = LocalDate.now();
         if (currDate.isAfter(prevDate) || currDate.isBefore(prevDate)) {
            handler.insertDate(String.valueOf(currDate));
            int a = handler.dailyFinalAmount(String.valueOf(prevDate));
            handler.deleteDailyHistory();
        }
        LottieAnimationView lottieAnimationView = findViewById(R.id.splashIntro);
        lottieAnimationView.setColorFilter(Color.parseColor("#244AC7EF"));
        new Handler().postDelayed((Runnable) () -> {
            Intent toHome = new Intent(splashActivity.this, MainActivity.class);
            startActivity(toHome);
        }, 1700);
    }
}