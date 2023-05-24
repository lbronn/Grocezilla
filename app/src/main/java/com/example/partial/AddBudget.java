package com.example.partial;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

public class AddBudget extends AppCompatActivity {
    View backToGroceryList;
    TextView budgetPageTitle;
    EditText budgetTitle;
    EditText budgetAmount;
    EditText budgetDescription;
    AppCompatButton addBudget;
    AppCompatButton deleteBudget;
    String bTitle, bAmount, bDesc, docId;
    boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addbudget);
        backToGroceryList = findViewById(R.id.backToGroceryList);
        budgetPageTitle = findViewById(R.id.budgetPageTitle);
        budgetTitle = findViewById(R.id.editBudgetName);
        budgetAmount = findViewById(R.id.editBudgetAmount);
        budgetDescription = findViewById(R.id.editBudgetDescription);
        addBudget = findViewById(R.id.btnBudgetAdder);
        deleteBudget = findViewById(R.id.btnBudgetDeleter);

        bTitle = getIntent().getStringExtra("budgetTitle");
        bDesc = getIntent().getStringExtra("budgetDescription");
        bAmount = getIntent().getStringExtra("budgetAmount");
        docId = getIntent().getStringExtra("docId");

        if(docId != null && !docId.isEmpty()) {
            isEditMode = true;
        }

        budgetTitle.setText(bTitle);
        budgetAmount.setText(bAmount);
        budgetDescription.setText(bDesc);

        if(isEditMode) {
            budgetPageTitle.setText("Edit your budget for today's run");
            deleteBudget.setVisibility(View.VISIBLE);
        }

        backToGroceryList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isEditMode) {
                    Intent i = new Intent(AddBudget.this, GroceryAdder.class);
                    startActivity(i);
                    finish();
                }
                Intent i = new Intent(AddBudget.this, BudgetAdder.class);
                startActivity(i);
                finish();
            }
        });

        addBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveBudget();
            }
        });

        deleteBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteBudgetFromFirebase();
            }
        });
    }

    public void saveBudget() {
        String budTitle = budgetTitle.getText().toString();
        String budAmount = budgetAmount.getText().toString();
        String budDesc = budgetDescription.getText().toString();
        if(budTitle.isEmpty() || budAmount.isEmpty() || budDesc.isEmpty()) {
            budgetTitle.setError("All fields are required!");
            return;
        }
        Budget budget = new Budget();
        budget.setBudgetTitle(budTitle);
        budget.setBudgetAmount(budAmount);
        budget.setBudgetDescription(budDesc);

        saveBudgetToFirebase(budget);
    }

    public void saveBudgetToFirebase(Budget budget) {
        DocumentReference documentReference;
        if(isEditMode) {
            documentReference = BudgetHelper.getCollectionReferenceForBudget().document(docId);
        } else {
            documentReference = BudgetHelper.getCollectionReferenceForBudget().document();
        }

        documentReference.set(budget).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    BudgetHelper.showToast(AddBudget.this, "Successfully added a budget for your grocery today!");
                    Intent i = new Intent(AddBudget.this, BudgetAdder.class);
                    startActivity(i);
                    finish();
                } else {
                    BudgetHelper.showToast(AddBudget.this, "Budget for today is not added, please try again.");
                }
            }
        });
    }

    public void deleteBudgetFromFirebase() {
        DocumentReference documentReference;
        documentReference = BudgetHelper.getCollectionReferenceForBudget().document(docId);

        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    BudgetHelper.showToast(AddBudget.this, "Successfully deleted your budget for today!");
                    Intent i = new Intent(AddBudget.this, BudgetAdder.class);
                    startActivity(i);
                    finish();
                } else {
                    BudgetHelper.showToast(AddBudget.this, "Budget for today is not deleted, please try again.");
                }
            }
        });
    }
}
