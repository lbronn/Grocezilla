package com.example.partial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.Query;

public class JournalAdder extends AppCompatActivity {

    FloatingActionButton journalAdd;
    ImageButton journalToHome;
    RecyclerView recyclerView;
    JournalAdapter journalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);
        journalAdd = findViewById(R.id.btnAddJournal);
        journalToHome = findViewById(R.id.jourToHome);
        recyclerView = findViewById(R.id.journalrecyler_view);

        journalAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(JournalAdder.this, AddFeatureJournal.class);
                startActivity(i);
                finish();
            }
        });

        journalToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(JournalAdder.this, home.class);
                startActivity(i);
                finish();
            }
        });

        setupRecyclerView();
    }

    void setupRecyclerView() {
        Query query = JournalHelper.getCollectionReferenceForJournal().orderBy("timestamp", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Journal> options = new FirestoreRecyclerOptions.Builder<Journal>().setQuery(query, Journal.class).build();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        journalAdapter = new JournalAdapter(options, this);
        recyclerView.setAdapter(journalAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        journalAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        journalAdapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        journalAdapter.notifyDataSetChanged();
    }
}