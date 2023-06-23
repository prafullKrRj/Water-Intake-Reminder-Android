package com.example.waterintakereminder.onBoardings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.waterintakereminder.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class onBoardingActivityLevel extends AppCompatActivity {
    ExtendedFloatingActionButton nextButtonActivity;
    FloatingActionButton prevButtonActivity;
    CardView sedentaryActivity, lightActivity, moderateActivity, highActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding_level);
        nextButtonActivity = findViewById(R.id.nextButtonActivity);
        prevButtonActivity = findViewById(R.id.prevButtonActivity);

        nextButtonActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(onBoardingActivityLevel.this, onBoardingWeather.class);
                startActivity(intent);
            }
        });
        prevButtonActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(onBoardingActivityLevel.this, onBoardingWeight.class);
                startActivity(intent);
            }
        });
        sedentaryActivity = findViewById(R.id.sedentaryActivity);
        highActivity = findViewById(R.id.highActivity);
        lightActivity = findViewById(R.id.lightActivity);
        moderateActivity = findViewById(R.id.moderateActivity);

        sedentaryActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setColor(sedentaryActivity, "#244AC7EF", "#80D9D9D9", highActivity, lightActivity, moderateActivity);
            }
        });moderateActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setColor(moderateActivity, "#244AC7EF", "#80D9D9D9", highActivity, lightActivity, sedentaryActivity);
            }
        });
        highActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setColor(highActivity, "#244AC7EF", "#80D9D9D9", sedentaryActivity, lightActivity, moderateActivity);
            }
        });lightActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setColor(lightActivity, "#244AC7EF", "#80D9D9D9", highActivity, sedentaryActivity, moderateActivity);
            }
        });
    }
    private void setColor(CardView cardView, String color1, String color2, CardView cardView1, CardView cardView2, CardView cardView3){
        cardView.setCardBackgroundColor(Color.parseColor(color1));
        cardView1.setCardBackgroundColor(Color.parseColor(color2));
        cardView2.setCardBackgroundColor(Color.parseColor(color2));
        cardView3.setCardBackgroundColor(Color.parseColor(color2));
    }
    @Override
    public void onBackPressed() {

    }
}