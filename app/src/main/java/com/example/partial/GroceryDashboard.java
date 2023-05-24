package com.example.partial;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GroceryDashboard extends AppCompatActivity {
    ImageView imgGrocerList;
    ImageView imgProductCheck;
    ImageView imgProductReview;
    TextView txtGrocerList;
    TextView txtProductCheck;
    TextView txtProductReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_grocery);
        imgGrocerList = findViewById(R.id.imgDBGroceryList);
        imgProductCheck = findViewById(R.id.imgDBProductCheck);
        imgProductReview = findViewById(R.id.imgDBProductReviews);
        txtGrocerList = findViewById(R.id.txtDBGroceryList);
        txtProductCheck = findViewById(R.id.txtDBProductCheck);
        txtProductReview = findViewById(R.id.txtDBProductReviews);

        imgGrocerList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GroceryDashboard.this, GroceryAdder.class);
                startActivity(i);
                finish();
            }
        });

        txtGrocerList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GroceryDashboard.this, GroceryAdder.class);
                startActivity(i);
                finish();
            }
        });

//        imgProductCheck.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(GroceryDashboard.this, GroceryAdder.class);
//                startActivity(i);
//                finish();
//            }
//        });
//
//        txtProductCheck.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(GroceryDashboard.this, GroceryAdder.class);
//                startActivity(i);
//                finish();
//            }
//        });
//
        imgProductReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GroceryDashboard.this, ProductReviewAdder.class);
                startActivity(i);
                finish();
            }
        });

        txtProductReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GroceryDashboard.this, ProductReviewAdder.class);
                startActivity(i);
                finish();
            }
        });
    }
}
