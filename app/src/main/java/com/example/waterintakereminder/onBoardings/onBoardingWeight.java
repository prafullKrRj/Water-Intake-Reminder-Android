package com.example.waterintakereminder.onBoardings;

import static java.lang.Short.MAX_VALUE;
import static java.lang.Short.MIN_VALUE;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.waterintakereminder.R;
import com.tistory.dwfox.dwrulerviewlibrary.view.DWRulerSeekbar;
import com.tistory.dwfox.dwrulerviewlibrary.view.ScrollingValuePicker;

public class onBoardingWeight extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding_weight);

    }
}