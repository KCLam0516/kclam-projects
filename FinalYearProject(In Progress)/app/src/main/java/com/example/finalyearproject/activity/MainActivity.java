package com.example.finalyearproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalyearproject.R;
import com.example.finalyearproject.data.Car;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "BookCarActivity";
    private FirebaseFirestore firebaseFirestore;
    private RecyclerView mCarList;
    private TextView bProfile,bCart;

    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstancedState) {
        super.onCreate(savedInstancedState);
        setContentView(R.layout.activity_main);

        firebaseFirestore= FirebaseFirestore.getInstance();
        mCarList = findViewById(R.id.recycleView);
        bProfile=(TextView) findViewById(R.id.bProfile);
        bCart=(TextView) findViewById(R.id.bCart);


        bProfile.setOnClickListener(this);
        bCart.setOnClickListener(this);

        //Query
        Query query = firebaseFirestore.collection("CarStorage")
                .whereEqualTo("carStatus","unBook");

        //RecyclerOptions
        FirestoreRecyclerOptions<Car> options = new FirestoreRecyclerOptions.Builder<Car>()
                .setQuery(query,Car.class)
                .build();
         adapter = new FirestoreRecyclerAdapter<Car, CarsViewHolder>(options) {
            @NonNull
            @Override
            public CarsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_design,parent,false);
                return new CarsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final CarsViewHolder holder, int i, @NonNull Car model) {
                holder.carName.setText(model.getCarName());
                holder.ownerName.setText(model.getOwnerName());
                holder.price.setText(model.getPrice());

                holder.buttonAdd.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        String documentId = getSnapshots().getSnapshot(holder.getAdapterPosition()).getId();
                        DocumentReference selectRef = firebaseFirestore.collection("CarStorage").document(documentId);
                        selectRef.update("carStatus","Book")
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "Car book successfully!");
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
        if (id == R.id.bCart) {
            Intent intent = new Intent(getApplicationContext(), CartActivity.class);
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
        private TextView buttonAdd;

        public CarsViewHolder(@NonNull View itemView) {
            super(itemView);

//            carImage=itemView.findViewById(R.id.carImage);
            carName=itemView.findViewById(R.id.carName);
            ownerName=itemView.findViewById(R.id.ownerName);
            price=itemView.findViewById(R.id.price);
            buttonAdd=itemView.findViewById(R.id.buttonAdd);
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
