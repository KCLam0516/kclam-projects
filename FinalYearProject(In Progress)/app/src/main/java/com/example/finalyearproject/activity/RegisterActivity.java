package com.example.finalyearproject.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalyearproject.R;
import com.example.finalyearproject.Validator;
import com.example.finalyearproject.data.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "TAG";
    EditText mUserRegName, mEmail, mPassword,mConfirmPassword;
    Button mRegisterBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    ProgressBar progressBar;
    TextView bMember;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mUserRegName = findViewById(R.id.userRegName);
        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.Password);
        mConfirmPassword = findViewById(R.id.ConfirmPassword);
        mRegisterBtn = findViewById(R.id.register);
        bMember=findViewById(R.id.member);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);

        mRegisterBtn.setOnClickListener(this);
        bMember.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.register) {
            final String userRegName = mUserRegName.getText().toString();
            final String email = mEmail.getText().toString().trim();
            final String password = mPassword.getText().toString().trim();
            final String confirmPassword = mConfirmPassword.getText().toString().trim();

            if (TextUtils.isEmpty(userRegName)) {
                mUserRegName.setError("Username is empty.");
                return;
            }

            if (userRegName.length() > 30 ||userRegName.length()<4) {
                mUserRegName.setError("Username No less than 4 character");
                return;
            }

            if (!Validator.isValidUsername(userRegName)) {
                mUserRegName.setError("Alphabet only in username");
                return;
            }

            if (!Validator.isValidEmail(email)) {
                mEmail.setError("Please enter a correct email format.");
                return;
            }

            if (TextUtils.isEmpty(password)) {
                mPassword.setError("Password is empty.");
                return;
            }

            if (!password.equals(confirmPassword)) {
                mConfirmPassword.setError("Both password is not same");
                return;
            }

            if (password.length() < 6) {
                mPassword.setError("Password need to be more than 6 character");
                return;
            }

            progressBar.setVisibility(View.VISIBLE);

            //register the user in firebase
            fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        final FirebaseUser fUser = fAuth.getCurrentUser();
                        fUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    userId=fAuth.getCurrentUser().getUid();
                                    DocumentReference documentReference = fStore.collection("Users").document(userId);
                                    User user = new User(userRegName,email,password,"","","Male");

                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.e("success",userId+ "profile created successfully");
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.e("failure", e.toString());
                                        }
                                    });

                                    //send verification link
                                    fUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.e("success","Verification link sent to the email");

                                            Toast.makeText(RegisterActivity.this, "Please click in verification link in your email.", Toast.LENGTH_LONG).show();
                                            finish();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.e("Error","Email send unsuccessfully"+e.getMessage());
                                        }
                                    });

                                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(userRegName).build();
                                    fUser.updateProfile(profileUpdates);

                                    mUserRegName.setText("");
                                    mEmail.setText("");
                                    mPassword.setText("");
                                } else {
                                    Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                                progressBar.setVisibility(View.GONE);
                            }
                        });
                    } else {
                        Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });
        } else if (id == R.id.member) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
    }
}