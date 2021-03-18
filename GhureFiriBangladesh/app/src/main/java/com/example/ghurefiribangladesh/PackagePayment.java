package com.example.ghurefiribangladesh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PackagePayment extends AppCompatActivity {
    private int mPRandomNumber;
    private String mPPhoneNumber = null;
    EditText Vcode;
    Button mbtnVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_payment);

        mPRandomNumber=getIntent().getExtras().getInt("RandomNumberGen");
        mPPhoneNumber=getIntent().getExtras().getString("PhoneNumberGen");


        Vcode=findViewById(R.id.VCODETP);
        mbtnVerify=findViewById(R.id.VerifyP);


        mbtnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPRandomNumber==Integer.parseInt(Vcode.getText().toString())){
                    Toast.makeText(PackagePayment.this,"Payment successful , Please See Your Profile For Booking List",Toast.LENGTH_SHORT).show();
                    Intent HomeIntent = new Intent(PackagePayment.this,HomeActivity.class);
                    startActivity(HomeIntent);




                }else{
                    Toast.makeText(PackagePayment.this,"Payment Unsuccessful , Please Put The Correct Verification Code ",Toast.LENGTH_SHORT).show();


                }

            }
        });

    }
}