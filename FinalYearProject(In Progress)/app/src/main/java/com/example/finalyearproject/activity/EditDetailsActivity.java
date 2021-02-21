package com.example.finalyearproject.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.finalyearproject.R;
import com.example.finalyearproject.Validator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    EditText fName, fAge, fPhoneNumber;
    RadioGroup radioGroup;
    RadioButton male, female;
    Button cancel, submit;
    FirebaseAuth fAuth;
    FirebaseUser fUser;
    FirebaseFirestore fStore;
    DocumentReference documentReference;

    String userId, sName, sAge, sPhone, sGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);

        fName=(EditText) findViewById(R.id.profile_name);
        fAge=(EditText) findViewById(R.id.age);
        fPhoneNumber=(EditText) findViewById(R.id.phone_number);
        fName=(EditText) findViewById(R.id.profile_name);
        radioGroup = (RadioGroup) findViewById(R.id.gender);
        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);
        cancel = (Button) findViewById(R.id.cancel);
        submit = (Button) findViewById(R.id.submit);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        fUser = fAuth.getCurrentUser();

        userId = fAuth.getCurrentUser().getUid();
        documentReference = fStore.collection("Users").document(userId);

        cancel.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    public void resetName() {
        sName = fName.getText().toString();
        if (!sName.equals("")) {
            if (sName != fUser.getDisplayName()) {
                if (Validator.isValidUsername(sName)) {
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(sName).build();
                    fUser.updateProfile(profileUpdates);
                    documentReference.update("name", sName);
                } else {
                    fName.setError("Username No less than 4 character");
                }
            } else {
                fName.setError("Username Cannot be Same");
            }
        } else {
            sName = fUser.getDisplayName();
        }
    }

    public void resetAge() {
        sAge = fAge.getText().toString();
        if (!sAge.equals("")) {
            documentReference.update("age", sAge);
        }
    }

    public void resetGender() {
        if (male.isChecked()) {
            sGender = "Male";
        } else if (female.isChecked()) {
            sGender = "Female";
        }
        documentReference.update("gender", sGender);

    }

    public void resetPhone() {
        sPhone = fPhoneNumber.getText().toString();
        if (!sPhone.equals("")) {
            if (Validator.isValidContactNumber(sPhone)) {
                documentReference.update("phoneNo", sPhone);
            }
        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.submit) {
            resetName();
            resetAge();
            resetPhone();
            resetGender();

            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
        } else if(id == R.id.cancel){
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
        }
    }
}
