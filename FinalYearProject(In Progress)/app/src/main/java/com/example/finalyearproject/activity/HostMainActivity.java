package com.example.finalyearproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalyearproject.R;
import com.example.finalyearproject.data.Car;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class HostMainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseFirestore firebaseFirestore;
    private RecyclerView mCarList;
    private TextView bProfile,bAddCar;
    private FirestoreRecyclerAdapter adapter;
    FirebaseAuth fAuth;
    FirebaseUser fUser;

    @Override
    protected void onCreate(Bundle savedInstancedState) {
        super.onCreate(savedInstancedState);
        setContentView(R.layout.activity_host_main);

        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
        firebaseFirestore= FirebaseFirestore.getInstance();
        mCarList = findViewById(R.id.recycleView);
        bProfile=(TextView) findViewById(R.id.bProfile);
        bAddCar=(TextView) findViewById(R.id.bAddCar);


        bProfile.setOnClickListener(this);
        bAddCar.setOnClickListener(this);

        //Query
        Query query = firebaseFirestore.collection("CarStorage")
                .whereEqualTo("ownerName", fUser.getDisplayName());

        //RecyclerOptions
        FirestoreRecyclerOptions<Car> options = new FirestoreRecyclerOptions.Builder<Car>()
                .setQuery(query,Car.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<Car, CarsViewHolder>(options) {
            @NonNull
            @Override
            public CarsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_main_design,parent,false);
                return new CarsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull CarsViewHolder holder, int i, @NonNull Car model) {
                holder.carName.setText(model.getCarName());
                holder.ownerName.setText(model.getOwnerName());
                holder.price.setText(model.getPrice());

            }
        };

        mCarList.setHasFixedSize(true);
        mCarList.setLayoutManager(new LinearLayoutManager(this));
        mCarList.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id==R.id.bAddCar){
            Intent intent = new Intent(getApplicationContext(), AddCarActivity.class);
            startActivity(intent);
        } else if (id == R.id.bProfile) {
            Intent intent = new Intent(getApplicationContext(), HostProfileActivity.class);
            startActivity(intent);
        }
    }

    private class CarsViewHolder extends RecyclerView.ViewHolder {

        //        private ImageView carImage;
        private TextView carName;
        private TextView ownerName;
        private TextView price;

        public CarsViewHolder(@NonNull View itemView) {
            super(itemView);

//            carImage=itemView.findViewById(R.id.carImage);
            carName=itemView.findViewById(R.id.carName);
            ownerName=itemView.findViewById(R.id.ownerName);
            price=itemView.findViewById(R.id.price);
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
