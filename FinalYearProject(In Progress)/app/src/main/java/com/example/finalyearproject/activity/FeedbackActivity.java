package com.example.finalyearproject.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.finalyearproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class FeedbackActivity extends AppCompatActivity implements View.OnClickListener {

    EditText feedback;
    Spinner dropdown;
    Button submit;
    List<String> items;
    ArrayAdapter<String> adapter;
    FirebaseAuth fAuth;
    FirebaseUser fUser;
    String sFeedback, sRating, sName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        Intent intent = getIntent();

        dropdown = (Spinner) findViewById(R.id.dropdown);
        feedback = (EditText) findViewById(R.id.feedback);
        submit = (Button) findViewById(R.id.submit);

        submit.setOnClickListener(this);

        setDropdown();

        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.submit) {
            sFeedback = feedback.getText().toString();
            sRating = dropdown.getSelectedItem().toString();
            sName= fUser.getDisplayName();

            //send email
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"keanchin980156@gmail.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT, sRating);
            intent.putExtra(Intent.EXTRA_TEXT, sFeedback + "\n\nFrom\n" + sName);
            try {
                startActivity(Intent.createChooser(intent, "Sending..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
            }
        }
        feedback.setText("");
        setDropdown();
    }

    private void setDropdown() {
        dropdown.setPrompt("Rating");
        items = new ArrayList<String>();

        items.add("Excellent");
        items.add("Good");
        items.add("Normal");
        items.add("Bad");
        adapter = new ArrayAdapter<>(this, R.layout.spinner_item, items);
        dropdown.setAdapter(adapter);
    }
}