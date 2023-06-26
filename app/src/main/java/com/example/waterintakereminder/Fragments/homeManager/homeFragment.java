package com.example.waterintakereminder.Fragments.homeManager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.waterintakereminder.Database.DBHandler;
import com.example.waterintakereminder.R;
import com.example.waterintakereminder.calculateAmount;
import com.example.waterintakereminder.Dialogs.CorneredDialogCupSelector;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.yangp.ypwaveview.YPWaveView;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class homeFragment extends Fragment {
    private FloatingActionButton cupSelector, add;
    public static int dailyAmount = 0;
    private LocalDate prev;
    private static boolean flag = false;
    private YPWaveView progress;
    private int glassValue, dailyNeedValue, current;
    protected static MaterialTextView dailyConsumptionCurrent, dailyConsumptionNeed, currentAddingAmountShower;
    public DBHandler db;
    public homeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        db = new DBHandler(getContext());
        /*if (!flag){
            db.insertUserDetails();
            flag = true;
        }*/
        f();
        currentAddingAmountShower = view.findViewById(R.id.currentAddingAmountShower);
        if (db.size()==0){
            db.updateAmount(125);
            currentAddingAmountShower.setText("Amount: "+str(125)+" ml");
        }else {
            currentAddingAmountShower.setText("Amount: "+str(db.getCurrAmount())+" ml");
        }

        current=db.current();

        dailyConsumptionCurrent = view.findViewById(R.id.dailyConsumptionCurrent);
        dailyConsumptionNeed = view.findViewById(R.id.dailyConsumptionNeed);
        setText(view, str(current), str(dailyNeedValue));

        progress = view.findViewById(R.id.progress_circular);
        progress.setMax(dailyNeedValue);
        progress.setProgress(Math.min(current, dailyNeedValue));

        add = view.findViewById(R.id.add);

        add.setOnClickListener(view12 -> {
            current = db.current()+db.getCurrAmount();
            progress.setProgress(Math.min(current, dailyNeedValue));
            setText(view12, str(current), str(dailyNeedValue));
            db.regularAmountInsertion(db.getCurrAmount());
        });

        cupSelector = view.findViewById(R.id.cupSelector);
        cupSelector.setOnClickListener(view1 -> {
            showDialogCupSelector();
        });
        return view;
    }


    private void showDialogCupSelector(){
        CorneredDialogCupSelector dialog = new CorneredDialogCupSelector(getContext());
        dialog.setCancelable(false);
        dialog.show();
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
                    db.updateAmount(Integer.parseInt(qty));
                    currentAddingAmountShower.setText("Amount: "+str(Integer.parseInt(qty))+" ml");
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }
    private void f(){
        List<String> list = db.getUserValues();
        calculateAmount amount = new calculateAmount(list.get(1), list.get(2), list.get(3), list.get(4), list.get(5), list.get(6));
        dailyNeedValue = amount.calculate();
        dailyAmount = dailyNeedValue;
    }
    static String str(int val){
        return String.valueOf(val);
    }
    private static void setText(View view, String current, String dailyNeed){
        dailyConsumptionCurrent.setText(current);
        dailyConsumptionNeed.setText(dailyNeed);
    }
}