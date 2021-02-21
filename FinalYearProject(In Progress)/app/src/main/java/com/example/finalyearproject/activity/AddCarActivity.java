package com.example.finalyearproject.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.autofill.AutofillId;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalyearproject.R;
import com.example.finalyearproject.Validator;
import com.example.finalyearproject.data.Car;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddCarActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "AddCarActivity";
    TextView bProfile,bMyCar;
    EditText mCarName, mPrice;
    Button add;
    FirebaseAuth fAuth;
    FirebaseUser fUser;
    FirebaseFirestore fStore;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        bProfile =(TextView) findViewById(R.id.bProfile);
        bMyCar =(TextView) findViewById(R.id.bMyCar);
        mCarName=findViewById(R.id.carName);
        mPrice=findViewById(R.id.price);
        add=findViewById(R.id.addNewCar);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        fUser = fAuth.getCurrentUser();
        progressBar = findViewById(R.id.progressBar);

        bProfile.setOnClickListener(this);
        bMyCar.setOnClickListener(this);
        add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.addNewCar) {
            final String carName = mCarName.getText().toString();
            final String ownerName = fUser.getDisplayName();
            final String price = mPrice.getText().toString();
            final String carStatus = "unBook";

            if (TextUtils.isEmpty(carName)) {
                mCarName.setError("Car name is empty.");
                return;
            }
            numCheck(mPrice);
            progressBar.setVisibility(View.VISIBLE);

            Car car = new Car(carName, ownerName, price, carStatus);

            fStore.collection("CarStorage").add(car)
                    .addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            Toast.makeText(AddCarActivity.this, "Car saved", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddCarActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, e.toString());
                        }
                    });
            clear();
            progressBar.setVisibility(View.GONE);
        } else if (id == R.id.bProfile) {
            Intent intent = new Intent(getApplicationContext(), HostProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.bMyCar) {
            Intent intent = new Intent(getApplicationContext(), HostMainActivity.class);
            startActivity(intent);
        }
    }

    private boolean numCheck(EditText field) {
        if (field.getText().toString().isEmpty()) {
            field.setError("Please fill in the blank");
            return false;
        }
        try {
            Float.parseFloat(field.getText().toString());
            field.setError(null);
            return true;
        } catch (NumberFormatException ex) {
            field.setError("No decimal point allow");
            return false;
        }
    }

    public void clear() {
        mCarName.setText("");
        mPrice.setText("");
    }

}