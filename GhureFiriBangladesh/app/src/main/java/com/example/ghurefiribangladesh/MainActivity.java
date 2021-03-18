package com.example.ghurefiribangladesh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText emailId,password ;
    Button btnSignUp ;
    TextView tvSignIn ;
    FirebaseAuth mFirebaseAuth;
    int RC_SIGN_IN = 264;
    String TAG ="Google Sign In";
    GoogleSignInClient mGoogleSignInClient;
    SignInButton signInBut;
    FirebaseDatabase rootNode;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        btnSignUp = findViewById(R.id.button);
        tvSignIn = findViewById(R.id.textView);
        signInBut=findViewById(R.id.sign_g);
        //request token for sign in with google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient=GoogleSignIn.getClient(this,gso);
        mFirebaseAuth=FirebaseAuth.getInstance();

        signInBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();


            }
        });
        //sign up via email and password
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = emailId.getText().toString();
                String pwd = password.getText().toString();
                if (email.isEmpty()){
                    emailId.setError("Please Enter Email ID");
                    emailId.requestFocus();
                }
                else if (pwd.isEmpty()){
                    password.setError("Please Enter a Valid Password");
                    password.requestFocus();
                }
                else if (email.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(MainActivity.this,"Fields Are Empty !",Toast.LENGTH_SHORT).show();
                }
                else if(!(email.isEmpty() && pwd.isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(MainActivity.this,"SignUp Unsuccessful , Please Try Again",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                mFirebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> taske) {
                                        if(taske.isSuccessful()){
                                            startActivity(new Intent(MainActivity.this,Registerv.class));
                                          //  Toast.makeText(Registerv.this,"Registered Successfully",Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                              // startActivity(new Intent(MainActivity.this,Registerv.class));
                            }
                        }
                    });

                }
                else {
                    Toast.makeText(MainActivity.this,"Error Ocurred ! ",Toast.LENGTH_SHORT).show();

                }


            }
        });
        //if already registered ( login in )
        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,olduser.class));

            }
        });





    }
    //google sign in method
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    //google sign in
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mFirebaseAuth.getCurrentUser();
                           updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                           // Snackbar.make(mBinding.mainLayout, "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }
    // update ui state for user
    private void updateUI(FirebaseUser fuser){
        //firebase database reference
        rootNode= FirebaseDatabase.getInstance();
        reference=rootNode.getReference("users");
        // login information put into database ( google sign in )
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if(account != null ) {
            String email = account.getEmail().toString();
            //String uid = account.getId().toString();
            String uid= mFirebaseAuth.getUid();
            String Username = account.getDisplayName();
            String Image_Url = account.getPhotoUrl().toString();
            UserHelperClass helperClass = new UserHelperClass(email, uid,Username);
            reference.child(uid).setValue(helperClass);
            reference.child(uid).child("Image_Url").setValue(Image_Url);
        }
        Intent ih = new Intent(MainActivity.this,HomeActivity.class);
        startActivity(ih);

    }



}