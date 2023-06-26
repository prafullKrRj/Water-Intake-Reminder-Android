package com.example.waterintakereminder.onBoardings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
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
        DBHandler dbHandler = new DBHandler(this);
        dbHandler.insertUserDetails();
        SharedPreferences preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("onboarding_completed", true);
        editor.apply();
        List<String> list = dbHandler.getUserValues();

        dbHandler.insertDate("2023-06-07");
        calculateAmount amount = new calculateAmount(list.get(1), list.get(2), list.get(3), list.get(4), list.get(5), list.get(6));
        textView.setText(String.valueOf(amount.calculate()));
        new Handler().postDelayed((Runnable) () -> {
            Intent toHome = new Intent(dailyGoalSplashOnBoard.this, MainActivity.class);
            startActivity(toHome);
        }, 3000);
        LottieAnimationView anim1 = findViewById(R.id.anim1);
        anim1.setSpeed(.7f);
    }
    @Override
    public void onBackPressed() {

    }
    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        return false;
    }
}