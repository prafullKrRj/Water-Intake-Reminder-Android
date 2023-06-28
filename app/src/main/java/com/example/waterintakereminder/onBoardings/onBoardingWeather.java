package com.example.waterintakereminder.onBoardings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.waterintakereminder.Database.DBHandler;
import com.example.waterintakereminder.R;
import com.example.waterintakereminder.Database.userDetails;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class onBoardingWeather extends AppCompatActivity {
    ExtendedFloatingActionButton nextButtonWeather;
    FloatingActionButton prevButtonWeather;
    CardView coolTemp, modTemp, hotTemp;
    ImageView check1, check2, check3;
    private String weather="hot";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding_weather);
        userDetails details = new userDetails();
        prevButtonWeather = findViewById(R.id.prevButtonWeather);
        nextButtonWeather = findViewById(R.id.nextButtonWeather);
        check1 = findViewById(R.id.hotToggle);
        check2 = findViewById(R.id.moderateToggleWeather);
        check3 = findViewById(R.id.coldToggle);
        DBHandler dbHandler = new DBHandler(this);
        prevButtonWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(onBoardingWeather.this, sleepActivity.class);
                startActivity(intent);
            }
        });
        nextButtonWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                details.setWeather(weather);
                dbHandler.insertDate("2023-06-07");
                Intent intent = new Intent(onBoardingWeather.this, dailyGoalSplashOnBoard.class);
                startActivity(intent);
            }
        });

        hotTemp = findViewById(R.id.hotTemp);
        coolTemp = findViewById(R.id.coolTemp);
        modTemp = findViewById(R.id.modTemp);
        setColor(hotTemp, "#244AC7EF", "#80D9D9D9", coolTemp, modTemp);
        show(check1,check2, check3);
        coolTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weather = "low";
                details.setWeather("low");
                setColor(coolTemp, "#244AC7EF", "#80D9D9D9", hotTemp, modTemp);
                show(check3, check2, check1);
            }
        });
       hotTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weather = "high";
                details.setWeather("high");
                setColor(hotTemp, "#244AC7EF", "#80D9D9D9", coolTemp, modTemp);
                show(check1, check2, check3);
            }
        });
       modTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weather = "moderate";
                show(check2, check3, check1);
                setColor(modTemp, "#244AC7EF", "#80D9D9D9", hotTemp, coolTemp);
            }
        });
    }
    private void setColor(CardView cardView, String color1, String color2, CardView cardView1, CardView cardView2){
        cardView.setCardBackgroundColor(Color.parseColor(color1));
        cardView1.setCardBackgroundColor(Color.parseColor(color2));
        cardView2.setCardBackgroundColor(Color.parseColor(color2));
    }
    private void show(ImageView check, ImageView i1, ImageView i2){
        check.setVisibility(View.VISIBLE);
        check.setColorFilter(Color.parseColor("#0079FF"));
        i1.setVisibility(View.INVISIBLE);
        i2.setVisibility(View.INVISIBLE);
    }
    @Override
    public void onBackPressed() {

    }
}