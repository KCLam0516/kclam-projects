package com.example.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomePage extends AppCompatActivity {

    EditText username, password;
    Button login, register;
    String strUserName,strPassword;
    DatabaseLoginHelper databaseLoginHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        databaseLoginHelper = new DatabaseLoginHelper(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameValue = username.getText().toString();
                String passwordValue = password.getText().toString();
                if(validateAll() == true) {
                    if (databaseLoginHelper.isLonginValid(usernameValue, passwordValue)) {
                        Intent intent = new Intent(HomePage.this, CovidInfo.class);
                        startActivity(intent);
                        Toast.makeText(HomePage.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(HomePage.this, "Invalid Username or Password !!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, Register.class);
                startActivity(intent);
            }
        });
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

    private boolean validateAll() {
        if (validateUserName() == true && validatePassword() == true) {
            return true;
        }
        else {
            return false;
        }
    }


}
