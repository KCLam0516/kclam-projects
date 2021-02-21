package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

public class CovidChecking extends AppCompatActivity implements View.OnClickListener {
    Button second, third, forth, fifth,check;
    CheckBox cough,flu,soreThroat;
    TextView result;
    EditText temperature;
    Toolbar toolbar;
    String sTemperature, sDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.covid_checking);
        Intent intent = getIntent();

        second = (Button) findViewById(R.id.second);
        third = (Button) findViewById(R.id.third);
        forth = (Button) findViewById(R.id.forth);
        fifth = (Button) findViewById(R.id.fifth);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        second.setOnClickListener(this);
        third.setOnClickListener(this);
        forth.setOnClickListener(this);
        fifth.setOnClickListener(this);

        cough = (CheckBox) findViewById(R.id.cough);
        flu = (CheckBox) findViewById(R.id.flu);
        soreThroat =(CheckBox) findViewById(R.id.soreThroat);
        temperature = (EditText)findViewById(R.id.temperature);
        check = (Button) findViewById(R.id.check);
        result = (TextView)findViewById(R.id.resultChecked);

        cough.setOnClickListener(this);
        flu.setOnClickListener(this);
        soreThroat.setOnClickListener(this);
        check.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        sTemperature = temperature.getText().toString();
        int id=view.getId();

        if (id == R.id.check) {
            if (temperatureValidation(temperature)) {
                if (!flu.isChecked() && !cough.isChecked() && !soreThroat.isChecked() && (Double.parseDouble(sTemperature)) >= 37.5){
                    sDisplay = "You are sick. Please stay at home until you get healthy and avoid going to crowded places.";
                    result.setText(sDisplay);
                }else if (flu.isChecked() && cough.isChecked() && soreThroat.isChecked() && (Double.parseDouble(sTemperature)) >= 37.5){
                    sDisplay = "If you have travel to overseas or meet anyone who have covid-19."+"\n" +
                            "Please go to the nearest hospital to have covid-19 test.";
                    result.setText(sDisplay);
                } else if (!flu.isChecked() && !cough.isChecked() && !soreThroat.isChecked() && (Double.parseDouble(sTemperature)) < 37.5) {
                    sDisplay = "You are safe. Remember to wear mask and sanitize your hand when you are away from home";
                    result.setText(sDisplay);
                } else if (flu.isChecked() || cough.isChecked() || soreThroat.isChecked() && (Double.parseDouble(sTemperature)) >= 37.5) {
                    sDisplay = "You are sick. Please stay at home until you get healthy and avoid going to crowded places.";
                    result.setText(sDisplay);
                } else if (flu.isChecked() && cough.isChecked() && soreThroat.isChecked() && (Double.parseDouble(sTemperature)) < 37.5){
                    sDisplay = "You are safe from covid. ";
                    result.setText(sDisplay);
                }
            }
        }

        else if (id == R.id.second) {
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

    private boolean temperatureValidation(EditText field) {
        if (field.getText().toString().isEmpty()) {
            field.setError("Please fill in the blank");
            return false;
        }
        try {
            Float.parseFloat(field.getText().toString());
            field.setError(null);
            return true;
        } catch (NumberFormatException ex) {
            field.setError("Please Insert a number");
            return false;
        }
    }

//    private boolean temperatureValidation(){
//        sTemperature = temperature.getText().toString();
//        if (sTemperature.isEmpty()) {
//            temperature.setError("Please key in something");
//            //Toast.makeText(getApplicationContext(),"Please key in your temperature", Toast.LENGTH_LONG).show();
//            return false;
//        } else {
//            temperature.setError(null);
//            return true;
//        }
//    }
}