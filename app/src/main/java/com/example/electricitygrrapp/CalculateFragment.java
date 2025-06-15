package com.example.electricitygrrapp;

import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.fragment.app.Fragment;

public class CalculateFragment extends Fragment {

    Spinner monthSpinner;
    EditText unitInput, rebateInput;
    TextView resultOutput;
    Button calculateBtn, saveBtn;
    DatabaseHelper db;

    String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculate, container, false);

        db = new DatabaseHelper(getContext());

        monthSpinner = view.findViewById(R.id.spinner_month);
        unitInput = view.findViewById(R.id.edit_unit);
        rebateInput = view.findViewById(R.id.edit_rebate);
        resultOutput = view.findViewById(R.id.text_result);
        calculateBtn = view.findViewById(R.id.btn_calculate);
        saveBtn = view.findViewById(R.id.btn_save);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, months);
        monthSpinner.setAdapter(adapter);

        calculateBtn.setOnClickListener(v -> {
            if (unitInput.getText().toString().isEmpty()) {
                unitInput.setError("Please enter units");
                return;
            }
            if (rebateInput.getText().toString().isEmpty()) {
                rebateInput.setError("Please enter rebate");
                return;
            }

            int units = Integer.parseInt(unitInput.getText().toString());
            double rebate = Double.parseDouble(rebateInput.getText().toString());

            if (rebate < 0 || rebate > 5) {
                rebateInput.setError("Rebate must be between 0% and 5%");
                return;
            }

            double total = calculateCharges(units);
            double finalCost = total - (total * rebate / 100.0);
            resultOutput.setText("Total: RM " + String.format("%.2f", total) + "\nFinal: RM " + String.format("%.2f", finalCost));
        });

        saveBtn.setOnClickListener(v -> {
            if (unitInput.getText().toString().isEmpty()) {
                unitInput.setError("Please enter units");
                return;
            }
            if (rebateInput.getText().toString().isEmpty()) {
                rebateInput.setError("Please enter rebate");
                return;
            }

            int units = Integer.parseInt(unitInput.getText().toString());
            double rebate = Double.parseDouble(rebateInput.getText().toString());

            if (rebate < 0 || rebate > 5) {
                rebateInput.setError("Rebate must be between 0% and 5%");
                return;
            }

            String month = monthSpinner.getSelectedItem().toString();
            double total = calculateCharges(units);
            double finalCost = total - (total * rebate / 100.0);

            db.insertData(month, units, total, rebate, finalCost);
            Toast.makeText(getContext(), "Saved to history", Toast.LENGTH_SHORT).show();
        });

        return view;
    }

    private double calculateCharges(int unit) {
        double total = 0;
        if (unit <= 200) total = unit * 0.218;
        else if (unit <= 300) total = 200 * 0.218 + (unit - 200) * 0.334;
        else if (unit <= 600) total = 200 * 0.218 + 100 * 0.334 + (unit - 300) * 0.516;
        else total = 200 * 0.218 + 100 * 0.334 + 300 * 0.516 + (unit - 600) * 0.546;
        return total;
    }
}
