package com.example.ghurefiribangladesh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class SinglePackageActivity extends AppCompatActivity {

    private ImageView mPackageImage;
    private TextView mPackageTitle;
    private TextView mPackageDescription;
    private TextView mPackageAccommodation;
    private TextView mPackageDining;
    private TextView mPackageSpots;
    private TextView mPackageTransportation;
    private TextView mPackagePrice;
    private DatabaseReference mDatabase ;
    private DatabaseReference mDatabasePackage;
    private String mPost_key = null;
    TextView PickDate;
    private String DateSelect;
    Button mBtnConfirmBooking;
    String CurrentUserID;
    String package_title;
    String package_description;
    String package_accommodation;
    String package_dining;
    String package_spots;
    String package_transportation;
    String package_price;
    String package_image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_package);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Package");
        mPost_key = getIntent().getExtras().getString("Package_id");

        mPackageImage = (ImageView) findViewById(R.id.PackageImage);
        mPackageTitle = (TextView) findViewById(R.id.pack_title);
        mPackageDescription = (TextView) findViewById(R.id.description);
        mPackageAccommodation = (TextView) findViewById(R.id.accommodation);
        mPackageSpots = (TextView) findViewById(R.id.spots_visit);
        mPackageDining = (TextView) findViewById(R.id.food_dine);
        mPackageTransportation = (TextView) findViewById(R.id.transportation);
        mPackagePrice = (TextView) findViewById(R.id.Pprice);
        mDatabasePackage= FirebaseDatabase.getInstance().getReference().child("PackageOrders");
        CurrentUserID= FirebaseAuth.getInstance().getCurrentUser().getUid();




        mDatabase.child(mPost_key).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                 package_title = (String) snapshot.child("packname").getValue();
                 package_description = (String) snapshot.child("packdesc").getValue();
                 package_accommodation = (String) snapshot.child("hotel").getValue();
                 package_dining = (String) snapshot.child("res").getValue();
                 package_spots = (String) snapshot.child("spot").getValue();
                 package_transportation = (String) snapshot.child("trans").getValue();
                 package_price = (String) snapshot.child("packprice").getValue();
                 package_image = (String) snapshot.child("pack_img").getValue();

                mPackageTitle.setText(package_title);
                mPackageDescription.setText(package_description);
                mPackageAccommodation.setText("Accommodation : "+package_accommodation);
                mPackageSpots.setText(package_spots);
                mPackageDining.setText("Restaurant : "+package_dining);
                mPackageTransportation.setText("Transportation : "+package_transportation);
                mPackagePrice.setText(package_price+" BDT");
                Picasso.get().load(package_image).into(mPackageImage);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        PickDate=findViewById(R.id.Date_pickPackage);


        Calendar calendar= Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);




        PickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DatePickerDialog datePickerDialog = new DatePickerDialog(SinglePackageActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {


                        month=month+1;
                        String Date = year+"-"+month+"-"+day;
                        PickDate.setText(Date);
                        DateSelect = Date;
                    }


                },year,month,day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();

            }
        });
        String UPay="Paid";
        DatabaseReference newPackageOrder=mDatabasePackage.push();

        mBtnConfirmBooking=findViewById(R.id.ConfirmBookings);

        mBtnConfirmBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newPackageOrder.child("Package_Name").setValue(package_title);
                newPackageOrder.child("Package_Description").setValue(package_description);
                newPackageOrder.child("Hotel_name").setValue(package_accommodation);
                newPackageOrder.child("Covering_Spots").setValue(package_spots);
                newPackageOrder.child("Dining").setValue(package_dining);
                newPackageOrder.child("Transportation").setValue(package_transportation);
                newPackageOrder.child("Journey_Date").setValue(DateSelect);
                newPackageOrder.child("Payment").setValue(UPay);
                newPackageOrder.child("Total_Amount").setValue(package_price);
                newPackageOrder.child("uid").setValue(CurrentUserID);


                Intent PackagePaymentProcessIntent = new Intent(SinglePackageActivity.this,PackageOrder.class);
                PackagePaymentProcessIntent.putExtra("Package_Name",package_title);
                PackagePaymentProcessIntent.putExtra("Package_Description",package_description);
                PackagePaymentProcessIntent.putExtra("Hotel_name",package_accommodation);
                PackagePaymentProcessIntent.putExtra("Covering_Spots",package_spots);
                PackagePaymentProcessIntent.putExtra("Dining",package_dining);
                PackagePaymentProcessIntent.putExtra("Transportation",package_transportation);
                PackagePaymentProcessIntent.putExtra("Total_Amount",package_price);
                PackagePaymentProcessIntent.putExtra("Journey_Date",DateSelect);
                startActivity(PackagePaymentProcessIntent);

            }
        });





        //Toast.makeText(SinglePackageActivity.this,post_key,Toast.LENGTH_SHORT).show();

    }
}