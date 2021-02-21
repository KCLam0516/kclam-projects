package com.example.finalyearproject.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalyearproject.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class HostProfileActivity extends AppCompatActivity implements View.OnClickListener {

    TextView bDetails, bPassword, bFeedback, bService, bLogout, name, bMyCar, bAddCar, bMember;
    Button bPen;
    FrameLayout image;
    ImageView profile;
    FirebaseAuth fAuth;
    FirebaseUser fUser;
    FirebaseFirestore fStore;
    StorageReference storageReference;

    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_profile);

        Intent intent = getIntent();

        bMyCar =(TextView) findViewById(R.id.bMyCar);
        bAddCar =(TextView) findViewById(R.id.bAddCar);
        bDetails = (TextView) findViewById(R.id.pDetails);
        bPassword = (TextView) findViewById(R.id.cPassword);
        bFeedback = (TextView) findViewById(R.id.feedback);
        bService = (TextView) findViewById(R.id.cService);
        bMember=(TextView) findViewById(R.id.sMember);
        bLogout = (TextView) findViewById(R.id.logout);
        profile=(ImageView)findViewById(R.id.profile_name);
        name=(TextView)findViewById(R.id.name);

        bMyCar.setOnClickListener(this);
        bAddCar.setOnClickListener(this);
        bDetails.setOnClickListener(this);
        bPassword.setOnClickListener(this);
        bFeedback.setOnClickListener(this);
        bService.setOnClickListener(this);
        bMember.setOnClickListener(this);
        bLogout.setOnClickListener(this);
        profile.setOnClickListener(this);
        name.setOnClickListener(this);

        fAuth = FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        fUser = fAuth.getCurrentUser();
        storageReference= FirebaseStorage.getInstance().getReference();

        name.setText(fUser.getDisplayName());

        final StorageReference profileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");

        if (profileRef==null)
        {
            profile.setImageResource(R.drawable.ic_action_user);
        }else {
            profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).into(profile);
                }
            });
        }

        String userId=fAuth.getCurrentUser().getUid();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.pDetails) {
            Intent intent = new Intent(getApplicationContext(), EditDetailsActivity.class);
            startActivity(intent);
        } else if (id == R.id.cPassword) {
            Intent intent = new Intent(getApplicationContext(), PasswordActivity.class);
            startActivity(intent);
        } else if (id == R.id.feedback) {
            Intent intent = new Intent(getApplicationContext(), FeedbackActivity.class);
            startActivity(intent);
        } else if (id == R.id.cService) {
            Intent intent = new Intent(getApplicationContext(), CustomerServiceActivity.class);
            startActivity(intent);
        } else if (id == R.id.sMember) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }else if (id==R.id.logout){
            fAuth.signOut();
            Toast.makeText(this, "Logout successfully.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }else if (id==R.id.profile_name || id==R.id.pen){

            //dialog choose photo
            final CharSequence[] options = {"View Photo","Change Photo", "Delete Photo", "Cancel"};
            AlertDialog.Builder builder = new AlertDialog.Builder(HostProfileActivity.this,R.style.AlertDialogTheme);
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (options[item].equals("View Photo")) {       //view photo in bigger size
                        startActivity(new Intent(getApplicationContext(), ProfilePictureActivity.class));

                    } if (options[item].equals("Change Photo")) {        //chose photo from gallery
                        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(openGalleryIntent,1000);

                    } else if (options[item].equals("Delete Photo")) {

                        final AlertDialog alertDialogDelete = new AlertDialog.Builder(HostProfileActivity.this,R.style.AlertDialogTheme).create();
                        alertDialogDelete.setTitle("Delete Photo");
                        alertDialogDelete.setMessage("Delete photo confirmation");

                        alertDialogDelete.setButton(AlertDialog.BUTTON_POSITIVE, "cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                        //delete photo
                        alertDialogDelete.setButton(AlertDialog.BUTTON_NEGATIVE, "delete",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        final StorageReference fileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
                                        fileRef.delete().addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(HostProfileActivity.this, "Your photo has been deleted", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        dialog.dismiss();

                                        profile.setImageResource(R.drawable.ic_action_user);
                                    }
                                });
                        alertDialogDelete.show();

                        Button btnPositive = alertDialogDelete.getButton(AlertDialog.BUTTON_POSITIVE);
                        Button btnNegative = alertDialogDelete.getButton(AlertDialog.BUTTON_NEGATIVE);
                        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) btnPositive.getLayoutParams();
                        layoutParams.weight = 10;
                        btnPositive.setLayoutParams(layoutParams);
                        btnNegative.setLayoutParams(layoutParams);
                        btnPositive.setTextColor(getResources().getColor(R.color.green));
                        btnPositive.setTextSize(18);
                        btnNegative.setTextColor(getResources().getColor(R.color.red));
                        btnNegative.setTextSize(18);

                    }else if (options[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            AlertDialog alertDialog=builder.create();
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            //set dialog at bottom
            Window window = alertDialog.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.BOTTOM;
            wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(wlp);

            alertDialog.show();

        }
        else if (id==R.id.bHome) {
            Intent intent = new Intent(getApplicationContext(), HostMainActivity.class);
            startActivity(intent);
        }else if (id==R.id.bAddCar){
            Intent intent = new Intent(getApplicationContext(), AddCarActivity.class);
            startActivity(intent);
        } else if (id == R.id.bMyCar) {
            Intent intent = new Intent(getApplicationContext(), HostMainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==1000){
            if (resultCode== Activity.RESULT_OK){
                imageUri = data.getData();
                profile.setImageURI(imageUri);
                Log.e("Success", "upload");
                uploadImageToFirebase(imageUri);
            }
        }
    }

    private void uploadImageToFirebase(Uri imageUri) {
        // uplaod image to firebase storage
        Log.e("Success", "in firebase");
        final StorageReference fileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.e("Success","image upload successfully");
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profile);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Failure","image upload unsuccessfully");
                Toast.makeText(getApplicationContext(), "Failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}