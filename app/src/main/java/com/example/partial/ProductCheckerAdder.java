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

public class ProductCheckerAdder extends AppCompatActivity {
    ImageButton checkerToGroceryDashboard;
    FloatingActionButton addProductAvailable;
    RecyclerView recyclerView;
    ProductCheckerAdapter productCheckerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_checker_adder);
        checkerToGroceryDashboard = findViewById(R.id.checkerToGroceryDashboard);
        addProductAvailable = findViewById(R.id.btnProductCheckerAdd);
        recyclerView = findViewById(R.id.checkerRecyclerView);

        checkerToGroceryDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProductCheckerAdder.this, GroceryDashboard.class);
                startActivity(i);
                finish();
            }
        });

        addProductAvailable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProductCheckerAdder.this, AddFeatureChecker.class);
                startActivity(i);
                finish();
            }
        });

        setupRecyclerView();
    }

    void setupRecyclerView() {
        Query query = ProductCheckerHelper.getCollectionReferenceForChecker().orderBy("timestamp", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<ProductChecker> options = new FirestoreRecyclerOptions.Builder<ProductChecker>().setQuery(query, ProductChecker.class).build();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productCheckerAdapter = new ProductCheckerAdapter(options, this);
        recyclerView.setAdapter(productCheckerAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        productCheckerAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        productCheckerAdapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        productCheckerAdapter.notifyDataSetChanged();
    }
}