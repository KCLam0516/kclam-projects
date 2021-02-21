package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class CalculatorPage extends AppCompatActivity implements View.OnClickListener {
    String name;
    EditText loanAmount, interestRate, loanPeriod, loanStartDate;
    Button report;
    DatePickerDialog picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        loanAmount = findViewById(R.id.loanAmount);
        interestRate = findViewById(R.id.interestRate);
        loanPeriod = findViewById(R.id.loanPeriod);
        loanStartDate = findViewById(R.id.loanStartDate);
        report = findViewById(R.id.report);

        loanStartDate.setOnClickListener(this);
        report.setOnClickListener(this);
        name=getIntent().getStringExtra("name");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.report) {
            if (isNotEmptyField(loanAmount) && isNotEmptyField(interestRate) && isNotEmptyField(loanPeriod) && isNotEmptyDate(loanStartDate)) {
                if (Integer.parseInt(loanPeriod.getText().toString()) <= 120) {
                    Intent intent = new Intent(getApplicationContext(), AmortisationSchedulePage.class);
                    intent.putExtra("interestName",name);
                    intent.putExtra("interestRate",interestRate.getText().toString());
                    intent.putExtra("loanAmount",loanAmount.getText().toString());
                    intent.putExtra("loanPeriod",loanPeriod.getText().toString());
                    intent.putExtra("loanStartDate",loanStartDate.getText().toString());
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Invalid Loan Period Value", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (id == R.id.loanStartDate) {
            final Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            picker = new DatePickerDialog(this,R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    loanStartDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                }
            }, year, month, day);
            picker.show();
        }
    }

    private boolean isNotEmptyField(EditText field) {
        if(field.getText().toString().isEmpty()) {
            field.setError("Please fill in the blank");
            return false;
        } try {
            Integer.parseInt(field.getText().toString());
            field.setError(null);
            return true;
        } catch (NumberFormatException ex) {
            field.setError("No decimal point allow");
            return false;
        }
    }

    private boolean isNotEmptyDate(EditText field) {
        if(field.getText().toString().isEmpty()) {
            field.setError("Please select a date");
            return false;
        } else {
            field.setError(null);
            return true;
        }
    }
}
