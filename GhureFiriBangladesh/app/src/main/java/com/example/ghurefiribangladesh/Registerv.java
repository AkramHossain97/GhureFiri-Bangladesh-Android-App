package com.example.ghurefiribangladesh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Registerv extends AppCompatActivity {
    Button btnLogIn;
    //buffer page between mail verification
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerv);
        Toast.makeText(Registerv.this,"You Have successfully Registered , Please verify Your Email And Goto Login ",Toast.LENGTH_SHORT).show();
        btnLogIn= findViewById(R.id.LogIn);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //after verification takes user to login page
                Intent intToLogin = new Intent(Registerv.this,LoginActivity.class);
                startActivity(intToLogin);

            }
        });
    }
}