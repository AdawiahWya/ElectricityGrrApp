package com.example.electricitygrrapp;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    TextView monthText, unitsText, totalText, rebateText, finalText;
    DatabaseHelper db;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        db = new DatabaseHelper(this);

        // Link your TextViews
        monthText = findViewById(R.id.detail_month);
        unitsText = findViewById(R.id.detail_units);
        totalText = findViewById(R.id.detail_total);
        rebateText = findViewById(R.id.detail_rebate);
        finalText = findViewById(R.id.detail_final);

        // Get the row ID passed from the history list
        id = getIntent().getIntExtra("RECORD_ID", -1);

        if (id != -1) {
            loadDetails(id);
        }
    }

    private void loadDetails(int id) {
        Cursor cursor = db.getSingleRecord(id);
        if (cursor.moveToFirst()) {
            String month = cursor.getString(0);
            int units = cursor.getInt(1);
            double total = cursor.getDouble(2);
            double rebate = cursor.getDouble(3);
            double finalCost = cursor.getDouble(4);

            monthText.setText("Month: " + month);
            unitsText.setText("Units Used: " + units + " kWh");
            totalText.setText("Total Charges: RM " + String.format("%.2f", total));
            rebateText.setText("Rebate: " + rebate + "%");
            finalText.setText("Final Cost: RM " + String.format("%.2f", finalCost));
        }
        cursor.close();
    }
}
