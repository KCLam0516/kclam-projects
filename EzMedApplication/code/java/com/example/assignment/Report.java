package com.example.assignment;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Report extends AppCompatActivity {
    DatabaseHelper myDb;
    TextView no_box, temperature_box, date_box, address_box;

    StringBuilder sNo = new StringBuilder();
    StringBuilder sTemperature = new StringBuilder();
    StringBuilder sDate = new StringBuilder();
    StringBuilder sAddress = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);
        myDb = new DatabaseHelper(this);
        no_box = findViewById(R.id.no_box);
        temperature_box = findViewById(R.id.temperature_box);
        date_box = findViewById(R.id.date_box);
        address_box = findViewById(R.id.address_box);

        Cursor res = myDb.getAllData();
        if (res.getCount() == 0) {
            Toast.makeText(Report.this,"No Record Found",Toast.LENGTH_LONG).show();
            return;
        }

        while (res.moveToNext()) {
        sNo.append(res.getString(0)+"\n");
        sTemperature.append(res.getString(1)+"\n");
        sDate.append(res.getString(2)+"\n");
        sAddress.append(res.getString(3)+"\n");
        }
        no_box.setText(sNo);
        temperature_box.setText(sTemperature);
        date_box.setText(sDate);
        address_box.setText(sAddress);
    }
}

