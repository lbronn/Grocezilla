package com.example.partial;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

public class AddFeatureChecker extends AppCompatActivity {
    View backDashboard;
    TextView checkerPageTitle;
    EditText checkProductName;
    EditText checkAvailable;
    EditText checkStore;
    AppCompatButton available;
    AppCompatButton unavailable;
    String cProdName, cAvailable, cStore, docId;
    boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addingchecker);
        backDashboard = findViewById(R.id.addCheckerToViewChecker);
        checkerPageTitle = findViewById(R.id.checkerPageTitle);
        checkProductName = findViewById(R.id.productName);
        checkAvailable = findViewById(R.id.productAvailable);
        checkStore = findViewById(R.id.productStoreAvailable);
        available = findViewById(R.id.btnAddChecker);
        unavailable = findViewById(R.id.btnDeleteChecker);

        cProdName = getIntent().getStringExtra("productName");
        cAvailable = getIntent().getStringExtra("productAvailable");
        cStore = getIntent().getStringExtra("productStore");
        docId = getIntent().getStringExtra("docId");

        if(docId != null && !docId.isEmpty()) {
            isEditMode = true;
        }

        checkProductName.setText(cProdName);
        checkAvailable.setText(cAvailable);
        checkStore.setText(cStore);

        if(isEditMode) {
            checkerPageTitle.setText("Edit the product's availability");
            unavailable.setVisibility(View.VISIBLE);
        }

        backDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddFeatureChecker.this, GroceryDashboard.class);
                startActivity(i);
                finish();
            }
        });

        available.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveChecker();
            }
        });

        unavailable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCheckerFromFirebase();
            }
        });
    }

    public void saveChecker() {
        String chckProdName = checkProductName.getText().toString();
        String chckAvail = checkAvailable.getText().toString();
        String chckStore = checkStore.getText().toString();
        if(chckProdName.isEmpty() || chckAvail.isEmpty() || chckStore.isEmpty()) {
            checkProductName.setError("All fields are required!");
            return;
        }
        ProductChecker productChecker = new ProductChecker();
        productChecker.setProdName(chckProdName);
        productChecker.setProdAvailable(chckAvail);
        productChecker.setStoreAvailable(chckStore);
        productChecker.setTimestamp(Timestamp.now());

        saveCheckerToFirebase(productChecker);
    }

    public void saveCheckerToFirebase(ProductChecker productChecker) {
        DocumentReference documentReference;
        if(isEditMode) {
            documentReference = ProductCheckerHelper.getCollectionReferenceForChecker().document(docId);
        } else {
            documentReference = ProductCheckerHelper.getCollectionReferenceForChecker().document();
        }

        documentReference.set(productChecker).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    ProductCheckerHelper.showToast(AddFeatureChecker.this, "Successfully added to available products!");
                    Intent i = new Intent(AddFeatureChecker.this, ProductCheckerAdder.class);
                    startActivity(i);
                    finish();
                } else {
                    PurchaseHistoryHelper.showToast(AddFeatureChecker.this, "Not yet added to available products, please try again.");
                }
            }
        });
    }

    public void deleteCheckerFromFirebase() {
        DocumentReference documentReference;
        documentReference = ProductCheckerHelper.getCollectionReferenceForChecker().document(docId);

        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    ProductCheckerHelper.showToast(AddFeatureChecker.this, "This product is now unavailable!");
                    Intent i = new Intent(AddFeatureChecker.this, ProductCheckerAdder.class);
                    startActivity(i);
                    finish();
                } else {
                    ProductCheckerHelper.showToast(AddFeatureChecker.this, "Product is not yet unavailable, please try again.");
                }
            }
        });
    }
}
