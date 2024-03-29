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
    ImageView iChat;
    TextView tHome;
    TextView tGrocery;
    TextView tProfile;
    TextView tChat;
    Button task;
    Button journal;
    Button purchased;
    Button grocery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        task = findViewById(R.id.btnJot);
        journal = findViewById(R.id.btnJournal);
        purchased = findViewById(R.id.btnPurchaseHistory);
        grocery = findViewById(R.id.btnGroceryList);
        iHome = findViewById(R.id.homeic);
        tHome = findViewById(R.id.hometxt);
        iProfile = findViewById(R.id.useric);
        tProfile = findViewById(R.id.usertxt);
        iChat = findViewById(R.id.chatic);
        tChat = findViewById(R.id.chattxt);
        iGrocery = findViewById(R.id.groceryic);
        tGrocery = findViewById(R.id.grocerytxt);

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

        iChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(home.this, MessageGPT.class);
                startActivity(i);
                finish();
            }
        });

        tChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(home.this, MessageGPT.class);
                startActivity(i);
                finish();
            }
        });

        journal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(home.this, JournalAdder.class);
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

        purchased.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(home.this, PurchaseHistoryAdder.class);
                startActivity(i);
                finish();
            }
        });

        grocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(home.this, GroceryAdder.class);
                startActivity(i);
                finish();
            }
        });

        iGrocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(home.this, GroceryDashboard.class);
                startActivity(i);
                finish();
            }
        });

        tGrocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(home.this, GroceryDashboard.class);
                startActivity(i);
                finish();
            }
        });

        iProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(home.this, Profile.class);
                startActivity(i);
                finish();
            }
        });

        tProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(home.this, Profile.class);
                startActivity(i);
                finish();
            }
        });
    }
}