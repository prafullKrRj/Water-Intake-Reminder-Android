package com.example.waterintakereminder.onBoardings;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.waterintakereminder.R;
import com.example.waterintakereminder.Database.userDetails;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.Objects;

public class onBoardingNameAndGender extends AppCompatActivity {
    TextInputEditText nameEditText, ageEditText;
    ExtendedFloatingActionButton nextButtonName;
    FloatingActionButton prevButtonName;
    RoundedImageView imageBoy, imageGirl;
    TextView maleTextView, femaleTextView;
    String gender="male";
    Boolean male;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding_name_gender);
        userDetails details = new userDetails();
        init();
        imageBoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gender.equals("female")){
                    imageBoy.setColorFilter(Color.parseColor("#244AC7EF"));
                    male = true;
                    imageGirl.clearColorFilter();
                    gender = "male";
                }
            }
        });
        imageGirl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gender.equals("male")){
                    imageGirl.setColorFilter(Color.parseColor("#244AC7EF"));
                    imageBoy.clearColorFilter();
                    male = false;
                    gender = "female";
                }
            }
        });
        nextButtonName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Objects.requireNonNull(nameEditText.getText()).toString().isEmpty() || Objects.requireNonNull(ageEditText.getText()).toString().isEmpty()){
                    TextView warningName = findViewById(R.id.warning);
                    warningName.setText(R.string.enter_your_name);
                }
                else{
                    // Get an instance of SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("name", nameEditText.getText().toString());
                    editor.putString("age", ageEditText.getText().toString());
                    editor.putString("gender", gender);
                    editor.apply();

                    details.setName(nameEditText.getText().toString());
                    details.setGender(gender);
                    details.setAge(ageEditText.getText().toString());
                    Intent intent = new Intent(onBoardingNameAndGender.this, onBoardingWeight.class);
                    startActivity(intent);
                }
            }
        });
        prevButtonName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(onBoardingNameAndGender.this, mainOnBoardingSplash.class);
                startActivity(intent);
            }
        });

    }
    private void init(){
        male = true;
        nameEditText = findViewById(R.id.nameEditText);
        ageEditText = findViewById(R.id.ageEditText);
        nextButtonName = findViewById(R.id.nextButtonName);
        prevButtonName = findViewById(R.id.prevButtonName);
        prevButtonName.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)));
        imageBoy = findViewById(R.id.imageBoy);
        imageGirl = findViewById(R.id.imageGirl);
        maleTextView = findViewById(R.id.maleTextView);
        femaleTextView = findViewById(R.id.femaleTextView);
        imageBoy.setClickable(true);
        imageGirl.setClickable(true);
        imageBoy.setColorFilter(Color.parseColor("#244AC7EF"));
    }
    @Override
    public void onBackPressed() {

    }
}