package com.example.partial;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;

public class ProductCheckerHelper {

    static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    static CollectionReference getCollectionReferenceForChecker() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("checker").document(currentUser.getUid()).collection("available_product");
    }

    @SuppressLint("SimpleDateFormat")
    static String timeStamptoString(Timestamp timestamp) {
        return new SimpleDateFormat("MM/dd/yyyy").format(timestamp.toDate());
    }
}
