package com.example.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity implements View.OnClickListener {
    EditText id;
    Button login;
    String listId, listName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        id = findViewById(R.id.id);
        login = findViewById(R.id.login);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ArrayList<String> nameList= new ArrayList<String>();
        nameList.add("10101,Steven");
        nameList.add("17845,Sofi");
        nameList.add("20157,Ahmad");
        nameList.add("31202,Natalie");
        nameList.add("36834,Wani");

        if(isNotEmptyField(id)) {
            if (id.getText().toString().length() == 5){
                for (int i=0;i<nameList.size();i++) {
                    listId = nameList.get(i).split(",")[0];
                    listName = nameList.get(i).split(",")[1];
                    if(id.getText().toString().equals(listId)){
                        Toast.makeText(this, "Welcome, "+listName, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), CalculatorPage.class);
                        intent.putExtra("name",listName);
                        startActivity(intent);
                    }
                }
            } else {
                Toast.makeText(this, "Student ID too short", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isNotEmptyField(EditText field) {
        if(field.getText().toString().isEmpty()){
            field.setError("Please fill in the blank");
            return false;
        } else {
            field.setError(null);
            return true;
        }
    }
}
