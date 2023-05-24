package com.example.partial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProductReviewAdder extends AppCompatActivity {
    DatabaseReference databaseReference;
    ArrayList<AddReview> addReviews;
    ReviewRecyclerView adapter;
    ImageButton productReviewToDashboard;
    FloatingActionButton addProductReview;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_review_adder);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        recyclerView = findViewById(R.id.reviewrecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productReviewToDashboard = findViewById(R.id.productreviewsToDashboard);
        addProductReview = findViewById(R.id.btnProductReviewAdd);
        addReviews = new ArrayList<>();

        productReviewToDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProductReviewAdder.this, GroceryDashboard.class);
                startActivity(i);
                finish();
            }
        });

        addProductReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewDialogAdd viewDialogAdd = new ProductReviewAdder.ViewDialogAdd();
                viewDialogAdd.showDialog(ProductReviewAdder.this);
            }
        });

        readData();
    }

    private void readData() {
        databaseReference.child("REVIEWS").orderByChild("reviews").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                addReviews.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    AddReview addReview = dataSnapshot.getValue(AddReview.class);
                    addReviews.add(addReview);
                }
                adapter = new ReviewRecyclerView(ProductReviewAdder.this, addReviews);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public class ViewDialogAdd {
        public void showDialog(Context context) {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.alertdialog_addnewreview);

            EditText addProductName = dialog.findViewById(R.id.ProductNameAdd);
            EditText addReviewDescription = dialog.findViewById(R.id.ReviewDescAdd);
            EditText addReviewRating = dialog.findViewById(R.id.ReviewRatingAdd);
            EditText addGroceryStore = dialog.findViewById(R.id.GroceryStoreAdd);
            EditText addRecommend = dialog.findViewById(R.id.ProductRecommendAdd);
            Button addReview = dialog.findViewById(R.id.buttonReviewAdd);
            Button exitReview = dialog.findViewById(R.id.buttonReviewExit);
            addReview.setText("ADD");

            exitReview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            addReview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id = "review" + new AddReview().getReviewID();
                    String prodName = addProductName.getText().toString();
                    String reviewDesc = addReviewDescription.getText().toString();
                    String rating = addReviewRating.getText().toString();
                    String store = addGroceryStore.getText().toString();
                    String recommend = addRecommend.getText().toString();

                    if(prodName.isEmpty() || reviewDesc.isEmpty() || rating.isEmpty() || store.isEmpty() || recommend.isEmpty()) {
                        Toast.makeText(context, "Please provide information on all fields.", Toast.LENGTH_SHORT).show();
                    } else {
                        String reviewId = databaseReference.child("REVIEWS").push().getKey(); // Generate a unique taskId
                        AddReview newReview = new AddReview(reviewId, prodName, reviewDesc, rating, store, recommend);
                        databaseReference.child("REVIEWS").child(reviewId).setValue(newReview); // Set value at the new taskId
                        Toast.makeText(context, "Successfully added a new task!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }
            });

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }
    }
}