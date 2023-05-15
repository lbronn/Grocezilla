package com.example.partial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class home extends AppCompatActivity {
    ImageView iChat;
    ImageView iSocial;
    ImageView iHome;
    ImageView iGrocery;
    ImageView iProfile;
    TextView tChat;
    TextView tSocial;
    TextView tHome;
    TextView tGrocery;
    TextView tProfile;
    Button task;
    Button journal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        iChat = findViewById(R.id.chatic);
        tChat = findViewById(R.id.chattxt);
        task = findViewById(R.id.btnJot);
        journal = findViewById(R.id.btnJournal);
        iHome = findViewById(R.id.homeic);
        tHome = findViewById(R.id.hometxt);

        iChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), message.class);
                startActivity(i);
                finish();
            }
        });

        tChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(home.this, message.class);
                startActivity(i);
                finish();
            }
        });

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