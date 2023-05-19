package com.example.partial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

public class AddFeatureJournal extends AppCompatActivity {

    ImageButton saveJournal;
    EditText journalTitle;
    EditText journalContent;
    TextView journalPageTitle;
    String jNewTitle, jNewContent, docId;
    boolean editJournalMode = false;
    Button deleteJournal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journaladder);
        saveJournal = findViewById(R.id.saveJournal);
        journalTitle = findViewById(R.id.editJournalTitle);
        journalContent = findViewById(R.id.editJournalContent);
        journalPageTitle = findViewById(R.id.journalPageTitle);
        deleteJournal = findViewById(R.id.journalDelete);

        jNewTitle = getIntent().getStringExtra("title");
        jNewContent = getIntent().getStringExtra("content");
        docId = getIntent().getStringExtra("docId");

        if(docId != null && !docId.isEmpty()) {
            editJournalMode = true;
        }

        journalTitle.setText(jNewTitle);
        journalContent.setText(jNewContent);

        if(editJournalMode) {
            journalPageTitle.setText("Edit your Journal");
            deleteJournal.setVisibility(View.VISIBLE);
        }

        saveJournal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                journalSaver();
            }
        });

        deleteJournal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteJournalFromFirebase();
            }
        });
    }

    public void journalSaver() {
        String jourTitle = journalTitle.getText().toString();
        String jourContent = journalContent.getText().toString();
        if(jourTitle.isEmpty() || jourContent.isEmpty()) {
            Toast.makeText(AddFeatureJournal.this, "Title and content must not be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        Journal journal = new Journal();
        journal.setjTitle(jourTitle);
        journal.setjContent(jourContent);
        journal.setTimestamp(Timestamp.now());

        saveJournalToFirebase(journal);
    }

    public void saveJournalToFirebase(Journal journal) {
        DocumentReference documentReference;

        if(editJournalMode) {
            documentReference = JournalHelper.getCollectionReferenceForJournal().document(docId);
        } else {
            documentReference = JournalHelper.getCollectionReferenceForJournal().document();
        }

        documentReference.set(journal).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    JournalHelper.showToast(AddFeatureJournal.this, "A journal is added succesfully!");
                    Intent i = new Intent(AddFeatureJournal.this, JournalAdder.class);
                    startActivity(i);
                    finish();
                } else {
                    JournalHelper.showToast(AddFeatureJournal.this, "Journal is not added, please try again.");
                }
            }
        });
    }

    public void deleteJournalFromFirebase() {
        DocumentReference documentReference;
        documentReference = JournalHelper.getCollectionReferenceForJournal().document(docId);


        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    JournalHelper.showToast(AddFeatureJournal.this, "A journal is deleted succesfully!");
                    Intent i = new Intent(AddFeatureJournal.this, JournalAdder.class);
                    startActivity(i);
                    finish();
                } else {
                    JournalHelper.showToast(AddFeatureJournal.this, "Journal is not deleted, please try again.");
                }
            }
        });
    }
}