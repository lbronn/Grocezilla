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

import kotlin.jvm.internal.Lambda;

public class PurchaseHistoryAdapter extends FirestoreRecyclerAdapter<PurchaseHistory, PurchaseHistoryAdapter.PurchaseViewHolder> {
    Context context;

    public PurchaseHistoryAdapter(@NonNull FirestoreRecyclerOptions<PurchaseHistory> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    @SuppressLint("RecyclerView")
    protected void onBindViewHolder(@NonNull PurchaseViewHolder holder,  int position, @NonNull PurchaseHistory purchaseHistory) {
        holder.purchaseTitleRecycle.setText(purchaseHistory.purchaseTitle);
        holder.purchaseDescRecycle.setText(purchaseHistory.purchaseDescription);
        holder.purchaseAmountRecycle.setText(purchaseHistory.totalExpenses);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, AddPurchases.class);
                i.putExtra("purchaseTitle", purchaseHistory.purchaseTitle);
                i.putExtra("purchaseDescription", purchaseHistory.purchaseDescription);
                i.putExtra("totalExpenses", purchaseHistory.totalExpenses);
                String docId = PurchaseHistoryAdapter.this.getSnapshots().getSnapshot(position).getId();
                i.putExtra("docId", docId);
                context.startActivity(i);
            }
        });
    }

    @NonNull
    @Override
    public PurchaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.purchase_recycleritems, parent, false);
        return new PurchaseViewHolder(view);
    }

    class PurchaseViewHolder extends RecyclerView.ViewHolder {
        TextView purchaseTitleRecycle;
        TextView purchaseDescRecycle;
        TextView purchaseAmountRecycle;

        public PurchaseViewHolder(@NonNull View itemView) {
            super(itemView);
            purchaseTitleRecycle = itemView.findViewById(R.id.purchaseTitleRecycler);
            purchaseDescRecycle = itemView.findViewById(R.id.purchaseDescriptionRecycler);
            purchaseAmountRecycle = itemView.findViewById(R.id.purchaseExpensesRecycler);
        }
    }
}
