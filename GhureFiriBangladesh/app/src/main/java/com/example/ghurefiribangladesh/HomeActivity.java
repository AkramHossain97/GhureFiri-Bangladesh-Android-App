package com.example.ghurefiribangladesh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;


public class HomeActivity extends AppCompatActivity {
    ImageButton btnLogout;
    ImageButton btnChat;
    ImageButton btnTour;
    ImageButton btnExplore;
    ImageButton btnPackage;
    FirebaseAuth mFirebaseAuth;
    ImageButton btnProfile;
    GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnLogout = findViewById(R.id.logout);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                mGoogleSignInClient.signOut();
                Intent intToMain = new Intent(HomeActivity.this,MainActivity.class);
                finishAffinity();
                startActivity(intToMain);

            }
        });
        //go to chatbot(customer support)
        btnChat= findViewById(R.id.chat);
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intToMain = new Intent(HomeActivity.this,chat.class);
                startActivity(intToMain);

            }
        });
        //go to explore
        btnExplore= findViewById(R.id.explore);
        btnExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intToEx = new Intent(HomeActivity.this,explore.class);
                startActivity(intToEx);

            }
        });


        // go to profile
        btnProfile=findViewById(R.id.editProfile);
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intToProfile = new Intent(HomeActivity.this,Profile.class);
                startActivity(intToProfile);
            }
        });


        // go to Package Options
        btnPackage=findViewById(R.id.packag);
        btnPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intToPackage = new Intent(HomeActivity.this,PackageOptions.class);
                startActivity(intToPackage);
            }
        });




        // go to tour options

        btnTour=findViewById(R.id.tour);
        btnTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intToTour = new Intent(HomeActivity.this,Tour.class);
                startActivity(intToTour);

            }
        });


    }
}