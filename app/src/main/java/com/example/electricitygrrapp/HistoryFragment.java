package com.example.electricitygrrapp;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.fragment.app.Fragment;

public class HistoryFragment extends Fragment {

    ListView listView;
    DatabaseHelper db;
    SimpleCursorAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        db = new DatabaseHelper(getContext());
        listView = view.findViewById(R.id.history_list);

        loadData();

        listView.setOnItemClickListener((parent, v, position, id) -> {
            Intent intent = new Intent(getContext(), DetailActivity.class);
            intent.putExtra("RECORD_ID", (int) id);
            startActivity(intent);
        });

        return view;
    }

    private void loadData() {
        Cursor cursor = db.getAllData();
        adapter = new SimpleCursorAdapter(getContext(), android.R.layout.simple_list_item_2, cursor,
                new String[]{"month", "finalCost"},
                new int[]{android.R.id.text1, android.R.id.text2}, 0);
        listView.setAdapter(adapter);
    }
}

