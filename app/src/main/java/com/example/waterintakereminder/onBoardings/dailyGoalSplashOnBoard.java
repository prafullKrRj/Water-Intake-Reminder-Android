package com.example.waterintakereminder.onBoardings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.waterintakereminder.Database.DBHandler;
import com.example.waterintakereminder.MainActivity;
import com.example.waterintakereminder.R;
import com.example.waterintakereminder.calculateAmount;

import java.util.List;

public class dailyGoalSplashOnBoard extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_goal_splash_on_board);
        TextView textView = findViewById(R.id.dailyGoal);
        DBHandler handler = new DBHandler(this);
        List<String> list = handler.getUserValues();
        calculateAmount amount = new calculateAmount(list.get(1), list.get(2), list.get(3), list.get(4), list.get(5), list.get(6));
        textView.setText(String.valueOf(amount.calculate()));
        new Handler().postDelayed((Runnable) () -> {
            Intent toHome = new Intent(dailyGoalSplashOnBoard.this, MainActivity.class);
            startActivity(toHome);
        }, 15000);
        LottieAnimationView anim1 = findViewById(R.id.anim1);
        anim1.setSpeed(.7f);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(dailyGoalSplashOnBoard.this, onBoardingWeather.class);
        startActivity(intent);
    }
}