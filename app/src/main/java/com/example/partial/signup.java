package com.example.partial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {
    Button signup;
    EditText firstName;
    EditText lastName;
    EditText email;
    EditText password;
    FirebaseAuth mAuth;
//    FirebaseDatabase database;
//    DatabaseReference reference;

//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            Intent i = new Intent(getApplicationContext(), home.class);
//            startActivity(i);
//            finish();
//        }
//    }

    public Boolean validateEmail() {
        String valEmail = email.getText().toString();
        if(valEmail.isEmpty()) {
            email.setError("Email Address cannot be empty!");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }

    public Boolean validatePassword() {
        String valEmail = password.getText().toString();
        if(valEmail.isEmpty()) {
            password.setError("Password cannot be empty!");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }

    public Boolean validateFName() {
        String valEmail = firstName.getText().toString();
        if(valEmail.isEmpty()) {
            firstName.setError("First name cannot be empty!");
            return false;
        } else {
            firstName.setError(null);
            return true;
        }
    }

    public Boolean validateLName() {
        String valEmail = lastName.getText().toString();
        if(valEmail.isEmpty()) {
            lastName.setError("Last name cannot be empty!");
            return false;
        } else {
            lastName.setError(null);
            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        firstName = findViewById(R.id.editname);
        lastName = findViewById(R.id.editlname);
        email = findViewById(R.id.editemail);
        password = findViewById(R.id.passEdit);
        signup = findViewById(R.id.btnSignup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                database = FirebaseDatabase.getInstance();
//                reference = database.getReference("users");
                String firstNametoStr, lastNametoStr, emailtoStr, passwordtoStr;
                firstNametoStr = firstName.getText().toString();
                lastNametoStr = lastName.getText().toString();
                emailtoStr = email.getText().toString();
                passwordtoStr = password.getText().toString();

                if(!validateFName()) {
                    Toast.makeText(signup.this, "Please enter your first name.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!validateLName()) {
                    Toast.makeText(signup.this, "Please enter your last name.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!validateEmail()) {
                    Toast.makeText(signup.this, "Please enter an email.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!validatePassword()) {
                    Toast.makeText(signup.this, "Please enter a password.", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(emailtoStr, passwordtoStr)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
//                                    HelperClass helperClass = new HelperClass(firstNametoStr, lastNametoStr, emailtoStr, passwordtoStr);
//                                    reference.child(firstNametoStr).setValue(helperClass);
                                    Toast.makeText(signup.this, "Signup success, welcome to Grocezilla!",
                                            Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(signup.this, login.class);
                                    startActivity(i);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(signup.this, "Signup failed, please try again.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }
}