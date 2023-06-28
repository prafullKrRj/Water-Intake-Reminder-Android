package com.example.waterintakereminder.Fragments.homeManager;

import static com.example.waterintakereminder.Database.params_db.params.ACTIVITY_LEVEL;
import static com.example.waterintakereminder.Database.params_db.params.COOL;
import static com.example.waterintakereminder.Database.params_db.params.HIGH_ACTIVITY;
import static com.example.waterintakereminder.Database.params_db.params.HOT;
import static com.example.waterintakereminder.Database.params_db.params.LIGHT_ACT;
import static com.example.waterintakereminder.Database.params_db.params.MODERATE_ACT;
import static com.example.waterintakereminder.Database.params_db.params.MODERATE_TEMP;
import static com.example.waterintakereminder.Database.params_db.params.SEDENTARY_ACT;
import static com.example.waterintakereminder.Database.params_db.params.WEATHER;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.waterintakereminder.Database.DBHandler;
import com.example.waterintakereminder.Dialogs.activityDialog;
import com.example.waterintakereminder.Dialogs.weatherDialog;
import com.example.waterintakereminder.R;
import com.example.waterintakereminder.calculateAmount;
import com.example.waterintakereminder.Dialogs.CorneredDialogCupSelector;
import com.google.android.material.card.MaterialCardView;
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
    private ImageView weatherSelector, activitySelector;
    ImageView sedentaryToggle, lightToggle, moderateToggle, highToggle, hotT, cold, modeW;
    MaterialCardView sedentary;
    private int glassValue, dailyNeedValue, current;
    protected static MaterialTextView dailyConsumptionCurrent, dailyConsumptionNeed, currentAddingAmountShower;
    public DBHandler db;
    private String nWeatherSelected, nActivitySelected;
    public homeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        db = new DBHandler(getContext());
        f();
        init(view);
        if (db.size()==0){
            db.updateAmount(125);
            currentAddingAmountShower.setText("Amount: "+str(125)+" ml");
        }else {
            currentAddingAmountShower.setText("Amount: "+str(db.getCurrAmount())+" ml");
        }

        current=db.current();

        setText(view, str(current), str(dailyNeedValue));

        progress.setMax(dailyNeedValue);
        progress.setProgress(Math.min(current, dailyNeedValue));

        add.setOnClickListener(view12 -> {
            current = db.current()+db.getCurrAmount();
            progress.setProgress(Math.min(current, dailyNeedValue));
            setText(view12, str(current), str(dailyNeedValue));
            db.regularAmountInsertion(db.getCurrAmount());
        });

        cupSelector.setOnClickListener(view1 -> {
            showDialogCupSelector();
        });
        weatherSelector.setOnClickListener(this::weatherSelector);
        activitySelector.setOnClickListener(this::activitySelector);
        return view;
    }


    private void init(View view){
        currentAddingAmountShower = view.findViewById(R.id.currentAddingAmountShower);
        dailyConsumptionCurrent = view.findViewById(R.id.dailyConsumptionCurrent);
        dailyConsumptionNeed = view.findViewById(R.id.dailyConsumptionNeed);
        progress = view.findViewById(R.id.progress_circular);
        add = view.findViewById(R.id.add);
        cupSelector = view.findViewById(R.id.cupSelector);
        weatherSelector = view.findViewById(R.id.weatherSelector);
        activitySelector = view.findViewById(R.id.activitySelector);
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
        calculateAmount amount = new calculateAmount((list.get(1)), list.get(2), (list.get(3)), list.get(4), list.get(5), list.get(6));
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

    private void activitySelector(View view){
        activityDialog dialog = new activityDialog(getContext());
        dialog.show();
        dialog.setCancelable(false);
        sedentaryToggle = dialog.findViewById(R.id.sedentary_toggle_dialog_activity);
        lightToggle = dialog.findViewById(R.id.light_toggle_dialog_activity);
        moderateToggle = dialog.findViewById(R.id.moderate_toggle_dialog_activity);
        highToggle = dialog.findViewById(R.id.high_toggle_dialog_activity);
        MaterialCardView sedentary = dialog.findViewById(R.id.sedentary_dialog_activity);
        MaterialCardView light = dialog.findViewById(R.id.light_dialog_activity);
        MaterialCardView moderate = dialog.findViewById(R.id.moderate_dialog_activity);
        MaterialCardView high = dialog.findViewById(R.id.high_dialog_activity);
        getCurrentActivity(dialog);
        sedentary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setColorActivity(sedentary, "#244AC7EF", "#80D9D9D9", light, moderate, high);
                showActivity(sedentaryToggle, moderateToggle, lightToggle, highToggle);
                nActivitySelected = SEDENTARY_ACT;
            }
        });
        light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setColorActivity(light, "#244AC7EF", "#80D9D9D9", sedentary, moderate, high);
                showActivity(lightToggle, moderateToggle, sedentaryToggle, highToggle);
                nActivitySelected = LIGHT_ACT;
            }
        });
        moderate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setColorActivity(moderate, "#244AC7EF", "#80D9D9D9", light, sedentary, high);
                showActivity(moderateToggle, sedentaryToggle, lightToggle, highToggle);
                nActivitySelected = MODERATE_ACT;
            }
        });
        high.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setColorActivity(high, "#244AC7EF", "#80D9D9D9", light, moderate, sedentary);
                showActivity(highToggle, moderateToggle, lightToggle, sedentaryToggle);
                nActivitySelected = HIGH_ACTIVITY;
            }
        });
        (dialog.findViewById(R.id.cancel_button_activityChange)).setOnClickListener(view1 -> {dialog.dismiss();});
        (dialog.findViewById(R.id.ok_btn_activityChange)).setOnClickListener(view1 -> {
            db.changeUserDetails(ACTIVITY_LEVEL, nActivitySelected);
            dialog.dismiss();
        });
    }

    private void getCurrentActivity(activityDialog dialog) {
        List<String> values = db.getUserValues();
        String curr =  values.get(5);
        if (curr.equals(SEDENTARY_ACT)) {
            showActivity(sedentaryToggle, lightToggle, moderateToggle, highToggle);
        }else if (curr.equals(LIGHT_ACT)){
            showActivity(lightToggle, sedentaryToggle, moderateToggle, highToggle);
        }else if (curr.equals(MODERATE_ACT)){
            showActivity(moderateToggle, lightToggle, sedentaryToggle, highToggle);
        }else if (curr.equals(HIGH_ACTIVITY)){
            showActivity(highToggle, lightToggle, moderateToggle, sedentaryToggle);
        }
    }
    private void setColorActivity(CardView cardView, String color1, String color2, CardView cardView1, CardView cardView2, CardView cardView3){
        cardView.setCardBackgroundColor(Color.parseColor(color1));
        cardView1.setCardBackgroundColor(Color.parseColor(color2));
        cardView2.setCardBackgroundColor(Color.parseColor(color2));
        cardView3.setCardBackgroundColor(Color.parseColor(color2));
    }
    private void showActivity(ImageView check, ImageView i1, ImageView i2, ImageView i3){
        check.setVisibility(View.VISIBLE);
        check.setColorFilter(Color.parseColor("#0079FF"));
        i1.setVisibility(View.INVISIBLE);
        i2.setVisibility(View.INVISIBLE);
        i3.setVisibility(View.INVISIBLE);
    }

    private void weatherSelector(View view) {
        weatherDialog dialog = new weatherDialog(getContext());
        dialog.show();
        dialog.setCancelable(false);
        hotT = dialog.findViewById(R.id.hotToggleHome);
        cold = dialog.findViewById(R.id.coldToggleHome);
        modeW = dialog.findViewById(R.id.moderateToggleHome);
        MaterialCardView hot = dialog.findViewById(R.id.homeHotW);
        MaterialCardView mod = dialog.findViewById(R.id.homeModW);
        MaterialCardView cool = dialog.findViewById(R.id.homeCoolW);
        getCurrentWeather(dialog);

        hot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setColor(hot, "#244AC7EF", "#80D9D9D9", mod, cool);
                show(hotT, cold, modeW);
                nWeatherSelected = HOT;
            }
        });
        mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setColor(mod, "#244AC7EF", "#80D9D9D9", hot, cool);
                show(modeW, cold, hotT);
                nWeatherSelected = MODERATE_TEMP;
            }
        });
        cool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setColor(cool, "#244AC7EF", "#80D9D9D9", mod, hot);
                show(cold, hotT, modeW);
                nWeatherSelected = COOL;
            }
        });
        (dialog.findViewById(R.id.cancel_button_weatherChange)).setOnClickListener(view1 -> {dialog.dismiss();});
        (dialog.findViewById(R.id.ok_btn_weatherChange)).setOnClickListener(view1 -> {
            db.changeUserDetails(WEATHER, nWeatherSelected);
            dialog.dismiss();
        });
    }
    private void setColor(CardView cardView, String color1, String color2, CardView cardView1, CardView cardView2){
        cardView.setCardBackgroundColor(Color.parseColor(color1));
        cardView1.setCardBackgroundColor(Color.parseColor(color2));
        cardView2.setCardBackgroundColor(Color.parseColor(color2));
    }
    private void show(ImageView check, ImageView i1, ImageView i2){
        check.setVisibility(View.VISIBLE);
        check.setColorFilter(Color.parseColor("#0079FF"));
        i1.setVisibility(View.INVISIBLE);
        i2.setVisibility(View.INVISIBLE);
    }
    private void getCurrentWeather(weatherDialog dialog){
        List<String> values = db.getUserValues();
        String curr =  values.get(6);
        if (curr.equals(HOT)){
            show(hotT, cold, modeW);
        }else if (curr.equals(MODERATE_TEMP)){
            show(modeW, cold, hotT);
        }else if (curr.equals(COOL)){
            show(cold, hotT, modeW);
        }
    }
}