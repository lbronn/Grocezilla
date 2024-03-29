package com.example.partial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {
    TextView createAccount;
    TextView forgotPass;
    Button login;
    EditText email;
    EditText password;
    CheckBox remember;
    FirebaseAuth mAuth;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.editEmail);
        password = findViewById(R.id.editPass);
        login = findViewById(R.id.btnLogin);
        forgotPass = findViewById(R.id.forgotPassword);
        remember = findViewById(R.id.loginRemember);
        createAccount = findViewById(R.id.createacc);

        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox  = preferences.getString("remember", "");
        if(checkbox.equals("true")) {
            Intent i = new Intent(login.this, home.class);
            startActivity(i);
            finish();
        }

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(login.this, signup.class);
                startActivity(i);
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailtoStr, passwordtoStr;
                emailtoStr = email.getText().toString();
                passwordtoStr = password.getText().toString();

                if(!validateEmail()) {
                    Toast.makeText(login.this, "Please enter an email.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!validatePassword()) {
                    Toast.makeText(login.this, "Please enter a password.", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(emailtoStr, passwordtoStr)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Login Successful!",
                                            Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(getApplicationContext(), home.class);
                                    startActivity(i);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(login.this, "Please enter a valid email or password.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(login.this, forgotpassword.class);
                startActivity(i);
                finish();
            }
        });

        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()) {
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "true");
                    editor.apply();
                } else if(!compoundButton.isChecked()) {
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "false");
                    editor.apply();
                }
            }
        });
    }
}