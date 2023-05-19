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

public class JournalAdapter extends FirestoreRecyclerAdapter<Journal, JournalAdapter.JournalViewHolder> {
    Context context;

    public JournalAdapter(@NonNull FirestoreRecyclerOptions<Journal> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    @SuppressLint("RecyclerView")
    protected void onBindViewHolder(@NonNull JournalViewHolder holder, int position, @NonNull Journal journal) {
        holder.jTitleRecycler.setText(journal.jTitle);
        holder.jContentRecycler.setText(journal.jContent);
        holder.jTimestampRecycler.setText(JournalHelper.timeStamptoString(journal.timestamp));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, AddFeatureJournal.class);
                i.putExtra("title", journal.jTitle);
                i.putExtra("content", journal.jContent);
                String docId = JournalAdapter.this.getSnapshots().getSnapshot(position).getId();
                i.putExtra("docId", docId);
                context.startActivity(i);
            }
        });
    }

    @NonNull
    @Override
    public JournalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.journal_recycleritems, parent, false);
        return new JournalViewHolder(view);
    }

    class JournalViewHolder extends RecyclerView.ViewHolder {
        TextView jTitleRecycler;
        TextView jContentRecycler;
        TextView jTimestampRecycler;

        public JournalViewHolder(@NonNull View itemView) {
            super(itemView);
            jTitleRecycler = itemView.findViewById(R.id.journalTitleRecycler);
            jContentRecycler = itemView.findViewById(R.id.journalContentRecycler);
            jTimestampRecycler = itemView.findViewById(R.id.journalTimestampRecycler);
        }
    }
}
