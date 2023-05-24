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

public class ProductCheckerAdapter extends FirestoreRecyclerAdapter<ProductChecker, ProductCheckerAdapter.CheckerViewHolder> {
    Context context;

    public ProductCheckerAdapter(@NonNull FirestoreRecyclerOptions<ProductChecker> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    @SuppressLint("RecyclerView")
    protected void onBindViewHolder(@NonNull ProductCheckerAdapter.CheckerViewHolder holder, int position, @NonNull ProductChecker productChecker) {
        holder.productNameRecycler.setText(productChecker.prodName);
        holder.productAvailRecycler.setText(productChecker.prodAvailable);
        holder.storeAvailRecycler.setText(productChecker.storeAvailable);
        String availMessage = "Available last: " + ProductCheckerHelper.timeStamptoString(productChecker.timestamp);
        if (productChecker.timestamp != null) {
            holder.timeRecycler.setText(availMessage);
        } else {
            holder.timeRecycler.setText("No timestamp available.");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, AddFeatureChecker.class);
                i.putExtra("productName", productChecker.prodName);
                i.putExtra("productAvailable", productChecker.prodAvailable);
                i.putExtra("productStore", productChecker.storeAvailable);
                String docId = ProductCheckerAdapter.this.getSnapshots().getSnapshot(position).getId();
                i.putExtra("docId", docId);
                context.startActivity(i);
            }
        });
    }

    @NonNull
    @Override
    public ProductCheckerAdapter.CheckerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checker_recycleritems, parent, false);
        return new ProductCheckerAdapter.CheckerViewHolder(view);
    }

    class CheckerViewHolder extends RecyclerView.ViewHolder {
        TextView productNameRecycler;
        TextView productAvailRecycler;
        TextView storeAvailRecycler;
        TextView timeRecycler;

        public CheckerViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameRecycler = itemView.findViewById(R.id.checkerProdNameRecycler);
            productAvailRecycler = itemView.findViewById(R.id.productAvailableRecycler);
            storeAvailRecycler = itemView.findViewById(R.id.productStoreRecycler);
            timeRecycler = itemView.findViewById(R.id.timestampRecycler);
        }
    }
}
