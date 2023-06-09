package com.example.waterintakereminder.onBoardings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.waterintakereminder.R;
import com.example.waterintakereminder.Database.userDetails;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.shawnlin.numberpicker.NumberPicker;

public class onBoardingWeight extends AppCompatActivity {
    ExtendedFloatingActionButton nextButtonWeight;
    FloatingActionButton prevButtonWeight;
    NumberPicker numberPicker;
    CardView kg, lbs;
    private String weightUnit = "kg";
    private int weight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding_weight);
        userDetails details = new userDetails();
        init();
        numberPicker.setValue(60);
        numberPicker.setMaxValue(1000);
        numberPicker.setMinValue(1);
        weight = numberPicker.getValue();

        nextButtonWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weight = numberPicker.getValue();
                // Get an instance of SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("weight", String.valueOf(weight));
                editor.putString("weightUnit", weightUnit);
                editor.apply();

                details.setWeight(String.valueOf(weight));
                details.setWeightUnit(weightUnit);
                Intent intent = new Intent(onBoardingWeight.this, onBoardingActivityLevel.class);
                startActivity(intent);
            }
        });
        prevButtonWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(onBoardingWeight.this, onBoardingNameAndGender.class);
                startActivity(intent);
            }
        });
        kg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               weightUnit = "kg";
                kg.setCardBackgroundColor(Color.parseColor("#244AC7EF"));
                lbs.setCardBackgroundColor(Color.parseColor("#80D9D9D9"));
            }
        });
        lbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               weightUnit = "lbs";
               lbs.setCardBackgroundColor(Color.parseColor("#244AC7EF"));
               kg.setCardBackgroundColor(Color.parseColor("#80D9D9D9"));
            }
        });
    }
    private void init(){
        nextButtonWeight = findViewById(R.id.nextButtonWeight);
        prevButtonWeight = findViewById(R.id.prevButtonWeight);
        prevButtonWeight.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)));
        numberPicker = findViewById(R.id.numberPicker);
        kg = findViewById(R.id.kg);
        kg.setCardBackgroundColor(Color.parseColor("#244AC7EF"));
        lbs = findViewById(R.id.lbs);
        lbs.setCardBackgroundColor(Color.parseColor("#80D9D9D9"));
    }
    @Override
    public void onBackPressed() {

    }
}