package com.example.ghurefiribangladesh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PaymentVerification extends AppCompatActivity {

    EditText Vcode;
    Button mbtnVerify;
    private int mRandomNumber;
    private String mPhoneNumber = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_verification);
        mRandomNumber=getIntent().getExtras().getInt("RandomNumberGen");
        mPhoneNumber=getIntent().getExtras().getString("PhoneNumberGen");


        Vcode=findViewById(R.id.VCODET);
        mbtnVerify=findViewById(R.id.Verify);


        mbtnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRandomNumber==Integer.parseInt(Vcode.getText().toString())){
                    Toast.makeText(PaymentVerification.this,"Payment successful , Please See Your Profile For Booking List",Toast.LENGTH_SHORT).show();
                    Intent HomeIntent = new Intent(PaymentVerification.this,HomeActivity.class);
                    startActivity(HomeIntent);




                }else{
                    Toast.makeText(PaymentVerification.this,"Payment Unsuccessful , Please Put The Correct Verification Code ",Toast.LENGTH_SHORT).show();


                }

            }
        });

    }
}