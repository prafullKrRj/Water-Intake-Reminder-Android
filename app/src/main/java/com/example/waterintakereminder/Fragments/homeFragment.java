package com.example.waterintakereminder.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.ColorUtils;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.waterintakereminder.Database.DBHandler;
import com.example.waterintakereminder.MainActivity;
import com.example.waterintakereminder.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.dialog.MaterialDialogs;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.yangp.ypwaveview.YPWaveView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class homeFragment extends Fragment {
    private FloatingActionButton cupSelector, add;
    private LocalDate prev;
    private YPWaveView progress;
    private int glassValue, dailyNeedValue, current;
    private static MaterialTextView dailyConsumptionCurrent;
    private static MaterialTextView dailyConsumptionNeed;
    public static int oneTimeIntake;
    private static boolean clicked = false;
    public DBHandler db;
    public homeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        db = new DBHandler(getContext());
        init();

        current=db.current();

        dailyNeedValue = 2145;
        oneTimeIntake = 125;
        dailyConsumptionCurrent = view.findViewById(R.id.dailyConsumptionCurrent);
        dailyConsumptionNeed = view.findViewById(R.id.dailyConsumptionNeed);
        setText(view, str(current), str(dailyNeedValue));

        progress = view.findViewById(R.id.progress_circular);
        progress.setMax(dailyNeedValue);
        progress.setProgress(Math.min(current, dailyNeedValue));

        add = view.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                current = db.current()+oneTimeIntake;
                progress.setProgress(Math.min(current, dailyNeedValue));
                setText(view, str(current), str(dailyNeedValue));
                db.regularAmountInsertion(oneTimeIntake);
            }
        });
        cupSelector = view.findViewById(R.id.cupSelector);
        cupSelector.setOnClickListener(view1 -> {
            showDialogCupSelector();
        });
        return view;
    }

    private void showDialogCupSelector(){
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.cup_selector_dialog_box);
        dialog.setCancelable(false);

        TextInputEditText quantity = dialog.findViewById(R.id.quantity);

        TextView cancelButton = dialog.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        
        TextView okButton = dialog.findViewById(R.id.ok);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String qty = Objects.requireNonNull(quantity.getText()).toString();
                if (!qty.isEmpty()){
                    oneTimeIntake = Integer.parseInt(qty);
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    private static String str(int val){
        return String.valueOf(val);
    }
    private static void setText(View view, String current, String dailyNeed){
        dailyConsumptionCurrent.setText(current);
        dailyConsumptionNeed.setText(dailyNeed);
    }
    private void init(){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        prev= LocalDate.parse("2023-06-01", dateFormatter);
        LocalDate currDate = LocalDate.now();
        if (prev.getYear()<currDate.getYear()){
            db.dailyFinalAmount(current, prev.toString());
            prev = currDate;
        }else if (prev.getMonthValue()<currDate.getMonthValue()){
            db.dailyFinalAmount(current, prev.toString());
            prev = currDate;
        }else if (prev.getDayOfMonth() < currDate.getDayOfMonth()){
            db.dailyFinalAmount(current, prev.toString());
            prev = currDate;
        }else {
            return;
        }
    }
}