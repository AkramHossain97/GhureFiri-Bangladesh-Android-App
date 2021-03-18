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
import android.widget.Toast;

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

public class PackageOptions extends AppCompatActivity {


    private RecyclerView mPackageList;
    private DatabaseReference mDatabaseP;
    private DatabaseReference mDatabaseStar;
    private boolean mProcessStar=false;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_options);
        mAuth=FirebaseAuth.getInstance();

        mDatabaseP = FirebaseDatabase.getInstance().getReference().child("Package");

        mDatabaseStar=FirebaseDatabase.getInstance().getReference().child("Stars");

        mDatabaseStar.keepSynced(true);

        mPackageList=(RecyclerView) findViewById(R.id.Package_List);
        mPackageList.setHasFixedSize(true);
        mPackageList.setLayoutManager(new LinearLayoutManager(this));

    }



    @Override
    protected void onStart() {
        super.onStart();

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Package")
                .limitToLast(50);
        FirebaseRecyclerOptions<PackageView> options =
                new FirebaseRecyclerOptions.Builder<PackageView>()
                        .setQuery(query, PackageView.class)
                        .build();
        //Recycler for viewing the information of posts from database
        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<PackageView, PackageOptions.PackageViewHolder>(options) {
            @Override
            public PackageOptions.PackageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Used same procedure as the posting options for pulling and setting information from database
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.package_row, parent, false);

                return new PackageOptions.PackageViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(PackageOptions.PackageViewHolder holder, int position, PackageView model) {

                String post_key = getRef(position).getKey();

                holder.setPackname(model.getPackname());
                holder.setPackdesc(model.getPackdesc());
                holder.setPack_img(getApplicationContext(), model.getPack_img());
                holder.setPackprice(model.getPackprice()+" BDT");
                holder.setStarBtn(post_key);



                holder.mBtnStar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mProcessStar=true;
                        mDatabaseStar.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                if(mProcessStar) {
                                    if (snapshot.child(post_key).hasChild(mAuth.getCurrentUser().getUid())) {
                                        mDatabaseStar.child(post_key).child(mAuth.getCurrentUser().getUid()).removeValue();
                                        mProcessStar = false;
                                    } else {
                                        mDatabaseStar.child(post_key).child(mAuth.getCurrentUser().getUid()).setValue("True");
                                        mProcessStar = false;
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                });

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(PackageOptions.this,post_key,Toast.LENGTH_SHORT).show();

                        Intent SinglePackageIntent = new Intent(PackageOptions.this,SinglePackageActivity.class);
                        SinglePackageIntent.putExtra("Package_id",post_key);
                        startActivity(SinglePackageIntent);

                    }
                });

            }
        };
        mPackageList.setAdapter(adapter);
        adapter.startListening();

    }

        //view holder of storing the Package information

    public static class PackageViewHolder extends RecyclerView.ViewHolder{

        View mView;
        ImageButton mBtnStar;
        DatabaseReference mDatabaseStar;
        FirebaseAuth mAuth;
        TextView StarView;


        public PackageViewHolder(View itemView) {
            super(itemView);

            mView=itemView;
            mBtnStar = (ImageButton) mView.findViewById(R.id.StarButton);
            StarView = (TextView)mView.findViewById(R.id.StarText) ;
            mAuth= FirebaseAuth.getInstance();
            mDatabaseStar=FirebaseDatabase.getInstance().getReference().child("Stars");

            mDatabaseStar.keepSynced(true);

        }

        public void setStarBtn(String post_key){

            mDatabaseStar.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.child(post_key).hasChild(mAuth.getCurrentUser().getUid())){
                        int StarCounter = (int)snapshot.child(post_key).getChildrenCount();
                        StarView.setText(StarCounter+" People Recommended It");
                        mBtnStar.setImageResource(R.drawable.star_yellow);
                    }else {
                        int StarCounter = (int)snapshot.child(post_key).getChildrenCount();
                        StarView.setText(StarCounter+" People Recommended It");
                        mBtnStar.setImageResource(R.drawable.star_gray);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

       public void setPackname(String packname){

            TextView pack_title = (TextView) mView.findViewById(R.id.package_title);
            pack_title.setText(packname);

        }

        public void setPackdesc(String packdesc){

            TextView pack_dec = (TextView) mView.findViewById(R.id.package_desc);
            pack_dec.setText(packdesc);

        }

        public void setPack_img(Context ctx , String pack_img) {
            ImageView p_img = (ImageView) mView.findViewById(R.id.package_image);
            Picasso.get().load(pack_img).into(p_img);

        }

        public void setPackprice(String packprice){

            TextView pack_price = (TextView) mView.findViewById(R.id.package_price);
            pack_price.setText(packprice);

        }


    }
}