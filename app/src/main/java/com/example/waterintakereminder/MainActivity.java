package com.example.waterintakereminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.utils.widget.ImageFilterButton;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.core.widget.ImageViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.waterintakereminder.Fragments.SettingsFragment;
import com.example.waterintakereminder.Fragments.alarmManagerFragment;
import com.example.waterintakereminder.Fragments.analyticsFragment;
import com.example.waterintakereminder.Fragments.HistoryManager.historyFragment;
import com.example.waterintakereminder.Fragments.homeManager.homeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    public Toolbar toolbar;
    private boolean isToggled = false;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        TextView textViewToolBar = findViewById(R.id.textViewToolBar);
        textViewToolBar.setText("Water Intake Reminder");

        ImageView logoOnToolBar = findViewById(R.id.logoOnToolBar);
        logoOnToolBar.setImageResource(R.drawable.logo);

        ImageButton reminderToggleToolBar = findViewById(R.id.reminderToggleToolBar);
        reminderToggleToolBar.setImageResource(R.drawable.baseline_alarm_on_24);
        reminderToggleToolBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isToggled = !isToggled;
                if (isToggled){
                    reminderToggleToolBar.setImageResource(R.drawable.baseline_alarm_on_24);
                }else{
                    reminderToggleToolBar.setImageResource(R.drawable.baseline_alarm_off_24);
                }
            }
        });
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.container, new homeFragment());
        ft.commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id==R.id.settings){
                changeFragment(new SettingsFragment());
                textViewToolBar.setText("Settings");
                reminderToggleToolBar.setImageDrawable(null);
            }else if (id==R.id.analytics) {
                changeFragment(new analyticsFragment());
                textViewToolBar.setText("Analytics");
                reminderToggleToolBar.setImageDrawable(null);
            }
            else if (id==R.id.alarmManager){
                changeFragment(new alarmManagerFragment());
                textViewToolBar.setText("Reminders");
                reminderToggleToolBar.setImageDrawable(null);
            }
            else if (id==R.id.history){
                changeFragment(new historyFragment());
                textViewToolBar.setText("History");
                reminderToggleToolBar.setImageDrawable(null);
            }
            else{
                changeFragment(new homeFragment());
                textViewToolBar.setText("Water Intake Reminder");
                reminderToggleToolBar.setImageResource((!isToggled) ? R.drawable.baseline_alarm_off_24 : R.drawable.baseline_alarm_on_24);
            }
            return true;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_items, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId==R.id.about){
            return true;
        }else if (itemId==R.id.Weight){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void changeFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.commit();
    }
}