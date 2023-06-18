package com.example.waterintakereminder.Fragments.HistoryManager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.waterintakereminder.Database.DBHandler;
import com.example.waterintakereminder.Fragments.HistoryManager.RecyclerView.HistoryAdapter;
import com.example.waterintakereminder.Fragments.HistoryManager.RecyclerView.HistoryModel;
import com.example.waterintakereminder.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class historyFragment extends Fragment {

    public historyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_history, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DBHandler dbHandler = new DBHandler(getContext());
        List<HistoryModel> historyModels = dbHandler.getHistory();
        Collections.reverse(historyModels);
        HistoryAdapter adapter = new HistoryAdapter(historyModels, getContext());

        if (!historyModels.isEmpty()){
            TextView textView = view.findViewById(R.id.emptyText);
            textView.setText("");
            recyclerView.setAdapter(adapter);
        }
        return view;
    }
}