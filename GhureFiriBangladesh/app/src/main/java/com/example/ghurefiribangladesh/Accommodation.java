package com.example.ghurefiribangladesh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class Accommodation extends AppCompatActivity {


    private RecyclerView mHotelList;
    private DatabaseReference mDatabaseHotel;
    private String mPlace_key = null;
    private String mPlaceName = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accommodation);
        mPlace_key = getIntent().getExtras().getString("Place_id");
        mPlaceName = getIntent().getExtras().getString("Place_name");
        mDatabaseHotel = FirebaseDatabase.getInstance().getReference().child("Tour").child("Place").child(mPlace_key).child("Hotels");
        mHotelList=(RecyclerView) findViewById(R.id.Hotel_List);
        mHotelList.setHasFixedSize(true);
        mHotelList.setLayoutManager(new LinearLayoutManager(this));
    }



    @Override
    protected void onStart() {
        super.onStart();

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Tour")
                .child("Place")
                .child(mPlace_key)
                .child("Hotels")
                .limitToLast(50);
        FirebaseRecyclerOptions<HotelView> options =
                new FirebaseRecyclerOptions.Builder<HotelView>()
                        .setQuery(query, HotelView.class)
                        .build();
        //Recycler for viewing the information of Hotels from database
        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<HotelView,Accommodation.HotelViewHolder>(options) {
            @Override
            public Accommodation.HotelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Used same procedure as the posting options for pulling and setting information from database
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.hotel_row, parent, false);

                return new Accommodation.HotelViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(Accommodation.HotelViewHolder holder, int position, HotelView model) {

                String post_key = getRef(position).getKey();

                holder.setHotel_name(model.getHotel_name());
                holder.setRoom_price(model.getRoom_price()+" BDT");
                holder.setHotel_descr(model.getHotel_descr());
                holder.setHotel_img(getApplicationContext(), model.getHotel_img());

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(Accommodation.this, "Hotel Selected", Toast.LENGTH_SHORT).show();
                        Intent TransportIntent = new Intent(Accommodation.this,Transportation.class);
                        TransportIntent.putExtra("Place_id",mPlace_key);
                        TransportIntent.putExtra("Place_name",mPlaceName);
                        TransportIntent.putExtra("Accommodation_name",model.getHotel_name());
                        TransportIntent.putExtra("Accommodation_Price",model.getRoom_price());
                        startActivity(TransportIntent);

                    }
                });

            }
        };
        mHotelList.setAdapter(adapter);
        adapter.startListening();

    }




    //view holder of storing the Hotel information

    public static class HotelViewHolder extends RecyclerView.ViewHolder{

        View mView;


        public HotelViewHolder(View itemView) {
            super(itemView);

            mView=itemView;

        }

        public void setHotel_name(String hotel_name){

            TextView h_name = (TextView) mView.findViewById(R.id.hotel_title);
            h_name.setText(hotel_name);

        }

        public void setRoom_price(String room_price){

            TextView h_price = (TextView) mView.findViewById(R.id.hotel_price);
            h_price.setText(room_price);

        }


        public void setHotel_img(Context ctx , String hotel_img) {
            ImageView h_img = (ImageView) mView.findViewById(R.id.hotel_image);
            Picasso.get().load(hotel_img).into(h_img);

        }


        public void setHotel_descr(String hotel_descr){

            TextView h_desc = (TextView) mView.findViewById(R.id.hotel_desc);
            h_desc.setText(hotel_descr);

        }


    }




}