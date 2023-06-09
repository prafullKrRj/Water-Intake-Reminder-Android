package com.example.waterintakereminder.Fragments.drawerFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.waterintakereminder.Database.DBHandler;
import com.example.waterintakereminder.R;
import com.example.waterintakereminder.Database.userDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link aboutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class aboutFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and changeUserDetails types of parameters
    private String mParam1;
    private String mParam2;

    public aboutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment aboutFragment.
     */
    // TODO: Rename and changeUserDetails types and number of parameters
    public static aboutFragment newInstance(String param1, String param2) {
        aboutFragment fragment = new aboutFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        userDetails details = new userDetails();
        DBHandler dbHandler = new DBHandler(getContext());
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        TextView textView = view.findViewById(R.id.text);
        List<String> list = dbHandler.getUserValues();
        StringBuilder str = new StringBuilder();
        for (String s: list){
            str.append(s).append("\n");
        }
        textView.setText(str.toString());
        return view;
    }
}