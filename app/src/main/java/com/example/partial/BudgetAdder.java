package com.example.partial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

public class BudgetAdder extends AppCompatActivity {
    ImageButton budgetToGroceryList;
    RecyclerView recyclerView;
    BudgetAdapter budgetAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_adder);
        budgetToGroceryList = findViewById(R.id.budgetToGroceryAdder);
        recyclerView = findViewById(R.id.budgetrecycler_view);

        budgetToGroceryList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BudgetAdder.this, GroceryAdder.class);
                startActivity(i);
                finish();
            }
        });

        setupRecyclerView();
    }

    public void setupRecyclerView() {
        Query query = BudgetHelper.getCollectionReferenceForBudget().orderBy("budgetTitle", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Budget> options = new FirestoreRecyclerOptions.Builder<Budget>().setQuery(query, Budget.class).build();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        budgetAdapter = new BudgetAdapter(options, this);
        recyclerView.setAdapter(budgetAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        budgetAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        budgetAdapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        budgetAdapter.notifyDataSetChanged();
    }
}