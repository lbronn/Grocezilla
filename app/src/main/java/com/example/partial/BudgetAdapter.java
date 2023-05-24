package com.example.partial;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class BudgetAdapter extends FirestoreRecyclerAdapter<Budget, BudgetAdapter.BudgetViewHolder> {
    Context context;

    public BudgetAdapter(@NonNull FirestoreRecyclerOptions<Budget> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    @SuppressLint("RecyclerView")
    protected void onBindViewHolder(@NonNull BudgetAdapter.BudgetViewHolder holder, int position, @NonNull Budget budget) {
        holder.budgetTitleRecycle.setText(budget.budgetTitle);
        holder.budgetDescRecycle.setText(budget.budgetDescription);
        String totalBudgetWithPeso = "₱" + budget.budgetAmount;
        holder.budgetAmountRecycle.setText(totalBudgetWithPeso);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, AddBudget.class);
                i.putExtra("budgetTitle", budget.budgetTitle);
                i.putExtra("budgetDescription", budget.budgetDescription);
                i.putExtra("budgetAmount", budget.budgetAmount);
                String docId = BudgetAdapter.this.getSnapshots().getSnapshot(position).getId();
                i.putExtra("docId", docId);
                context.startActivity(i);
            }
        });
    }

    @NonNull
    @Override
    public BudgetAdapter.BudgetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.budget_recycleritems, parent, false);
        return new BudgetAdapter.BudgetViewHolder(view);
    }

    class BudgetViewHolder extends RecyclerView.ViewHolder {
        TextView budgetTitleRecycle;
        TextView budgetDescRecycle;
        TextView budgetAmountRecycle;

        public BudgetViewHolder(@NonNull View itemView) {
            super(itemView);
            budgetTitleRecycle = itemView.findViewById(R.id.budgetTitleRecycler);
            budgetDescRecycle = itemView.findViewById(R.id.budgetDescriptionRecycler);
            budgetAmountRecycle = itemView.findViewById(R.id.budgetExpensesRecycler);
        }
    }
}
