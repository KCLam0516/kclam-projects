package com.example.assignment;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

public class CovidInfo extends AppCompatActivity implements View.OnClickListener {
    Button second, third, forth, fifth;
    Toolbar toolbar;
    ImageView backBtn; //back button
    RelativeLayout expandableView,expandable2,expandable3;
    Button expand,expand2,expand3;
    RelativeLayout box1,box2,box3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.covid_info);

        Intent intent = getIntent();

        second = (Button) findViewById(R.id.second);
        third = (Button) findViewById(R.id.third);
        forth = (Button) findViewById(R.id.forth);
        fifth = (Button) findViewById(R.id.fifth);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        expandableView=findViewById(R.id.expandableView);
        expandable2=findViewById(R.id.expandableView2);
        expandable3=findViewById(R.id.expandableView3);
        expand = findViewById(R.id.expand_btn);
        expand2 = findViewById(R.id.expand_btn2);
        expand3 = findViewById(R.id.expandBtn3);
        box1=findViewById(R.id.oriBox1);
        box2=findViewById(R.id.oriBox2);
        box3=findViewById(R.id.Box3);

        second.setOnClickListener(this);
        third.setOnClickListener(this);
        forth.setOnClickListener(this);
        fifth.setOnClickListener(this);
        expand.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                if(expandableView.getVisibility()==View.GONE){
                    TransitionManager.beginDelayedTransition(box1, new AutoTransition());
                    expandableView.setVisibility(View.VISIBLE);
                }else{
                    TransitionManager.beginDelayedTransition(box1, new AutoTransition());
                    expandableView.setVisibility(View.GONE);
                }
            }
        });
        expand2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                if(expandable2.getVisibility()==View.GONE){
                    TransitionManager.beginDelayedTransition(box2, new AutoTransition());
                    expandable2.setVisibility(View.VISIBLE);
                }else{
                    TransitionManager.beginDelayedTransition(box2, new AutoTransition());
                    expandable2.setVisibility(View.GONE);
                }
            }
        });
        expand3.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                if(expandable3.getVisibility()==View.GONE){
                    TransitionManager.beginDelayedTransition(box3, new AutoTransition());
                    expandable3.setVisibility(View.VISIBLE);
                }else{
                    TransitionManager.beginDelayedTransition(box3, new AutoTransition());
                    expandable3.setVisibility(View.GONE);
                }
            }
        });
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                startActivity(intent);
                Toast.makeText(this, "Log Out Successfully", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        if (id == R.id.second) {
            Intent intent = new Intent(getApplicationContext(), CovidInfo.class);
            startActivity(intent);
        } else if (id == R.id.third) {
            Intent intent = new Intent(getApplicationContext(), CovidChecking.class);
            startActivity(intent);
        } else if (id == R.id.forth) {
            Intent intent = new Intent(getApplicationContext(), Notification.class);
            startActivity(intent);
        } else if (id == R.id.fifth) {
            Intent intent = new Intent(getApplicationContext(), LocationRecorder.class);
            startActivity(intent);
        }
    }
}