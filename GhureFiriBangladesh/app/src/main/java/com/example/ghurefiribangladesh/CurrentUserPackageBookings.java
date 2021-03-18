package com.example.ghurefiribangladesh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class CurrentUserPackageBookings extends AppCompatActivity {
    private RecyclerView mPackageBookingList;
    private DatabaseReference mDatabasePackage;
    private FirebaseAuth mAuthPackage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_user_package_bookings);

        mAuthPackage=FirebaseAuth.getInstance();
        mDatabasePackage = FirebaseDatabase.getInstance().getReference().child("PackageOrders");
        mPackageBookingList = (RecyclerView) findViewById(R.id.Package_Booking_List);
        mPackageBookingList.setHasFixedSize(true);
        mPackageBookingList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true));

    }

    @Override
    protected void onStart() {
        super.onStart();
        String CurrentUserID=mAuthPackage.getCurrentUser().getUid();

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("PackageOrders")
                .orderByChild("uid")
                .equalTo(CurrentUserID)
                .limitToLast(50);
        FirebaseRecyclerOptions<PackageBookingView> options =
                new FirebaseRecyclerOptions.Builder<PackageBookingView>()
                        .setQuery(query, PackageBookingView.class)
                        .build();
//Recycler for viewing the information of posts from database
        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<PackageBookingView, CurrentUserPackageBookings.PackageBookingViewHolder>(options) {
            @Override
            public CurrentUserPackageBookings.PackageBookingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.pack_order_row, parent, false);

                return new CurrentUserPackageBookings.PackageBookingViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(CurrentUserPackageBookings.PackageBookingViewHolder holder, int position, PackageBookingView model) {

                //final String post_key= getRef(position).getKey();
                holder.setPackage_Name(model.getPackage_Name());
                holder.setPackage_Description(model.getPackage_Description());
                holder.setHotel_name(model.getHotel_name());
                holder.setDining(model.getDining());
                holder.setCovering_Spots(model.getCovering_Spots());
                holder.setTransportation(model.getTransportation());
                holder.setJourney_Date("Departure Date : "+model.getJourney_Date());
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
        mPackageBookingList.setAdapter(adapter);
        adapter.startListening();

    }


    public static class PackageBookingViewHolder extends RecyclerView.ViewHolder{

        View mView;
        FirebaseAuth mAuth;


        public PackageBookingViewHolder(View itemView) {
            super(itemView);

            mView=itemView;
            mAuth=FirebaseAuth.getInstance();


        }


        public void setPackage_Name(String package_Name){

            TextView PackageBookedName = (TextView) mView.findViewById(R.id.PackageNameBooking);
            PackageBookedName.setText(package_Name);

        }

        public void setPackage_Description(String package_Description){

            TextView PackageBookingDescription = (TextView) mView.findViewById(R.id.PackageDesceBooking);
            PackageBookingDescription.setText(package_Description);

        }


        public void setHotel_name(String hotel_name){

            TextView BookedHotelName = (TextView) mView.findViewById(R.id.PackageHotelBooking);
            BookedHotelName.setText(hotel_name);

        }


        public void setDining(String dining){

            TextView BookedDining = (TextView) mView.findViewById(R.id.packageDiningBooking);
            BookedDining.setText(dining);

        }

        public void setCovering_Spots(String covering_Spots){

            TextView BookedCoveringSpots = (TextView) mView.findViewById(R.id.CoveringSpotsPackageBooking);
            BookedCoveringSpots.setText(covering_Spots);

        }


        public void setTransportation(String transportation){

            TextView BookedTransport = (TextView) mView.findViewById(R.id.PackageTransportationBooking);
            BookedTransport.setText(transportation);

        }


        public void setJourney_Date(String journey_Date){

            TextView PackageBookedJourneyDate = (TextView) mView.findViewById(R.id.PackageJourneyDateBooking);
            PackageBookedJourneyDate.setText(journey_Date);

        }



        public void setTotal_Amount(String total_Amount){

            TextView PackageTotalBookingCost = (TextView) mView.findViewById(R.id.TotalPackagePriceBooking);
            PackageTotalBookingCost.setText(total_Amount);

        }


    }

}