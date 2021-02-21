package com.example.finalyearproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalyearproject.R;
import com.example.finalyearproject.data.Car;
import com.example.finalyearproject.data.Payment;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Date;

public class CartActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "PayCarActivity";

    private FirebaseFirestore firebaseFirestore;
    private RecyclerView mCarList;
    private TextView bProfile,bHome;
    FirebaseAuth fAuth;
    FirebaseUser fUser;

    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstancedState) {
        super.onCreate(savedInstancedState);
        setContentView(R.layout.activity_cart);

        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
        firebaseFirestore= FirebaseFirestore.getInstance();
        mCarList = findViewById(R.id.recycleView);
        bProfile=(TextView) findViewById(R.id.bProfile);
        bHome=(TextView) findViewById(R.id.bHome);


        bProfile.setOnClickListener(this);
        bHome.setOnClickListener(this);

        //Query
        Query query = firebaseFirestore.collection("CarStorage")
                .whereEqualTo("carStatus","Book");

        //RecyclerOptions
        FirestoreRecyclerOptions<Car> options = new FirestoreRecyclerOptions.Builder<Car>()
                .setQuery(query,Car.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<Car, CarsViewHolder>(options) {
            @NonNull
            @Override
            public CarsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_design,parent,false);
                return new CarsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final CarsViewHolder holder, int i, @NonNull Car model) {
                holder.carName.setText(model.getCarName());
                holder.ownerName.setText(model.getOwnerName());
                holder.price.setText(model.getPrice());


                holder.buttonPay.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        String documentId = getSnapshots().getSnapshot(holder.getAdapterPosition()).getId();
                        DocumentReference selectRef = firebaseFirestore.collection("CarStorage").document(documentId);
                        final String payName = fUser.getDisplayName();
                        final String payDate = new Date().toString();

                        selectRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        Log.d(TAG, "Document data get: " + document.getData());
                                        String ownerName = document.getString("ownerName");
                                        String amount = document.getString("price");
                                        Payment payment = new Payment(payName,payDate,ownerName,amount);

                                        firebaseFirestore.collection("PaymentRecord").add(payment)
                                                .addOnSuccessListener(new OnSuccessListener() {
                                                    @Override
                                                    public void onSuccess(Object o) {
                                                        Toast.makeText(CartActivity.this, "Car paid", Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(CartActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                                                        Log.d(TAG, e.toString());
                                                    }
                                                });
                                    } else {
                                        Log.d(TAG, "No such document");
                                    }
                                } else {
                                    Log.d(TAG, "get failed with ", task.getException());
                                }
                            }
                        });

                        selectRef.update("carStatus","unBook")
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "Car paid successfully!");
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error booking car", e);
                            }
                        });
                    }
                });

            }
        };

        mCarList.setHasFixedSize(true);
        mCarList.setLayoutManager(new LinearLayoutManager(this));
        mCarList.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bHome) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.bProfile) {
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
        }
    }

    private class CarsViewHolder extends RecyclerView.ViewHolder {

        //        private ImageView carImage;
        private TextView carName;
        private TextView ownerName;
        private TextView price;
        private TextView buttonPay;

        public CarsViewHolder(@NonNull View itemView) {
            super(itemView);

//            carImage=itemView.findViewById(R.id.carImage);
            carName=itemView.findViewById(R.id.carName);
            ownerName=itemView.findViewById(R.id.ownerName);
            price=itemView.findViewById(R.id.price);
            buttonPay=itemView.findViewById(R.id.buttonPay);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}