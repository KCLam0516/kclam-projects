package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LocationRecorder extends AppCompatActivity implements View.OnClickListener {
    DatabaseHelper myDb;
    Button second, third, forth, fifth;
    EditText editTemperature,editLocation;
    Button btnAddData,btnReport;
    TextView temp_output_box, address_output_box;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_recorder);
        myDb = new DatabaseHelper(this);
        Intent intent = getIntent();

        second = (Button) findViewById(R.id.second);
        third = (Button) findViewById(R.id.third);
        forth = (Button) findViewById(R.id.forth);
        fifth = (Button) findViewById(R.id.fifth);
        editLocation=(EditText) findViewById(R.id.location_input);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        second.setOnClickListener(this);
        third.setOnClickListener(this);
        forth.setOnClickListener(this);
        fifth.setOnClickListener(this);
        editLocation.setOnClickListener(this);

        temp_output_box = findViewById(R.id.temp_output);
        address_output_box = findViewById(R.id.address_output);

        findViewById(R.id.report).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Report.class));
            }
        });

        editTemperature = (EditText)findViewById(R.id.temp_input);
        editLocation = (EditText)findViewById(R.id.location_input);
        btnAddData = (Button)findViewById(R.id.add);
        btnReport = (Button)findViewById(R.id.report);

        AddData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                startActivity(intent);
                Toast.makeText(this, "Log Out Successfully", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void AddData() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(View view) {
                        Date date = new Date();
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
                        if (tempCheck(editTemperature) && locationCheck(editLocation)) {
                           boolean isInserted = myDb.insertData(editTemperature.getText().toString(),
                                   formatter.format(date),
                                    editLocation.getText().toString());
                           if(isInserted) {
                               temp_output_box.setText(editTemperature.getText().toString()+"°c");
                               address_output_box.setText(editLocation.getText().toString());
                               Toast.makeText(LocationRecorder.this, "Data Inserted", Toast.LENGTH_LONG).show();
                           } else {
                               Toast.makeText(LocationRecorder.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                           }
                        }
                    }
                }
        );
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        if (id == R.id.second) {
            Intent intent = new Intent(getApplicationContext(), CovidInfo.class);
            startActivity(intent);
        } else if (id == R.id.third) {
            Intent intent = new Intent(getApplicationContext(), CovidChecking.class);
            startActivity(intent);
        } else if (id == R.id.forth) {
            Intent intent = new Intent(getApplicationContext(), Notification.class);
            startActivity(intent);
        } else if (id == R.id.fifth) {
            Intent intent = new Intent(getApplicationContext(), LocationRecorder.class);
            startActivity(intent);
        }
    }

    private boolean tempCheck(EditText field) {
        if (field.getText().toString().isEmpty()) {
            field.setError("Please fill in the blank");
            return false;
        }
        try {
            Float.parseFloat(field.getText().toString());
            if( Float.parseFloat(field.getText().toString())<35 ||  Float.parseFloat(field.getText().toString())>40){
                field.setError("Please Insert a number range between 35-40");
                return false;
            }
            field.setError(null);
            return true;
        } catch (NumberFormatException ex) {
            field.setError("Please Insert a number");
            return false;
        }
    }

    private boolean locationCheck(EditText field) {
        if (field.getText().toString().isEmpty()) {
            field.setError("Please fill in the blank");
            return false;
        } else {
            field.setError(null);
            return true;
        }
    }
}