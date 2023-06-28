package com.example.waterintakereminder;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

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
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.waterintakereminder.Database.DBHandler;
import com.example.waterintakereminder.Dialogs.exitDialogBox;
import com.example.waterintakereminder.Fragments.settingsManager.SettingsFragment;
import com.example.waterintakereminder.Fragments.analyticsFragment;
import com.example.waterintakereminder.Fragments.HistoryManager.historyFragment;
import com.example.waterintakereminder.Fragments.drawerFragments.aboutFragment;
import com.example.waterintakereminder.Fragments.homeManager.homeFragment;
import com.example.waterintakereminder.Dialogs.CorneredDialogUnits;
import com.example.waterintakereminder.Dialogs.CorneredDialogWeight;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.makeramen.roundedimageview.RoundedImageView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    BottomNavigationView bottomNavigationView;
    public Toolbar toolbar;
    private boolean isToggled = false;
    private ImageButton reminderToggleToolBar;
    private ImageView logoOnToolBar;
    private TextView userNameNavigationDrawer;
    public TextView textViewToolBar;
    private RoundedImageView roundedImageView;
    private String selectedChangeUnit = "ml";
    private String newWeight;
    private DBHandler handler;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new DBHandler(getApplicationContext());
        setAlarmMain();
        List<Integer> sl = handler.getSleepDetails();
        //Toast.makeText(this, String.valueOf(sl.get(1)), Toast.LENGTH_SHORT).show();
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        textViewToolBar = findViewById(R.id.textViewToolBar);
        textViewToolBar.setText("Aqua Rewa");
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
                if (id==R.id.Statistics){
                    changeFragment(new analyticsFragment(), "Stats", INVISIBLE);
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
                    changeFragment(new aboutFragment(), "About", INVISIBLE);
                    textViewToolBar.setText("About");
                    reminderToggleToolBar.setVisibility(INVISIBLE);
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
                changeFragment(new analyticsFragment(), "Stats", INVISIBLE);
            }
            else if (id==R.id.settings){
                changeFragment(new SettingsFragment(), "Settings", INVISIBLE);
            }
            else if (id==R.id.history){
                changeFragment(new historyFragment(), "History", INVISIBLE);
            }
            else{
                changeFragment(new homeFragment(), "Aqua Rewa", VISIBLE);
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
    private void setAlarmMain() {

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


    public void changeFragment(Fragment fragment, String text, int visibility){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.container, fragment);
        textViewToolBar.setText(text);
        reminderToggleToolBar.setVisibility(visibility);
        ft.commit();
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
                    changes changes = new changes(getApplicationContext());
                    changes.changeWeight(newWeight);
                    dialog.dismiss();
                }
            }
        });

    }
    @Override
    public void onBackPressed() {
        exitDialogBox dialogBox = new exitDialogBox(this);
        dialogBox.setCancelable(false);
        dialogBox.show();

        TextView cnl = dialogBox.findViewById(R.id.dontExitBtn);
        TextView exit = dialogBox.findViewById(R.id.exitBtn);
        cnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBox.dismiss();
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
            }
        });
    }
}
