package com.example.ghurefiribangladesh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class CurrentUserBookings extends AppCompatActivity {

    private RecyclerView mBookingList;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_user_bookings);

        mAuth=FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Orders");
        mBookingList = (RecyclerView) findViewById(R.id.Booking_List);
        mBookingList.setHasFixedSize(true);
        mBookingList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true));

    }

    @Override
    protected void onStart() {
        super.onStart();
        String CurrentUserID=mAuth.getCurrentUser().getUid();

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Orders")
                .orderByChild("uid")
                .equalTo(CurrentUserID)
                .limitToLast(50);
        FirebaseRecyclerOptions<BookingView> options =
                new FirebaseRecyclerOptions.Builder<BookingView>()
                        .setQuery(query, BookingView.class)
                        .build();
//Recycler for viewing the information of posts from database
        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<BookingView, CurrentUserBookings.BookingViewHolder>(options) {
            @Override
            public CurrentUserBookings.BookingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.bookings_row, parent, false);

                return new CurrentUserBookings.BookingViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(CurrentUserBookings.BookingViewHolder holder, int position, BookingView model) {

                //final String post_key= getRef(position).getKey();
                holder.setPlace_Name(model.getPlace_Name());
                holder.setHotel_Name(model.getHotel_Name());
                holder.setHotel_Price(model.getHotel_Price()+" BDT");
                holder.setTransportation_Name(model.getTransportation_Name());
                holder.setTransportation_Company(model.getTransportation_Company());
                holder.setTicket_Price(model.getTicket_Price()+" BDT");
                holder.setJourney_Date("Departure Date : "+model.getJourney_Date());
                holder.setPessenger("Total Passenger :         "+model.getPessenger());
                holder.setTotal_Amount(model.getTotal_Amount()+" BDT");



                /*holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        *//*Intent SinglePostIntent = new Intent(CurrentUserPosts.this,SinglePostActivity.class);
                        SinglePostIntent.putExtra("Post_id",post_key);
                        startActivity(SinglePostIntent);*//*
                    }
                });*/
            }
        };
        mBookingList.setAdapter(adapter);
        adapter.startListening();

    }

//view holder of storing the post information

    public static class BookingViewHolder extends RecyclerView.ViewHolder{

        View mView;
        FirebaseAuth mAuth;


        public BookingViewHolder(View itemView) {
            super(itemView);

            mView=itemView;
            mAuth=FirebaseAuth.getInstance();


        }


        public void setPlace_Name(String place_Name){

            TextView placeBookedName = (TextView) mView.findViewById(R.id.PlaceNameBooking);
            placeBookedName.setText(place_Name);

        }

        public void setHotel_Name(String hotel_Name){

            TextView HotelBookedName = (TextView) mView.findViewById(R.id.HotelNameBookin);
            HotelBookedName.setText(hotel_Name);

        }


        public void setHotel_Price(String hotel_Price){

            TextView BookedHotelPrice = (TextView) mView.findViewById(R.id.HotelPriceBooking);
            BookedHotelPrice.setText(hotel_Price);

        }


        public void setTransportation_Name(String transportation_Name){

            TextView BookedTransport = (TextView) mView.findViewById(R.id.TransportType);
            BookedTransport.setText(transportation_Name);

        }

        public void setTransportation_Company(String transportation_Company){

            TextView BookedTransportCompany = (TextView) mView.findViewById(R.id.CompanyName);
            BookedTransportCompany.setText(transportation_Company);

        }


        public void setTicket_Price(String ticket_Price){

            TextView BookedTicketPrice = (TextView) mView.findViewById(R.id.TikcetClassPrice);
            BookedTicketPrice.setText(ticket_Price);

        }


        public void setJourney_Date(String journey_Date){

            TextView BookedJourneyDate = (TextView) mView.findViewById(R.id.JourneyDate);
            BookedJourneyDate.setText(journey_Date);

        }

        public void setPessenger(String pessenger){

            TextView BookedPassenger = (TextView) mView.findViewById(R.id.TotalPERSONS);
            BookedPassenger.setText(pessenger);

        }


        public void setTotal_Amount(String total_Amount){

            TextView TotalBookingCost = (TextView) mView.findViewById(R.id.TotalOrderPrice);
            TotalBookingCost.setText(total_Amount);

        }


    }


}