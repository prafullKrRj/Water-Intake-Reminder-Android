package com.example.waterintakereminder.onBoardings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.waterintakereminder.MainActivity;
import com.example.waterintakereminder.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.shawnlin.numberpicker.NumberPicker;

public class onBoardingWeather extends AppCompatActivity {
    ExtendedFloatingActionButton nextButtonWeather;
    FloatingActionButton prevButtonWeather;
    CardView lowTemperature, moderateTemperature, highTemperature;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding_weather);
        prevButtonWeather = findViewById(R.id.prevButtonWeather);
        nextButtonWeather = findViewById(R.id.nextButtonWeather);
        prevButtonWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(onBoardingWeather.this, onBoardingActivityLevel.class);
                startActivity(intent);
            }
        });
        nextButtonWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(onBoardingWeather.this, lastOnboard.class);
                startActivity(intent);
            }
        });

        highTemperature = findViewById(R.id.highTemperature);
        lowTemperature = findViewById(R.id.lowTemperature);
        moderateTemperature = findViewById(R.id.moderateTemperature);
        lowTemperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setColor(lowTemperature, "#244AC7EF", "#80D9D9D9", highTemperature, moderateTemperature);
            }
        });
       highTemperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setColor(highTemperature, "#244AC7EF", "#80D9D9D9", lowTemperature, moderateTemperature);
            }
        });
       moderateTemperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setColor(moderateTemperature, "#244AC7EF", "#80D9D9D9", highTemperature, lowTemperature);
            }
        });
    }
    private void setColor(CardView cardView, String color1, String color2, CardView cardView1, CardView cardView2){
        cardView.setCardBackgroundColor(Color.parseColor(color1));
        cardView1.setCardBackgroundColor(Color.parseColor(color2));
        cardView2.setCardBackgroundColor(Color.parseColor(color2));
    }
    @Override
    public void onBackPressed() {

    }
}