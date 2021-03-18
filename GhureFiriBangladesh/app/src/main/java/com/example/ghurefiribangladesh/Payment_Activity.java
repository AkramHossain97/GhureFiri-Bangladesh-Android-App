package com.example.ghurefiribangladesh;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Payment_Activity extends AppCompatActivity {

    private String mPlaceName = null;
    private String mAccommodationName = null;
    private String mAccommodationPrice = null;
    private String mTime_SlotSelected = null;
    private String mTransportName = null;
    private String mTransportCompany = null;
    private String mTransportPrice = null;
    private String mTotalPassenger = null;
    private String mDepartureDate = null;
    private String mTotal_Bill = null;
    private TextView PmPlaceName;
    private TextView PmAccommodationName;
    private TextView PmAccommodationPrice;
    private TextView PmTime_SlotSelected;
    private TextView PmTransportName;
    private TextView PmTransportCompany;
    private TextView PmTransportPrice;
    private TextView PmTotalPassenger;
    private TextView PmDepartureDate;
    private TextView PmTotal_Bill;
    Button mBtnPay;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_);



        mAccommodationName=getIntent().getExtras().getString("Accommodation_name");
        mAccommodationPrice=getIntent().getExtras().getString("Accommodation_Price");
        mPlaceName=getIntent().getExtras().getString("Place_name");
        mTransportName=getIntent().getExtras().getString("Transport_Type");
        mTransportCompany=getIntent().getExtras().getString("Transport_name");
        mTransportPrice=getIntent().getExtras().getString("Transport_Price");
        mTime_SlotSelected=getIntent().getExtras().getString("Time_SlotSelected");
        mTotalPassenger=getIntent().getExtras().getString("TotalPassenger");
        mTotal_Bill=getIntent().getExtras().getString("Total_Bill");
        mDepartureDate=getIntent().getExtras().getString("DepartureDate");



        PmPlaceName=findViewById(R.id.PlaceNameOrder);
        PmAccommodationName=findViewById(R.id.HotelNameOrder);
        PmAccommodationPrice=findViewById(R.id.HotelPriceOrder);
        PmTransportName=findViewById(R.id.TransportTypeOrder);
        PmTransportCompany=findViewById(R.id.CompanyNameOrder);
        PmTransportPrice=findViewById(R.id.TikcetClassPriceOrder);
        PmTotal_Bill=findViewById(R.id.TotalOrderPriceOrder);
        PmTotalPassenger=findViewById(R.id.TotalPERSONSOrder);
        PmDepartureDate=findViewById(R.id.JourneyDateOrder);
        mBtnPay = findViewById(R.id.PayNow);


        PmPlaceName.setText(mPlaceName);
        PmAccommodationName.setText("Hotel : "+mAccommodationName);
        PmAccommodationPrice.setText("Room Price : "+mAccommodationPrice+" BDT");
        PmTransportName.setText(mTransportName);
        PmTransportCompany.setText(mTransportCompany+"("+mTime_SlotSelected+")");
        PmTransportPrice.setText("Per Ticket Price : "+mTransportPrice+" BDT");
        PmTotal_Bill.setText("Total Amount : "+mTotal_Bill+" BDT");
        PmTotalPassenger.setText("Passenger : "+mTotalPassenger);
        PmDepartureDate.setText("Departure Date : "+mDepartureDate);


        mBtnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent PaymentProcessIntent = new Intent(Payment_Activity.this,PaymentProcess.class);
                PaymentProcessIntent.putExtra("DepartureDate",mDepartureDate);
                PaymentProcessIntent.putExtra("Total_Bill",mTotal_Bill);
                PaymentProcessIntent.putExtra("Time_SlotSelected",mTime_SlotSelected);
                PaymentProcessIntent.putExtra("TotalPassenger",mTotalPassenger);
                startActivity(PaymentProcessIntent);

            }
        });



    }
}