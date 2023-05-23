package com.example.partial;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

import org.w3c.dom.Document;

public class AddPurchases extends AppCompatActivity {
    View backToHome;
    EditText expenseTitle;
    EditText expenseAmount;
    EditText expenseDesc;
    AppCompatButton addExpense;
    AppCompatButton deleteExpense;
    TextView purchaseTitle;
    String pTitle, pDesc, pExpense, docId;
    boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpurchases);
        backToHome = findViewById(R.id.addExpenseToViewHistory);
        expenseTitle = findViewById(R.id.purchaseTitle);
        expenseAmount = findViewById(R.id.purchaseAmount);
        expenseDesc = findViewById(R.id.purchaseDescription);
        addExpense = findViewById(R.id.btnAddExpense);
        deleteExpense = findViewById(R.id.btnDeleteExpense);
        purchaseTitle = findViewById(R.id.purchasePageTitle);

        pTitle = getIntent().getStringExtra("purchaseTitle");
        pDesc = getIntent().getStringExtra("purchaseDescription");
        pExpense = getIntent().getStringExtra("totalExpenses");
        docId = getIntent().getStringExtra("docId");

        if(docId != null && !docId.isEmpty()) {
            isEditMode = true;
        }

        expenseTitle.setText(pTitle);
        expenseDesc.setText(pDesc);
        expenseAmount.setText(pExpense);

        if(isEditMode) {
            purchaseTitle.setText("Edit your grocery expenses");
            deleteExpense.setVisibility(View.VISIBLE);
        }

        backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddPurchases.this, PurchaseHistoryAdder.class);
                startActivity(i);
                finish();
            }
        });

        addExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePurchase();
            }
        });

        deleteExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePurchaseFromFirebase();
            }
        });
    }

    public void savePurchase() {
        String purTitle = expenseTitle.getText().toString();
        String purAmount = expenseAmount.getText().toString();
        String purDesc = expenseDesc.getText().toString();
        if(purTitle.isEmpty() || purAmount.isEmpty() || purDesc.isEmpty()) {
            expenseTitle.setError("All fields are required!");
            return;
        }
        PurchaseHistory purchaseHistory = new PurchaseHistory();
        purchaseHistory.setPurchaseTitle(purTitle);
        purchaseHistory.setTotalExpenses(purAmount);
        purchaseHistory.setPurchaseDescription(purDesc);

        savePurchaseToFirebase(purchaseHistory);
    }

    public void savePurchaseToFirebase(PurchaseHistory purchaseHistory) {
        DocumentReference documentReference;
        if(isEditMode) {
            documentReference = PurchaseHistoryHelper.getCollectionReferenceForPurchase().document(docId);
        } else {
            documentReference = PurchaseHistoryHelper.getCollectionReferenceForPurchase().document();
        }

        documentReference.set(purchaseHistory).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    PurchaseHistoryHelper.showToast(AddPurchases.this, "Successfully added to purchase history!");
                    Intent i = new Intent(AddPurchases.this, PurchaseHistoryAdder.class);
                    startActivity(i);
                    finish();
                } else {
                    PurchaseHistoryHelper.showToast(AddPurchases.this, "Purchase from last grocery is not added, please try again.");
                }
            }
        });
    }

    public void deletePurchaseFromFirebase() {
        DocumentReference documentReference;
        documentReference = PurchaseHistoryHelper.getCollectionReferenceForPurchase().document(docId);

        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    PurchaseHistoryHelper.showToast(AddPurchases.this, "Successfully deleted from purchase history!");
                    Intent i = new Intent(AddPurchases.this, PurchaseHistoryAdder.class);
                    startActivity(i);
                    finish();
                } else {
                    PurchaseHistoryHelper.showToast(AddPurchases.this, "Purchase from last grocery is not deleted, please try again.");
                }
            }
        });
    }
}
