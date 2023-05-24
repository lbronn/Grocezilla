package com.example.partial;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ReviewRecyclerView extends RecyclerView.Adapter<ReviewRecyclerView.ViewHolder> {
    Context context;
    ArrayList<AddReview> addReviewArrayList;
    DatabaseReference databaseReference;

    public ReviewRecyclerView(Context context, ArrayList<AddReview> addReviewArrayList) {
        this.context = context;
        this.addReviewArrayList = addReviewArrayList;
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @NonNull
    @Override
    public ReviewRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.addingreview, parent, false);
        return new ReviewRecyclerView.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewRecyclerView.ViewHolder holder, int position) {
        AddReview addReviews = addReviewArrayList.get(position);
        holder.productName.setText("Product Name: " + addReviews.getProductName());
        holder.reviewDescribe.setText("Review Description: " + addReviews.getReviewDesc());
        holder.reviewRating.setText("Rating: " + addReviews.getReviewRate());
        holder.storePurchased.setText("Grocery Store: " + addReviews.getStorePurchased());
        holder.recommend.setText("Will be Bought Again? " + addReviews.getRecommendIt());

        holder.updateReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReviewRecyclerView.ViewDialogUpdate viewDialogUpdate = new ReviewRecyclerView.ViewDialogUpdate();
                viewDialogUpdate.showDialog(context, addReviews.getReviewID(), addReviews.getProductName(), addReviews.getReviewDesc(), addReviews.getReviewRate(), addReviews.getStorePurchased(), addReviews.getRecommendIt());
            }
        });

        holder.deleteReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReviewRecyclerView.ViewDialogDelete viewDialogDelete = new ReviewRecyclerView.ViewDialogDelete();
                viewDialogDelete.showDialog(context, addReviews.getReviewID());
            }
        });
    }

    @Override
    public int getItemCount() {
        return addReviewArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        TextView reviewDescribe;
        TextView reviewRating;
        TextView storePurchased;
        TextView recommend;
        Button deleteReview;
        Button updateReview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.productName);
            reviewDescribe = itemView.findViewById(R.id.descReview);
            reviewRating = itemView.findViewById(R.id.ratingReview);
            storePurchased = itemView.findViewById(R.id.storeName);
            recommend = itemView.findViewById(R.id.recommendProduct);
            deleteReview = itemView.findViewById(R.id.btnDeleteReview);
            updateReview = itemView.findViewById(R.id.btnUpdateReview);
        }
    }

    public class ViewDialogUpdate {
        public void showDialog(Context context, String reviewID, String productName, String reviewDesc, String reviewRate, String store, String recommend) {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.alertdialog_addnewreview);

            EditText addProductName = dialog.findViewById(R.id.ProductNameAdd);
            EditText addReviewDesc = dialog.findViewById(R.id.ReviewDescAdd);
            EditText addReviewRating = dialog.findViewById(R.id.ReviewRatingAdd);
            EditText addStore = dialog.findViewById(R.id.GroceryStoreAdd);
            EditText addRecommend = dialog.findViewById(R.id.ProductRecommendAdd);
            Button updateReview = dialog.findViewById(R.id.buttonReviewAdd);
            Button exitReview = dialog.findViewById(R.id.buttonReviewExit);

            addProductName.setText(productName);
            addReviewDesc.setText(reviewDesc);
            addReviewRating.setText(reviewRate);
            addStore.setText(store);
            addRecommend.setText(recommend);
            updateReview.setText("UPDATE");

            exitReview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            updateReview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String newProdName = addProductName.getText().toString();
                    String newRevDesc = addReviewDesc.getText().toString();
                    String newRevRating = addReviewRating.getText().toString();
                    String newStore = addStore.getText().toString();
                    String newRecommend = addRecommend.getText().toString();

                    if(newProdName.isEmpty() || newRevDesc.isEmpty() || newRevRating.isEmpty() || newStore.isEmpty() || newRecommend.isEmpty()) {
                        Toast.makeText(context, "Please provide information on all fields.", Toast.LENGTH_SHORT).show();
                    } else {
                        if(newProdName.equals(productName) && newRevDesc.equals(reviewDesc) && newRevRating.equals(reviewRate) && newStore.equals(store) && newRecommend.equals(recommend)) {
                            Toast.makeText(context, "Nothing is updated.", Toast.LENGTH_SHORT).show();
                        } else {
                            databaseReference.child("REVIEWS").child(reviewID).setValue(new AddReview(reviewID, newProdName, newRevDesc, newRevRating, newStore, newRecommend));
                            Toast.makeText(context, "A review is updated successfully!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                }
            });

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }
    }

    public class ViewDialogDelete {
        public void showDialog(Context context, String id) {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.alertdialog_deletereview);

            Button deleteReview = dialog.findViewById(R.id.buttonYesDelReview);
            Button exitReview = dialog.findViewById(R.id.buttonNoDelReview);

            exitReview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            deleteReview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (id != null) {
                        databaseReference.child("REVIEWS").child(id).removeValue();
                        Toast.makeText(context, "A review is deleted successfully!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }
            });

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }
    }
}
