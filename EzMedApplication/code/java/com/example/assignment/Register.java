package com.example.assignment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity implements View.OnClickListener {

    EditText username, password, email, dob;
    RadioGroup gender;
    Button register,cancel;
    String strUserName, strPassword, strEmail, strDOB;
    DatabaseLoginHelper databaseLoginHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        databaseLoginHelper = new DatabaseLoginHelper(this);
        Intent intent = getIntent();

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        dob = findViewById(R.id.dob);
        gender = findViewById(R.id.gender);
        register = findViewById(R.id.register);
        cancel = findViewById(R.id.cancel);

        dob.setOnClickListener(this);
        cancel.setOnClickListener(this);
        register.setOnClickListener(this);
    }

        @Override
        public void onClick(View view) {
            String usernameValue,passwordValue,emailValue,dobValue ,genderValue;
            int id = view.getId();
            if (id == R.id.register) {
                if (isValidEmail(email.getText().toString())){
                    usernameValue = username.getText().toString();
                    passwordValue = password.getText().toString();
                    emailValue = email.getText().toString();
                    dobValue = dob.getText().toString();
                    RadioButton checkBtn = findViewById(gender.getCheckedRadioButtonId());
                    genderValue = checkBtn.getText().toString();
                    //add
                    if (validateAll() == true) {
                        if (usernameValue.length() > 1) {

                            databaseLoginHelper.insertData(usernameValue,passwordValue,emailValue,dobValue);
                            Toast.makeText(Register.this, "User registered", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), HomePage.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Register.this, "Enter the values!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(Register.this, "Invalid Email Format!!!", Toast.LENGTH_SHORT).show();
                }
            } else if (id == R.id.dob) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                DatePickerDialog picker = new DatePickerDialog(this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        dob.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);
                picker.show();

            } else if (id == R.id.cancel){
                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                startActivity(intent);
            } else if (id == R.id.dob) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                DatePickerDialog picker = new DatePickerDialog(this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        dob.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);
                picker.show();
            } else if (id == R.id.cancel){
                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                startActivity(intent);
            }
        }


    private boolean validateUserName() {
        strUserName = username.getText().toString();

        if(strUserName.isEmpty()) {
            username.setError("Please enter your username!!");
            return false;
        }
        else {
            username.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        strPassword = password.getText().toString();

        if(strPassword.isEmpty()) {
            password.setError("Please enter your password!!");
            return false;
        }
        else {
            password.setError(null);
            return true;
        }
    }

    private boolean validateEmail() {
        strEmail = email.getText().toString();

        if(strEmail.isEmpty()) {
            email.setError("Please enter your email!!");
            return false;
        }
        else {
            email.setError(null);
            return true;
        }
    }

    private boolean validateDOB() {
        strDOB = dob.getText().toString();

        if(strDOB.isEmpty()) {
            dob.setError("Please enter your date of birth!!");
            return false;
        }
        else {
            dob.setError(null);
            return true;
        }
    }

    private boolean validateAll() {
        if (validateUserName() == true && validatePassword() == true && validateEmail() == true && validateDOB() == true) {
            return true;
        }
        else {
            return false;
        }
    }

    private static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null) {
            return false;
        }
        return pat.matcher(email).matches();
    }
}