package com.example.ghurefiribangladesh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class olduser extends AppCompatActivity {

    EditText emailId, password,uname;
    Button btnSignIn;
    TextView tvSignUp;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        btnSignIn = findViewById(R.id.button);
        tvSignUp = findViewById(R.id.textView);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if (mFirebaseUser != null) {
                    Toast.makeText(olduser.this, "You Are Logged In", Toast.LENGTH_SHORT).show();
                    //Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                    //startActivity(i);
                } else {
                    Toast.makeText(olduser.this, "Please Log In", Toast.LENGTH_SHORT).show();


                }
            }
        };
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailId.getText().toString();
                String pwd = password.getText().toString();
                if (email.isEmpty()) {
                    emailId.setError("Please Enter Email ID");
                    emailId.requestFocus();
                } else if (pwd.isEmpty()) {
                    password.setError("Please Enter a Valid Password");
                    password.requestFocus();
                } else if (email.isEmpty() && pwd.isEmpty()) {
                    Toast.makeText(olduser.this, "Fields Are Empty !", Toast.LENGTH_SHORT).show();
                } else if (!(email.isEmpty() && pwd.isEmpty())) {
                    mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(olduser.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(olduser.this, "Login Error , Please Log In Again ", Toast.LENGTH_SHORT).show();

                            }
                            else
                            {
                                //email verification check
                                if(mFirebaseAuth.getCurrentUser().isEmailVerified()) {
                                    Intent intToHome = new Intent(olduser.this, HomeActivity.class);
                                    startActivity(intToHome);
                                }
                                else {
                                    Toast.makeText(olduser.this, "Please Verify Your Email ! ", Toast.LENGTH_SHORT).show();

                                }
                            }

                        }
                    });

                } else {
                    Toast.makeText(olduser.this, "Error Ocurred ! ", Toast.LENGTH_SHORT).show();

                }


            }
        });
        // if not registered
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intSignUp = new Intent(olduser.this, MainActivity.class);
                startActivity(intSignUp);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}