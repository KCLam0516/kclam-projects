package com.example.finalyearproject.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.finalyearproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class PasswordActivity extends AppCompatActivity implements View.OnClickListener {
    EditText oldPassword, newPassword, confirmPassword;
    Button cancel, submit;
    FirebaseAuth fAuth;
    FirebaseUser fUser;
    FirebaseFirestore fStore;
    DocumentReference documentReference;

    String userId, sOldPassword, sNewPassword, sConfirmPassword, sEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        oldPassword=(EditText) findViewById(R.id.old_password);
        newPassword=(EditText) findViewById(R.id.new_password);
        confirmPassword=(EditText) findViewById(R.id.cofirm_password);
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

    @Override
    public void onClick(View v) {
        sEmail = fUser.getEmail();
        sOldPassword = oldPassword.getText().toString();
        sNewPassword = newPassword.getText().toString();
        sConfirmPassword = confirmPassword.getText().toString();
        int id = v.getId();
        if (id == R.id.submit) {
            if(sOldPassword.equals(sNewPassword)){
            newPassword.setError("New password cannot be the same as old password");
                return;
            } else if(!sNewPassword.equals(sConfirmPassword)){
                newPassword.setError("New password cannot be the same as old password");
                return;
            }
                resetPassword();
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
        } else if (id == R.id.cancel) {
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
        }
    }

    public void resetPassword() {
        sEmail = fUser.getEmail();
        sOldPassword = oldPassword.getText().toString();
        sNewPassword = newPassword.getText().toString();
        sConfirmPassword = confirmPassword.getText().toString();
            AuthCredential credential = EmailAuthProvider.getCredential(sEmail, sOldPassword);

            // Prompt the user to re-provide their sign-in credentials
            fUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        documentReference.update("password", sNewPassword);
                        fUser.updatePassword(sNewPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.e("Success", "Password update successfully");
                                } else {
                                    Log.e("Fail", "Error, password will not update");
                                }
                            }
                        });
                    } else {
                        Log.e("Fail", "Error auth failed");
                    }
                }
            });
    }
}