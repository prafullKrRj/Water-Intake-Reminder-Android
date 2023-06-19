package com.example.waterintakereminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

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
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.container, new homeFragment());
        ft.commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id==R.id.settings){
                changeFragment(new SettingsFragment());
            }else if (id==R.id.analytics)
                changeFragment(new analyticsFragment());
            else if (id==R.id.alarmManager)
                changeFragment(new alarmManagerFragment());
            else if (id==R.id.history)
                changeFragment(new historyFragment());
            else
                changeFragment(new homeFragment());
            return true;
        });

    }



    public void changeFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.commit();
    }
}