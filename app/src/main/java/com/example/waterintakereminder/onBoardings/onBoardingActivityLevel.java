package com.example.waterintakereminder.onBoardings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.waterintakereminder.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class onBoardingActivityLevel extends AppCompatActivity {
    ExtendedFloatingActionButton nextButtonActivity;
    FloatingActionButton prevButtonActivity;
    CardView sedentaryActivity, lightActivity, moderateActivity, highActivity;
    ImageView check1, check2, check3, check4;
    private int clicked=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding_level);
        nextButtonActivity = findViewById(R.id.nextButtonActivity);
        prevButtonActivity = findViewById(R.id.prevButtonActivity);
        sedentaryActivity = findViewById(R.id.sedentaryActivity);
        highActivity = findViewById(R.id.highActivity);
        lightActivity = findViewById(R.id.lightActivity);
        moderateActivity = findViewById(R.id.moderateActivity);
        check1 = findViewById(R.id.sedentaryToggle);
        check2 = findViewById(R.id.lightToggle);
        check3 = findViewById(R.id.moderateToggle);
        check4 = findViewById(R.id.highToggle);
        setColor(sedentaryActivity, "#244AC7EF", "#80D9D9D9", highActivity, lightActivity, moderateActivity);
        check1.setColorFilter(Color.parseColor("#0079FF"));


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

        sedentaryActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked = 1;
                setColor(sedentaryActivity, "#244AC7EF", "#80D9D9D9", highActivity, lightActivity, moderateActivity);
                show(check1, check2, check3, check4);
            }
        });
        moderateActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked = 3;
                setColor(moderateActivity, "#244AC7EF", "#80D9D9D9", highActivity, lightActivity, sedentaryActivity);
                show(check3, check1, check2, check4);
            }
        });
        highActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked = 4;
                setColor(highActivity, "#244AC7EF", "#80D9D9D9", sedentaryActivity, lightActivity, moderateActivity);
                show(check4, check1, check3, check2);
            }
        });
        lightActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked = 2;
                setColor(lightActivity, "#244AC7EF", "#80D9D9D9", highActivity, sedentaryActivity, moderateActivity);
                show(check2, check1, check3, check4);
            }
        });
    }
    private void setColor(CardView cardView, String color1, String color2, CardView cardView1, CardView cardView2, CardView cardView3){
        cardView.setCardBackgroundColor(Color.parseColor(color1));
        cardView1.setCardBackgroundColor(Color.parseColor(color2));
        cardView2.setCardBackgroundColor(Color.parseColor(color2));
        cardView3.setCardBackgroundColor(Color.parseColor(color2));
    }
    private void show(ImageView check, ImageView i1, ImageView i2, ImageView i3){
        check.setVisibility(View.VISIBLE);
        check.setColorFilter(Color.parseColor("#0079FF"));
        i1.setVisibility(View.INVISIBLE);
        i2.setVisibility(View.INVISIBLE);
        i3.setVisibility(View.INVISIBLE);
    }
    @Override
    public void onBackPressed() {

    }
}