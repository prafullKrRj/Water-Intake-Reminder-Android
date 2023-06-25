package com.example.waterintakereminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.waterintakereminder.Database.DBHandler;
import com.example.waterintakereminder.Fragments.SettingsFragment;
import com.example.waterintakereminder.Fragments.alarmManagerFragment;
import com.example.waterintakereminder.Fragments.analyticsFragment;
import com.example.waterintakereminder.Fragments.HistoryManager.historyFragment;
import com.example.waterintakereminder.Fragments.drawerFragments.aboutFragment;
import com.example.waterintakereminder.Fragments.homeManager.homeFragment;
import com.example.waterintakereminder.onBoardings.CorneredDialogUnits;
import com.example.waterintakereminder.onBoardings.CorneredDialogWeight;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.makeramen.roundedimageview.RoundedImageView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    BottomNavigationView bottomNavigationView;
    public Toolbar toolbar;
    private boolean isToggled = false;
    private ImageButton reminderToggleToolBar;
    private ImageView logoOnToolBar;
    private TextView textViewToolBar, userNameNavigationDrawer;
    private RoundedImageView roundedImageView;
    private String selectedChangeUnit = "ml";
    private String newWeight;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        textViewToolBar = findViewById(R.id.textViewToolBar);
        textViewToolBar.setText("Water Intake Reminder");
        logoOnToolBar = findViewById(R.id.logoOnToolBar);
        logoOnToolBar.setImageResource(R.drawable.logo);
        reminderToggleToolBar = findViewById(R.id.reminderToggleToolBar);
        reminderToggleToolBar.setImageResource(R.drawable.baseline_alarm_on_24);
        drawerLayout = findViewById(R.id.navigationDrawer);
        DBHandler dbHandler = new DBHandler(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.openDrawer,
                R.string.closeDrawer);
        drawerLayout.addDrawerListener(toggle);

        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.inflateMenu(R.menu.drawer_layout_menu);
        userNameNavigationDrawer = navigationView.getHeaderView(0).findViewById(R.id.userNameNavigationDrawer);
        userNameNavigationDrawer.setText(dbHandler.getUserValues().get(0));
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id==R.id.settings){
                    changeFragment(new SettingsFragment());
                    textViewToolBar.setText("Settings");
                    reminderToggleToolBar.setVisibility(View.INVISIBLE);
                }
                else if (id==R.id.Statistics){
                    toAnalyticsFragment();
                }
                else if (id==R.id.changeUnitsDrawer){
                    changeUnit();
                }
                else if (id==R.id.changeWeightDrawer){
                    changeWeight();
                }
                else if (id==R.id.shareDrawer){

                }
                else if (id==R.id.aboutDrawer){
                    changeFragment(new aboutFragment());
                    textViewToolBar.setText("About");
                    reminderToggleToolBar.setVisibility(View.INVISIBLE);
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
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

        bottomNavigationView = findViewById(R.id.bottomNavView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id==R.id.analytics) {
                toAnalyticsFragment();
            }
            else if (id==R.id.alarmManager){
                toAlarmManagerFragment();
            }
            else if (id==R.id.history){
                toHistoryFragment();
            }
            else{
                changeFragment(new homeFragment());
                textViewToolBar.setText("Water Intake Reminder");
                reminderToggleToolBar.setImageResource((!isToggled) ? R.drawable.baseline_alarm_off_24 : R.drawable.baseline_alarm_on_24);

            }
            return true;
        });
        logoOnToolBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }

    private void changeUnit() {
        CorneredDialogUnits dialog = new CorneredDialogUnits(this);
        dialog.setCancelable(false);
        dialog.show();
        TextView cancel = dialog.findViewById(R.id.cancel_button_weight);
        TextView ok = dialog.findViewById(R.id.okButtonWeightDialogBox);
        CardView cardViewMl = dialog.findViewById(R.id.changeUnitMl);
        CardView cardViewF = dialog.findViewById(R.id.changeUnitFlOz);
        cardViewMl.setCardBackgroundColor(Color.parseColor("#244AC7EF"));

        cardViewMl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedChangeUnit = "ml";
                cardViewMl.setCardBackgroundColor(Color.parseColor("#244AC7EF"));
                cardViewF.setCardBackgroundColor(Color.parseColor("#80D9D9D9"));
            }
        });
        cardViewF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedChangeUnit = "floz";
                cardViewF.setCardBackgroundColor(Color.parseColor("#244AC7EF"));
                cardViewMl.setCardBackgroundColor(Color.parseColor("#80D9D9D9"));
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
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
            changeFragment(new aboutFragment());
        }else if (itemId==R.id.Weight){
            changeWeight();
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
    private void toHistoryFragment(){
        changeFragment(new historyFragment());
        textViewToolBar.setText("History");
        reminderToggleToolBar.setImageDrawable(null);
    }private void toAnalyticsFragment(){
        changeFragment(new analyticsFragment());
        textViewToolBar.setText("Analytics");
        reminderToggleToolBar.setImageDrawable(null);
    }
    private void toAlarmManagerFragment(){
        changeFragment(new alarmManagerFragment());
        textViewToolBar.setText("Reminders");
        reminderToggleToolBar.setImageDrawable(null);
    }
    private void changeWeight(){
        CorneredDialogWeight dialog = new CorneredDialogWeight(this);
        dialog.setCancelable(false);
        dialog.show();
        TextView cancel = dialog.findViewById(R.id.cancel_button_weight);
        TextView ok = dialog.findViewById(R.id.okButtonWeightDialogBox);
        TextInputEditText weight = dialog.findViewById(R.id.newWeight);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Objects.requireNonNull(weight.getText()).toString().isEmpty()){
                    newWeight = weight.getText().toString();

                    dialog.dismiss();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}