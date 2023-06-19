package com.example.waterintakereminder.Fragments.homeManager;

import static com.example.waterintakereminder.Fragments.homeManager.homeFragment.currentAddingAmountShower;
import static com.example.waterintakereminder.Fragments.homeManager.homeFragment.str;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.waterintakereminder.Database.DBHandler;
import com.example.waterintakereminder.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class CustomDialog extends Dialog {
    public CustomDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cup_selector_dialog_box);
        DBHandler db = new DBHandler(getContext());

    }
}
