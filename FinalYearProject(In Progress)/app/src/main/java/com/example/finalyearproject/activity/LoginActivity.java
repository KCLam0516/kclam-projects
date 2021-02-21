package com.example.finalyearproject.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalyearproject.R;
import com.example.finalyearproject.Validator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String FILE_NAME = "myFile";
    EditText mEmail, mPassword;
    Button mLoginBtn;
    TextView mSignUpBtn, forgotTextLink;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    FirebaseUser fUser;
    CheckBox remember;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = (EditText) findViewById(R.id.loginEmail);
        mPassword = (EditText) findViewById(R.id.loginPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mLoginBtn = (Button) findViewById(R.id.loginBtn);
        mSignUpBtn = (TextView) findViewById(R.id.signUp);
        forgotTextLink = (TextView) findViewById(R.id.forgotPassword);
        remember = findViewById(R.id.rememberMe);

        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();

        sharedPreferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");
        final String password = sharedPreferences.getString("password", "");

        mEmail.setText(email);
        mPassword.setText(password);

        if (fUser != null && fUser.isEmailVerified()) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//            startActivity(new Intent(getApplicationContext(), Testing.class));
            finish();
        }

        mLoginBtn.setOnClickListener(this);
        mSignUpBtn.setOnClickListener(this);
        forgotTextLink.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();


        if (isNetworkConnected(LoginActivity.this)) {
            if (id == R.id.loginBtn) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if (!Validator.isValidEmail(email)) {
                    mEmail.setError("Correct email format is required.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is Required.");
                    return;
                }

                if (password.length() < 6) {
                    mPassword.setError("Password Must be more than 6 Characters");
                    return;
                }

                if (remember.isChecked()) {
                    StoredDataUsingSharedPref(email, password);
                } else if (!remember.isChecked()) {
                    sharedPreferences.edit().clear().apply();
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate the user
                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            if (fAuth.getCurrentUser().isEmailVerified()) {
                                FirebaseUser user = fAuth.getCurrentUser();
                                if (user != null) {
                                    Toast.makeText(LoginActivity.this, "Hello, " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
                                }
                                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                            } else {
                                Toast.makeText(LoginActivity.this, "Please verify your email address to proceed", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        } else {
                            Toast.makeText(LoginActivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

            } else if (id == R.id.signUp) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            } else if (id == R.id.forgotPassword) {
                //Display edit text on dialog
                LinearLayout layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(70, 0, 70, 30);

                final EditText resetMail = new EditText(v.getContext());
                resetMail.setTextColor(getResources().getColor(R.color.lightGrey));
                resetMail.setTextSize(17);

                layout.addView(resetMail, params);

                final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext(), R.style.AlertDialogTheme);
                passwordResetDialog.setTitle("Reset Password?");
                passwordResetDialog.setMessage("Email needed for password reset");
                passwordResetDialog.setView(layout);

                //Display dialog allow user to enter their email address
                passwordResetDialog.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        if (resetMail.getText().toString().isEmpty()) {
                            Toast.makeText(LoginActivity.this, "Please Enter your email", Toast.LENGTH_SHORT).show();
                        } else {
                            //extract the email and send reset link
                            String mail = resetMail.getText().toString();
                            fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(LoginActivity.this, "Please open the reset link in your mail.", Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(LoginActivity.this, "Reset Link sent unsuccessfully." + e.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    }
                });


                passwordResetDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        //close the dialog
                    }
                });

                final AlertDialog dialog = passwordResetDialog.create();
                dialog.show();

                Button btnPositive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                Button btnNegative = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);

                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) btnPositive.getLayoutParams();
                layoutParams.weight = 15;
                btnPositive.setLayoutParams(layoutParams);
                btnNegative.setLayoutParams(layoutParams);

                btnPositive.setTextColor(getResources().getColor(R.color.green));
                btnPositive.setTextSize(18);
                btnNegative.setTextColor(getResources().getColor(R.color.red));
                btnNegative.setTextSize(18);


            }
        } else {
            final AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this, R.style.AlertDialogTheme).create();
            alertDialog.setTitle("Network Disconnect");
            alertDialog.setMessage("Network Connection is needed.");
            alertDialog.setCancelable(false);

            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Connect",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                        }
                    });

            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();

            Button btnPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            Button btnNegative = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);

            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) btnPositive.getLayoutParams();
            layoutParams.weight = 10;
            btnPositive.setLayoutParams(layoutParams);
            btnNegative.setLayoutParams(layoutParams);

            btnPositive.setTextColor(getResources().getColor(R.color.green));
            btnPositive.setTextSize(18);
            btnNegative.setTextColor(getResources().getColor(R.color.red));
            btnNegative.setTextSize(18);
        }
    }

    //Shared Pref
    private void StoredDataUsingSharedPref(String email, String password) {

        SharedPreferences.Editor editor = getSharedPreferences(FILE_NAME, MODE_PRIVATE).edit();
        editor.putString("email", email);
        editor.putString("password", password);

        editor.apply();
    }

    //check internet connection
    private boolean isNetworkConnected(LoginActivity login) {
        ConnectivityManager connectivityManager = (ConnectivityManager) login.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifiInfo != null && wifiInfo.isConnected() || (mobileInfo != null && mobileInfo.isConnected()))) {
            return true;
        } else
            return false;
    }
}