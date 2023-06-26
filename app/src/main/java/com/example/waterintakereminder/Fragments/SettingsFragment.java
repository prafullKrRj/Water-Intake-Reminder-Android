package com.example.waterintakereminder.Fragments;

import static com.example.waterintakereminder.Fragments.homeManager.homeFragment.dailyAmount;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;

import com.example.waterintakereminder.Dialogs.CorneredDialogGenderChange;
import com.example.waterintakereminder.Dialogs.CorneredDialogWeight;
import com.example.waterintakereminder.Dialogs.LanguageChangeDialog;
import com.example.waterintakereminder.Fragments.homeManager.homeFragment;

import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.waterintakereminder.Database.DBHandler;
import com.example.waterintakereminder.R;
import com.example.waterintakereminder.calculateAmount;
import com.example.waterintakereminder.changes;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class SettingsFragment extends Fragment {
    private ConstraintLayout furtherLayout, languageLayout, unitChangeLayout, weightChangeLayout, genderChangeLayout, shareLayout,
            privacyPolicyLayout;
    private TextView username, weight, current;
    private DBHandler handler;
    private changes changes;
    public SettingsFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        changes = new changes(getContext());
        init(view);
        handler = new DBHandler(getContext());
        setProfileText();
        genderChangeLayout.setOnClickListener(this::changeGender);
        weightChangeLayout.setOnClickListener(this::changeWeight);
        return view;
    }
    private void changeLanguage(){
        languageLayout.setOnClickListener(view -> {
            LanguageChangeDialog dialog = new LanguageChangeDialog(getContext());
            dialog.show();

        });
    }
    private void changeGender(View view){
        CorneredDialogGenderChange dialogGenderChange = new CorneredDialogGenderChange(getContext());
        dialogGenderChange.setCancelable(false);
        dialogGenderChange.show();
        AppCompatRadioButton maleButton = dialogGenderChange.findViewById(R.id.maleRadioButton);
        AppCompatRadioButton femaleButton = dialogGenderChange.findViewById(R.id.femaleRadioButton);
        String gender = handler.getUserValues().get(2);
        if (gender.equals("male")) {
            maleButton.setChecked(true);
        } else {
            femaleButton.setChecked(true);
        }
        maleButton.setOnClickListener(view12 -> {
            femaleButton.setChecked(false);
            maleButton.setChecked(true);
        });
        femaleButton.setOnClickListener(view1 -> {
            maleButton.setChecked(false);
            femaleButton.setChecked(true);
        });
        dialogGenderChange.findViewById(R.id.ok_button_gender).setOnClickListener(view2 -> {
            changes.changeGender((maleButton.isChecked()) ? "male" : "female");
            dialogGenderChange.dismiss();
        });
        dialogGenderChange.findViewById(R.id.cancel_button_gender).setOnClickListener(view3 -> {
            dialogGenderChange.dismiss();
        });
    }

    private void changeWeight(View view){
        CorneredDialogWeight dialog = new CorneredDialogWeight(getContext());
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
                    changes.changeWeight(weight.getText().toString());
                    dialog.dismiss();
                }
            }
        });
    }
    private void init(View view){
        furtherLayout = view.findViewById(R.id.furtherLayout);
        languageLayout = view.findViewById(R.id.languageLayout);
        unitChangeLayout = view.findViewById(R.id.unitChangeLayout);
        weightChangeLayout = view.findViewById(R.id.weightChangeLayout);
        genderChangeLayout = view.findViewById(R.id.genderChangeLayout);
        shareLayout = view.findViewById(R.id.shareLayout);
        privacyPolicyLayout = view.findViewById(R.id.privacyPolicyLayout);
        username = view.findViewById(R.id.usernameSettings);
        weight = view.findViewById(R.id.weightProfileSettings);
        current = view.findViewById(R.id.dailyIntakeProfileSettings);
    }
    private void setProfileText(){
        List<String> list = handler.getUserValues();
        username.setText(list.get(0));

        weight.setText("Weight: "+list.get(3));
        current.setText(handler.current()+"/"+dailyAmount);
    }
}