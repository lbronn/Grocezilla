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

public class PurchaseHistoryAdder extends AppCompatActivity {
    ImageButton toHome;
    FloatingActionButton addPurchase;
    RecyclerView recyclerView;
    PurchaseHistoryAdapter purchaseHistoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchasehistory);
        toHome = findViewById(R.id.purchaseToHome);
        addPurchase = findViewById(R.id.btnAddPurchase);
        recyclerView = findViewById(R.id.purchaserecyler_view);

        toHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PurchaseHistoryAdder.this, home.class);
                startActivity(i);
                finish();
            }
        });

        addPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PurchaseHistoryAdder.this, AddPurchases.class);
                startActivity(i);
                finish();
            }
        });

        setupRecyclerView();
    }

    public void setupRecyclerView() {
        Query query = PurchaseHistoryHelper.getCollectionReferenceForPurchase().orderBy("purchaseTitle", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<PurchaseHistory> options = new FirestoreRecyclerOptions.Builder<PurchaseHistory>().setQuery(query, PurchaseHistory.class).build();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        purchaseHistoryAdapter = new PurchaseHistoryAdapter(options, this);
        recyclerView.setAdapter(purchaseHistoryAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        purchaseHistoryAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        purchaseHistoryAdapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        purchaseHistoryAdapter.notifyDataSetChanged();
    }
}