package com.example.waterintakereminder.onBoardings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.example.waterintakereminder.MainActivity;
import com.example.waterintakereminder.R;

public class mainOnBoardingSplash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_onboarding_splsah);
        // Get an instance of SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        Button btn = findViewById(R.id.btn);
        btn.setBackgroundColor(Color.parseColor("#4AC7EF"));
        btn.setOnClickListener(view -> {
            Intent intent = new Intent(mainOnBoardingSplash.this, onBoardingNameAndGender.class);
            startActivity(intent);
        });
       // tog.setColorFilter(Color.parseColor("#ff3564"));

    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}