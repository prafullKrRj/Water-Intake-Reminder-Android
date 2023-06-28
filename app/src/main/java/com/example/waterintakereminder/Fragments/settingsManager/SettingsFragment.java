package com.example.waterintakereminder.Fragments.settingsManager;

import static android.view.View.INVISIBLE;
import static com.example.waterintakereminder.Database.params_db.params.LINK;
import static com.example.waterintakereminder.Fragments.homeManager.homeFragment.dailyAmount;

import android.content.Intent;
import android.os.Bundle;

import com.example.waterintakereminder.Dialogs.CorneredDialogGenderChange;
import com.example.waterintakereminder.Dialogs.CorneredDialogWeight;
import com.example.waterintakereminder.Dialogs.LanguageChangeDialog;
import com.example.waterintakereminder.Fragments.homeManager.homeFragment;

import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.waterintakereminder.Database.DBHandler;
import com.example.waterintakereminder.R;
import com.example.waterintakereminder.changes;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.Objects;

public class SettingsFragment extends Fragment {
    private ConstraintLayout furtherLayout, languageLayout, unitChangeLayout, weightChangeLayout, genderChangeLayout, shareLayout,
            privacyPolicyLayout;
    private TextView username, weight, current, reminderSchedule;
    private DBHandler handler;
    private changes changes;
    private SwipeRefreshLayout swipeRefresh;
    public SettingsFragment() {
        // Required empty public constructor
    }
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_settings, container, false);
        changes = new changes(getContext());
        init(view);
        handler = new DBHandler(getContext());
        setProfileText();
        genderChangeLayout.setOnClickListener(this::changeGender);
        weightChangeLayout.setOnClickListener(this::changeWeight);
        shareLayout.setOnClickListener(this::shareLink);
        languageLayout.setOnClickListener(this::changeLanguage);
        swipeRefresh.setOnRefreshListener(this::swipe);
        reminderSchedule.setOnClickListener(this::reminderSchedule);
        return view;
    }

    private void reminderSchedule(View view) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.container, new reminderScheduleReminder());
        ft.commit();
    }

    private void swipe(){
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.container, new SettingsFragment());
        ((TextView) requireActivity().findViewById(R.id.textViewToolBar)).setText("Settings");
        requireActivity().findViewById(R.id.reminderToggleToolBar).setVisibility(INVISIBLE);
        swipeRefresh.setRefreshing(false);
        ft.commit();
    }
    private void changeLanguage(View view){
        LanguageChangeDialog dialog = new LanguageChangeDialog(getContext());
        dialog.show();
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
        reminderSchedule = view.findViewById(R.id.reminderScheduleSettings);
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
        swipeRefresh = view.findViewById(R.id.swipeRefresh);
    }
    private void setProfileText(){
        List<String> list = handler.getUserValues();
        username.setText(list.get(0));

        weight.setText("Weight: "+list.get(3));
        current.setText(handler.current()+"/"+dailyAmount);
    }
    private void shareLink(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, LINK);
        startActivity(Intent.createChooser(intent, "Share link"));
    }
}
