package com.example.ghurefiribangladesh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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

import java.util.Collections;

public class explore extends AppCompatActivity {

    private RecyclerView mPostList;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseLike;
    private boolean mProcessLike=false;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        mAuth=FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Post");
        mDatabaseLike=FirebaseDatabase.getInstance().getReference().child("Likes");

        mDatabaseLike.keepSynced(true);

        mPostList = (RecyclerView) findViewById(R.id.Post_List);
        mPostList.setHasFixedSize(true);
        mPostList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true));




    }

    @Override
    protected void onStart() {
        super.onStart();

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Post")
                .limitToLast(50);
        FirebaseRecyclerOptions<Bpost> options =
                new FirebaseRecyclerOptions.Builder<Bpost>()
                        .setQuery(query, Bpost.class)
                        .build();
//Recycler for viewing the information of posts from database
        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<Bpost, PostViewHolder>(options) {
            @Override
            public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.post_row, parent, false);

                return new PostViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(PostViewHolder holder, int position, Bpost model) {

                final String post_key= getRef(position).getKey();
                holder.setPostname(model.getPostname());
                holder.setPosttext(model.getPosttext());
                holder.setPost_image(getApplicationContext(), model.getPost_image());
                holder.setUsername(model.getUsername());
                holder.setLikeBtn(post_key);


                holder.mBtnLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mProcessLike=true;

                            mDatabaseLike.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    if(mProcessLike) {
                                        if (snapshot.child(post_key).hasChild(mAuth.getCurrentUser().getUid())) {

                                            mDatabaseLike.child(post_key).child(mAuth.getCurrentUser().getUid()).removeValue();
                                            mProcessLike = false;
                                        } else {
                                            mDatabaseLike.child(post_key).child(mAuth.getCurrentUser().getUid()).setValue("RValues");
                                            mProcessLike = false;


                                        }
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                    }
                });

            }
        };
        mPostList.setAdapter(adapter);
        adapter.startListening();

    }

//view holder of storing the post information

    public static class PostViewHolder extends RecyclerView.ViewHolder{

        View mView;
        ImageButton mBtnLike;
        DatabaseReference mDatabaseLike;
        FirebaseAuth mAuth;
        TextView LikeView;


        public PostViewHolder(View itemView) {
            super(itemView);

            mView=itemView;
            mBtnLike = (ImageButton) mView.findViewById(R.id.LikeButton);
            LikeView = (TextView)mView.findViewById(R.id.LikesText) ;
            mAuth=FirebaseAuth.getInstance();
            mDatabaseLike=FirebaseDatabase.getInstance().getReference().child("Likes");

            mDatabaseLike.keepSynced(true);


        }

        public void setLikeBtn(String post_key){

            mDatabaseLike.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.child(post_key).hasChild(mAuth.getCurrentUser().getUid())){

                            int LikeCounter = (int)snapshot.child(post_key).getChildrenCount();
                            LikeView.setText(LikeCounter+" Likes");
                            mBtnLike.setImageResource(R.drawable.thumbsup_red);

                    }else {

                        int LikeCounter = (int)snapshot.child(post_key).getChildrenCount();
                        LikeView.setText(LikeCounter+" Likes");
                        mBtnLike.setImageResource(R.drawable.thumbsup_gray);

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }

        public void setPostname(String postname){

            TextView post_title = (TextView) mView.findViewById(R.id.post_title);
            post_title.setText(postname);

        }

        public void setPosttext(String posttext){

            TextView post_dec = (TextView) mView.findViewById(R.id.post_desc);
            post_dec.setText(posttext);

        }

        public void setPost_image(Context ctx , String post_image) {
            ImageView post_img = (ImageView) mView.findViewById(R.id.post_image);
            Picasso.get().load(post_image).resize(800,0).centerCrop().into(post_img);

        }

        public void setUsername(String username){

            TextView post_Username = (TextView) mView.findViewById(R.id.post_userName);
            post_Username.setText(username);

        }


    }

    //for the + sign menu item in action bar

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.action_add){

            startActivity(new Intent(explore.this,PostActivity.class));

        }
        return super.onOptionsItemSelected(item);
    }
}