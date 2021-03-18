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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class Transportation extends AppCompatActivity {

    private RecyclerView mTransportationList;
    private DatabaseReference mDatabaseTransport;
    private String mTransport_key = null;
    private String mAccommodationName = null;
    private String mAccommodationPrice = null;
    private String mPlaceName = null;

    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transportation);

        mTransport_key = getIntent().getExtras().getString("Place_id");
        mAccommodationName=getIntent().getExtras().getString("Accommodation_name");
        mAccommodationPrice=getIntent().getExtras().getString("Accommodation_Price");
        mPlaceName=getIntent().getExtras().getString("Place_name");
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Orders");

        mDatabaseTransport = FirebaseDatabase.getInstance().getReference().child("Tour").child("Place").child(mTransport_key).child("Transportation");
        mTransportationList=(RecyclerView) findViewById(R.id.Transport_List);
        mTransportationList.setHasFixedSize(true);
        mTransportationList.setLayoutManager(new LinearLayoutManager(this));
    }
    @Override
    protected void onStart() {
        super.onStart();

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Tour")
                .child("Place")
                .child(mTransport_key)
                .child("Transportation")
                .limitToLast(50);
        FirebaseRecyclerOptions<TransportationView> options =
                new FirebaseRecyclerOptions.Builder<TransportationView>()
                        .setQuery(query, TransportationView.class)
                        .build();
        //Recycler for viewing the information of Transportation Options from database
        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<TransportationView,Transportation.TransportViewHolder>(options) {
            @Override
            public Transportation.TransportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Used same procedure as the posting options for pulling and setting information from database
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.transportation_row, parent, false);

                return new Transportation.TransportViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(Transportation.TransportViewHolder holder, int position, TransportationView model) {

                String TransPortKEY = getRef(position).getKey();
                //String CurrentUserID= FirebaseAuth.getInstance().getCurrentUser().getUid();
                //DatabaseReference newOrder=mDatabase.push();

                holder.setSeats("Available Seats "+model.getSeats());
                holder.setTrans(model.getTrans());
                holder.setTrans_name(model.getTrans_name());
                holder.setType("Type "+model.getType());
                holder.setTicket_price(model.getTicket_price()+" BDT");
                holder.setTime_one(model.getTime_one());
                holder.setTime_two(model.getTime_two());
                holder.setTime_three(model.getTime_three());
                holder.setTrans_img(getApplicationContext(), model.getTrans_img());

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(Transportation.this, "Transportation Selected", Toast.LENGTH_SHORT).show();
                        Intent DateAndTimeIntent = new Intent(Transportation.this,TimeAndDate.class);
                        DateAndTimeIntent.putExtra("Place_id",mTransport_key);
                        DateAndTimeIntent.putExtra("Place_name",mPlaceName);
                        DateAndTimeIntent.putExtra("Accommodation_name",mAccommodationName);
                        DateAndTimeIntent.putExtra("Accommodation_Price",mAccommodationPrice);
                        DateAndTimeIntent.putExtra("Transport_name",model.getTrans_name());
                        DateAndTimeIntent.putExtra("Transport_Type",model.getTrans());
                        DateAndTimeIntent.putExtra("AC_NonAC",model.getType());
                        DateAndTimeIntent.putExtra("Transport_Seats",model.getSeats());
                        DateAndTimeIntent.putExtra("Transport_TimeOne",model.getTime_one());
                        DateAndTimeIntent.putExtra("Transport_TimeTwo",model.getTime_two());
                        DateAndTimeIntent.putExtra("Transport_TimeThree",model.getTime_three());
                        DateAndTimeIntent.putExtra("Transport_Price",model.getTicket_price());
                        DateAndTimeIntent.putExtra("TransPort_ID_KEY",TransPortKEY);


                        startActivity(DateAndTimeIntent);


                    }
                });

            }
        };
        mTransportationList.setAdapter(adapter);
        adapter.startListening();

    }


    public static class TransportViewHolder extends RecyclerView.ViewHolder{

        View mView;


        public TransportViewHolder(View itemView) {
            super(itemView);

            mView=itemView;

        }

        public void setSeats(String seats){

            TextView T_seats = (TextView) mView.findViewById(R.id.seats);
            T_seats.setText(seats);

        }

        public void setTrans(String trans){

            TextView T_name = (TextView) mView.findViewById(R.id.transportation_name);
            T_name.setText(trans);

        }

        public void setTrans_name(String trans_name){

            TextView Trans_Com_name = (TextView) mView.findViewById(R.id.transportation_comName);
            Trans_Com_name.setText(trans_name);

        }

        public void setType(String type){

            TextView Trans_com_type = (TextView) mView.findViewById(R.id.Type);
            Trans_com_type.setText(type);

        }


        public void setTicket_price(String ticket_price){

            TextView Trans_price = (TextView) mView.findViewById(R.id.transportation_price);
            Trans_price.setText(ticket_price);
        }


        public void setTime_one(String time_one){

            TextView Trans_timeOne = (TextView) mView.findViewById(R.id.TimeOne);
            Trans_timeOne.setText(time_one);
        }


        public void setTime_two(String time_two){

            TextView Trans_timeTwo = (TextView) mView.findViewById(R.id.TimeTwo);
            Trans_timeTwo.setText(time_two);
        }


        public void setTime_three(String time_three){

            TextView Trans_timeThree = (TextView) mView.findViewById(R.id.TimeThree);
            Trans_timeThree.setText(time_three);
        }


        public void setTrans_img(Context ctx ,String trans_img) {
            ImageView T_img = (ImageView) mView.findViewById(R.id.transportation_image);
            Picasso.get().load(trans_img).into(T_img);

        }


    }
}