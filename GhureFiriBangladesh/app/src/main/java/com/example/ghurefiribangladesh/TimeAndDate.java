package com.example.ghurefiribangladesh;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.Format;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TimeAndDate extends AppCompatActivity {

    TextView PickDate;
    private DatabaseReference mDatabaseOrder;
    DatePickerDialog.OnDateSetListener setListener;

    private String mTransport_key = null;
    private String mAccommodationName = null;
    private String mAccommodationPrice = null;
    private String mPlaceName = null;
    private String mTransportName = null;
    private String mTransportCompany = null;
    private String mTransportPrice = null;
    private String mTransportSeats = null;
    private String mTransportTimeOne = null;
    private String mTransportTimeTwo = null;
    private String mTransportTimeThree = null;
    private String Ac_NonAc = null;
    private String mTransportation_ID_KEY =null;
    private Spinner TimeSpinner;
    private Spinner PeopleSpinner;
    private Button btnConfirmBooking;
    private String passenger;
    private String StringPassenger;
     int INTPassenger;
     int NSeats;
     int newTotalSeats;
     String NewSeatsT;

    private String Timing;
    private String DateSelect;
    private int TotalPerson;
    private int INTicketPrice;
    private int INTHotelPrice;
    private int Total_Price;
    String itemPeople;
    private int NewTotalPerson;
    private String Total_S_Price;
    private String TicketSprice;
    private String Spersons;
    private String HotelSprice;
    DatabaseReference DataBaseRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_and_date);

        mTransport_key = getIntent().getExtras().getString("Place_id");
        mAccommodationName=getIntent().getExtras().getString("Accommodation_name");
        mAccommodationPrice=getIntent().getExtras().getString("Accommodation_Price");
        mPlaceName=getIntent().getExtras().getString("Place_name");
        mTransportName=getIntent().getExtras().getString("Transport_Type");
        mTransportCompany=getIntent().getExtras().getString("Transport_name");
        mTransportPrice=getIntent().getExtras().getString("Transport_Price");
        mTransportSeats=getIntent().getExtras().getString("Transport_Seats");
        mTransportTimeOne=getIntent().getExtras().getString("Transport_TimeOne");
        mTransportTimeTwo=getIntent().getExtras().getString("Transport_TimeTwo");
        mTransportTimeThree=getIntent().getExtras().getString("Transport_TimeThree");
        Ac_NonAc=getIntent().getExtras().getString("AC_NonAC");
        mTransportation_ID_KEY=getIntent().getExtras().getString("TransPort_ID_KEY");
        mDatabaseOrder= FirebaseDatabase.getInstance().getReference().child("Orders");
        DataBaseRef=FirebaseDatabase.getInstance().getReference().child("Tour");
        DatabaseReference newOrder=mDatabaseOrder.push();


        TimeSpinner = findViewById(R.id.TimeSpinner);
        PeopleSpinner = findViewById(R.id.SelectPeopleSpinner);
        btnConfirmBooking=findViewById(R.id.Booking);





        List<String> categoriesPeople = new ArrayList<>();
        categoriesPeople.add(0,"Choose People");
        categoriesPeople.add("1");
        categoriesPeople.add("2");
        categoriesPeople.add("3");
        categoriesPeople.add("4");
        categoriesPeople.add("5");
        categoriesPeople.add("6");
        categoriesPeople.add("7");
        categoriesPeople.add("8");
        categoriesPeople.add("9");
        categoriesPeople.add("10");


        ArrayAdapter<String> dataAdapterPerson;
        dataAdapterPerson = new ArrayAdapter(this, R.layout.style_spinner ,categoriesPeople);

        dataAdapterPerson.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        PeopleSpinner.setAdapter(dataAdapterPerson);
        PeopleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("Choose people")){

                }else{

                  itemPeople = PeopleSpinner.getItemAtPosition(position).toString();
                 passenger=itemPeople;
                 StringPassenger=itemPeople;


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        List<String> categories = new ArrayList<>();
        categories.add(0,"Choose Timing");
        categories.add(mTransportTimeOne);
        categories.add(mTransportTimeTwo);
        categories.add(mTransportTimeThree);

        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter(this, R.layout.style_spinner ,categories);

        dataAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        TimeSpinner.setAdapter(dataAdapter);
        TimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("Choose Timing")){

                }else{

                    String item = parent.getItemAtPosition(position).toString();
                    Timing=item;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        PickDate=findViewById(R.id.Date_pick);


        Calendar calendar= Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);


        PickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(TimeAndDate.this, new DatePickerDialog.OnDateSetListener() {
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
        String CurrentUserID= FirebaseAuth.getInstance().getCurrentUser().getUid();
        btnConfirmBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                INTPassenger=Integer.parseInt(StringPassenger);
                NSeats=Integer.parseInt(mTransportSeats);

                newTotalSeats=(NSeats-INTPassenger);

                NewSeatsT=Integer.toString(newTotalSeats);





                Total_S_Price=mTransportPrice.toString();
                HotelSprice = mAccommodationPrice.toString();

                try {
                    TotalPerson = Integer.parseInt(passenger);
                } catch(NumberFormatException nfe){}
                try {
                    INTicketPrice= Integer.parseInt(Total_S_Price);
                } catch(NumberFormatException nfe){}
                try {
                    INTHotelPrice = Integer.parseInt(HotelSprice);
                } catch(NumberFormatException nfe){}

                Total_Price = INTHotelPrice+((TotalPerson*INTicketPrice));
                Total_S_Price= Integer.toString(Total_Price);

                newOrder.child("Place_Name").setValue(mPlaceName);
                newOrder.child("Hotel_Name").setValue(mAccommodationName);
                newOrder.child("Hotel_Price").setValue(mAccommodationPrice);
                newOrder.child("Transportation_Name").setValue(mTransportName);
                newOrder.child("Transportation_Company").setValue(mTransportCompany);
                newOrder.child("Transportation_Type").setValue(Ac_NonAc);
                newOrder.child("Ticket_Price").setValue(mTransportPrice);
                newOrder.child("Journey_Date").setValue(DateSelect);
                newOrder.child("Pessenger").setValue(passenger.toString());
                newOrder.child("Time_Slot").setValue(Timing.toString());
                newOrder.child("Payment").setValue(UPay);
                newOrder.child("Total_Amount").setValue(Total_S_Price);
                newOrder.child("uid").setValue(CurrentUserID);
                DataBaseRef.child("Place").child(mTransport_key).child("Transportation").child(mTransportation_ID_KEY).child("seats").setValue(NewSeatsT);

                Intent PaymentIntent = new Intent(TimeAndDate.this,Payment_Activity.class);
                PaymentIntent.putExtra("Place_name",mPlaceName);
                PaymentIntent.putExtra("Accommodation_name",mAccommodationName);
                PaymentIntent.putExtra("Accommodation_Price",mAccommodationPrice);
                PaymentIntent.putExtra("Transport_name",mTransportCompany);
                PaymentIntent.putExtra("Transport_Type",mTransportName);
                PaymentIntent.putExtra("Transport_Price",mTransportPrice);
                PaymentIntent.putExtra("DepartureDate",DateSelect);
                PaymentIntent.putExtra("TotalPassenger",passenger);
                PaymentIntent.putExtra("Total_Bill",Total_S_Price);
                PaymentIntent.putExtra("Time_SlotSelected",Timing);
                startActivity(PaymentIntent);
            }
        });

    }
}