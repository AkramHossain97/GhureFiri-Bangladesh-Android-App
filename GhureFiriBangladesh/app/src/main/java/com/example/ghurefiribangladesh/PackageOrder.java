package com.example.ghurefiribangladesh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.apptakk.http_request.HttpRequest;
import com.apptakk.http_request.HttpRequestTask;
import com.apptakk.http_request.HttpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.HttpURLConnection;
import java.util.Calendar;
import java.util.Random;

public class PackageOrder extends AppCompatActivity {private String mPlaceName = null;
    private String mPackageName = null;
    private String mPackageDescription = null;
    private String mHotelPackage = null;
    private String mDiningPackage = null;
    private String mCoveringSpots = null;
    private String mTransportPackage = null;
    private String mJourneyDate = null;
    private String mTotalPricePackage = null;
    TextView TPackageName;
    TextView TPackageDescription;
    TextView THotelPackage;
    TextView TDiningPackage;
    TextView TCoveringSpots;
    TextView TTransportPackage;
    TextView TJourneyDate;
    TextView TTotalPricePackage;
    TextView mUserNname;
    EditText mPhoneNumber;
    Button mPayNowBTN;
    private FirebaseUser userP = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference mDatabaseUserP;
    int PrandomNumber;
    String Ptextmessage;
    String Pto;



    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_order);

        mPackageName=getIntent().getExtras().getString("Package_Name");
        mPackageDescription=getIntent().getExtras().getString("Package_Description");
        mHotelPackage=getIntent().getExtras().getString("Hotel_name");
        mDiningPackage=getIntent().getExtras().getString("Dining");
        mCoveringSpots=getIntent().getExtras().getString("Covering_Spots");
        mTransportPackage=getIntent().getExtras().getString("Transportation");
        mJourneyDate=getIntent().getExtras().getString("Journey_Date");
        mTotalPricePackage=getIntent().getExtras().getString("Total_Amount");


        TPackageName=findViewById(R.id.PackageNameOrder);
        TPackageDescription=findViewById(R.id.PackageDesceOrder);
        THotelPackage=findViewById(R.id.PackageHotelOrder);
        TDiningPackage=findViewById(R.id.packageDiningOrder);
        TCoveringSpots=findViewById(R.id.CoveringSpotsPackageOrder);
        TTransportPackage=findViewById(R.id.PackageTransportationOrder);
        TJourneyDate=findViewById(R.id.PackageJourneyDateOrder);
        TTotalPricePackage=findViewById(R.id.TotalPackagePriceOrder);
        mUserNname=findViewById(R.id.UNAME);
        mPhoneNumber=findViewById(R.id.UPhoneNumber);
        mPayNowBTN=findViewById(R.id.PackagePayNow);

        TPackageName.setText(mPackageName);
        TPackageDescription.setText(mPackageDescription);
        THotelPackage.setText("Hotel : "+mHotelPackage);
        TDiningPackage.setText("Dining : "+mDiningPackage);
        TCoveringSpots.setText("Spot Visits : "+mCoveringSpots);
        TTransportPackage.setText(mTransportPackage);
        TJourneyDate.setText("Journey Date : "+mJourneyDate);
        TTotalPricePackage.setText(mTotalPricePackage+" BDT");



        mDatabaseUserP= FirebaseDatabase.getInstance().getReference().child("users").child(userP.getUid());


        mDatabaseUserP.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                UserProfileInfo userProfileInfo = snapshot.getValue(UserProfileInfo.class);
                mUserNname.setText(userProfileInfo.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


            mPayNowBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Random random = new Random();
                    PrandomNumber=random.nextInt(999999);
                    Pto=mPhoneNumber.getText().toString();



                    Ptextmessage = "Your+verification+code+for+GhureFiri+Bangladesh+is+"+PrandomNumber+" ";




                    new HttpRequestTask(
                            new HttpRequest("https://api.greenweb.com.bd/api.php?token=TokenHasBeenRemoved&to="+Pto+"&message="+Ptextmessage, HttpRequest.POST, "{ \"some\": \"data\" }"),
                            new HttpRequest.Handler() {
                                @Override
                                public void response(HttpResponse response) {
                                    if (response.code == 200) {
                                        Intent VerifyIntent = new Intent(PackageOrder.this,PaymentVerification.class);
                                        VerifyIntent.putExtra("RandomNumberGen",PrandomNumber);
                                        VerifyIntent.putExtra("PhoneNumberGen",Pto);
                                        startActivity(VerifyIntent);
                                    } else {
                                        Log.e(this.getClass().toString(), "Request unsuccessful: " + response);
                                    }
                                }
                            }).execute();
                }
            });



    }
}
