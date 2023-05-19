package com.example.partial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class home extends AppCompatActivity {
    ImageView iHome;
    ImageView iGrocery;
    ImageView iProfile;
    TextView tHome;
    TextView tGrocery;
    TextView tProfile;
    Button task;
    Button journal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        task = findViewById(R.id.btnJot);
        journal = findViewById(R.id.btnJournal);
        iHome = findViewById(R.id.homeic);
        tHome = findViewById(R.id.hometxt);

        iHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(home.this, home.class);
                startActivity(i);
                finish();
            }
        });

        tHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(home.this, home.class);
                startActivity(i);
                finish();
            }
        });

        task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(home.this, TaskAdder.class);
                startActivity(i);
                finish();
            }
        });

        journal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(home.this, featurejounal.class);
                startActivity(i);
                finish();
            }
        });
    }
}