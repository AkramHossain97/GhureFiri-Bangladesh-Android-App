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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Tour extends AppCompatActivity {

    private RecyclerView mPlaceList;
    private DatabaseReference mDatabasePlace;
    private DatabaseReference mDatabaseO;
    private FirebaseUser mUsersO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);


        mDatabasePlace = FirebaseDatabase.getInstance().getReference().child("Tour").child("Place");
        mDatabaseO = FirebaseDatabase.getInstance().getReference().child("Orders");

        mPlaceList=(RecyclerView) findViewById(R.id.Place_List);
        mPlaceList.setHasFixedSize(true);
        mPlaceList.setLayoutManager(new LinearLayoutManager(this));


    }



    @Override
    protected void onStart() {
        super.onStart();

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Tour")
                .child("Place")
                .limitToLast(50);
        FirebaseRecyclerOptions<PlaceView> options =
                new FirebaseRecyclerOptions.Builder<PlaceView>()
                        .setQuery(query, PlaceView.class)
                        .build();
        //Recycler for viewing the information of places from database
        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<PlaceView,Tour.PlaceViewHolder>(options) {
            @Override
            public Tour.PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Used same procedure as the posting options for pulling and setting information from database
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.place_row, parent, false);

                return new Tour.PlaceViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(Tour.PlaceViewHolder holder, int position, PlaceView model) {

                String post_key = getRef(position).getKey();
                String mUsersO= FirebaseAuth.getInstance().getCurrentUser().getUid();

                holder.setPlace_name(model.getPlace_name());
                holder.setPlace_img(getApplicationContext(), model.getPlace_img());

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {




                        Toast.makeText(Tour.this, "Place Selected", Toast.LENGTH_SHORT).show();
                        Intent HotelIntent = new Intent(Tour.this,Accommodation.class);
                        HotelIntent.putExtra("Place_id",post_key);
                        HotelIntent.putExtra("Place_name",model.getPlace_name());
                        startActivity(HotelIntent);


                    }
                });

            }
        };
        mPlaceList.setAdapter(adapter);
        adapter.startListening();

    }




    //view holder of storing the place information

    public static class PlaceViewHolder extends RecyclerView.ViewHolder{

        View mView;


        public PlaceViewHolder(View itemView) {
            super(itemView);

            mView=itemView;

        }

        public void setPlace_name(String place_name){

            TextView pla_name = (TextView) mView.findViewById(R.id.place_title);
            pla_name.setText(place_name);

        }


        public void setPlace_img(Context ctx ,String place_img) {
            ImageView pla_img = (ImageView) mView.findViewById(R.id.place_image);
            Picasso.get().load(place_img).into(pla_img);

        }


    }








}